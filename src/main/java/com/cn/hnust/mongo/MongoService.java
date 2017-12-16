package com.cn.hnust.mongo;

import com.cn.hnust.Exception.MongoException;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOneModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import static com.mongodb.client.model.Filters.*;

/**
 * mongo db 插入查询
 *
 * @author zhengdong.xiao
 */
@Service
public class MongoService {

    private static final Logger log = LoggerFactory.getLogger(MongoService.class);
    private static final int NEARLY_DAY = -30;

    //文章字段
    private static final String Article_Titlehash = "titlehash";
    private static final String CATE_ID = "cate_id";
    private static final String CATE_NAME = "cate_name";
    private static final String SHORT_URL = "short_url";


    private static final Cache<String, BlockingQueue<byte[]>> cacheQueue = CacheBuilder.newBuilder()
            .maximumSize(MongoUtils.getTotalColl().size()).build();

    public void insertMany(final String document, final String collectionName) {
        Preconditions.checkArgument(MongoUtils.checkCollName(collectionName), "collection not exist in mongo");

        BlockingQueue<byte[]> documents = getQueue(collectionName);
        try {
            documents.put(Snappy.compress(document, Charsets.UTF_8));

            log.info("put document into BlockingQueue success. collectionName: {}, document: {}", collectionName, document);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MongoException("could not put document into BlockingQueue", e);
        } catch (IOException e) {
            throw new MongoException("snappy compress document error", e);
        }

        if (documents.size() >= DomainConstans.LOG_BATCH_SIZE) {
            final List<byte[]> selectDocs = Lists.newLinkedList();

            synchronized (this) {
                if (documents.size() >= DomainConstans.LOG_BATCH_SIZE) {
                    documents.drainTo(selectDocs);
                }
            }

            //如果数量大于100，同步插入mongodb
            if (CollectionUtils.isNotEmpty(selectDocs)) {
                log.info("start insert many docs. the size: {}", selectDocs.size());

                MongoUtils.getCollection(collectionName).insertMany(Lists.transform(selectDocs, new Function<byte[], Document>() {
                        @Override
                        public Document apply(byte[] input) {
                            try {
                                return Document.parse(Snappy.uncompressString(input, Charsets.UTF_8));
                            } catch (IOException e) {
                                throw new MongoException("snappy uncompress document error", e);
                            }
                        }
                }));
            }
        }
    }

    /**
     * update Article status
     * @param collectionName
     * @return
     */
    public void  updateTutorial(String name, String description, int sort_id, String content, String short_url, String collectionName) {
        Preconditions.checkArgument(MongoUtils.checkCollName(collectionName), "collection name not exist in mongo");

        //防空
        if(StringUtils.isEmpty(short_url)){
            return ;
        }
        Document doc=new Document(SHORT_URL, short_url);
        //更新条件
//        doc=new Document(SHORT_URL, short_url);

//        Map<String, Object> map = Maps.newHashMap();
//        map.put("name",name);
//        map.put("description",description);
//        map.put("sort_id",sort_id);
//        map.put("content",content);


        //需要更新字段
        Document document=new Document("name", name).append("description", description)
                .append("sort_id", sort_id)
                .append("content", content);
       Document  update=new Document("$set", document);

//        UpdateOneModel updateOneModel = new UpdateOneModel<Document>(new Document("_id", 10), new Document("$set", new Document("x", 101010)))
        /**
         * 条件查询mongodb
         */
        MongoUtils.getCollection(collectionName).updateOne(doc, update);

        return;
    }


    /**
     *
     * @param collectionName
     * @return
     */
    public List<Document> findByCateIdAndShortUrl(Integer cate_id, String short_url, String collectionName) {
        Preconditions.checkArgument(MongoUtils.checkCollName(collectionName), "collection name not exist in mongo");

        final List<Document> docs = Lists.newLinkedList();
        Bson bson = null;

        if (cate_id != null) {
            bson = and(eq(CATE_ID, cate_id));
        }
        if(StringUtils.isNotEmpty(short_url)){
            bson = and(eq(SHORT_URL, short_url));
        }

        FindIterable<Document> iterable = MongoUtils.getCollection(collectionName).find(bson).projection(Projections.exclude("_id"));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                docs.add(document);
            }
        });

        return docs;
    }

    /**
     *
     * @param collectionName
     * @return
     */
    public List<Document> findByCateName(String cate_name, String collectionName) {
        Preconditions.checkArgument(MongoUtils.checkCollName(collectionName), "collection name not exist in mongo");

        final List<Document> docs = Lists.newLinkedList();
        Bson bson = null;

        if (StringUtils.isNotEmpty(cate_name)) {
            bson = and(eq(CATE_NAME, cate_name));
        }
//        if(StringUtils.isNotEmpty(short_url)){
//            bson = and(eq(SHORT_URL, short_url));
//        }
        List<String> fieldNames=this.buildNearIncludeFieldNames();

        FindIterable<Document> iterable = MongoUtils.getCollection(collectionName).find(bson)
                                                    .projection(Projections.exclude("_id"))
                                                    .projection(Projections.include(fieldNames))
                                                    ;
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                docs.add(document);
            }
        });

        return docs;
    }


    /**
     *
     * @param collectionName
     * @return
     */
    public List<Document> findShortUrlByCateName(String cate_name, String collectionName) {
        Preconditions.checkArgument(MongoUtils.checkCollName(collectionName), "collection name not exist in mongo");

        final List<Document> docs = Lists.newLinkedList();
        Bson bson = null;

        if (StringUtils.isNotEmpty(cate_name)) {
            bson = and(eq(CATE_NAME, cate_name));
        }

        FindIterable<Document> iterable = MongoUtils.getCollection(collectionName).find(bson)
                .projection(Projections.exclude("_id"))
                .projection(Projections.include("short_url"))
                ;
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                docs.add(document);
            }
        });

        return docs;
    }

    /**
     *
     * @param collectionName
     * @return
     */
    public List<Document> findByShortUrl(String short_url, String collectionName) {
        Preconditions.checkArgument(MongoUtils.checkCollName(collectionName), "collection name not exist in mongo");

        final List<Document> docs = Lists.newLinkedList();
        Bson bson = null;

//        if (StringUtils.isNotEmpty(cate_name)) {
//            bson = and(eq(CATE_NAME, cate_name));
//        }
        if(StringUtils.isNotEmpty(short_url)){
            bson = and(eq(SHORT_URL, short_url));
        }
        List<String> fieldNames=this.buildNearIncludeFieldNames();

        FindIterable<Document> iterable = MongoUtils.getCollection(collectionName).find(bson)
                .projection(Projections.exclude("_id"))
                ;
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                docs.add(document);
            }
        });

        return docs;
    }

    /**
     * 构建列表查询，需要返回的字段列表
     * @return
     */
    private  List<String> buildNearIncludeFieldNames(){
        List<String> fieldNames=Lists.newArrayList();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("cate_name");
        fieldNames.add("short_url");
        return fieldNames;
    }



    private BlockingQueue<byte[]> getQueue(final String collectionName) {
        try {
            return cacheQueue.get(collectionName, new Callable<BlockingQueue<byte[]>>() {
                @Override
                public BlockingQueue<byte[]> call() throws Exception {
                    return new LinkedBlockingQueue<byte[]>(DomainConstans.LOG_BATCH_SIZE * 2);
                }
            });
        } catch (ExecutionException e) {
            throw new MongoException(e.getCause());
        }
    }


    //返回所有类目
    public List<Document> getAllCategory(String collectionName) {
        Preconditions.checkArgument(MongoUtils.checkCollName(collectionName), "collection name not exist in mongo");

        final List<Document> docs = Lists.newLinkedList();

        FindIterable<Document> iterable = MongoUtils.getCollection(collectionName).find().projection(Projections.exclude("_id"));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                docs.add(document);
            }
        });

        return docs;
    }

    public static void main(String[] args) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, -10);
        System.out.println(now.getTimeInMillis());
    }


}
