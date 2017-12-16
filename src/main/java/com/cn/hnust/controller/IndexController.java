package com.cn.hnust.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.hnust.domain.Category;
import com.cn.hnust.domain.CategoryDO;
import com.cn.hnust.mongo.DomainConstans;
import com.cn.hnust.service.ICategoryService;
import com.cn.hnust.service.ISitemapService;
import com.cn.hnust.service.IUserService;
import com.cn.hnust.utils.ImageDownloadUtil;
import com.cn.hnust.utils.ImageRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    //	@Resource
    private IUserService userService;

    @Resource(name="categoryService")
    private ICategoryService categoryService;

    @Resource(name="sitemapService")
    private ISitemapService sitemapService;

    //复制到图片服务器目录test
    private static final String PATH = DomainConstans.IMAGE_PATH;
    //nginx静态图片资源url
    private static final String IMAGE_URL = DomainConstans.IMAGE_URL;


    @RequestMapping(value = {"index.htm","/"})
    public String toIndex(HttpServletRequest request, Model model) {

        //回传类目列表
        List<CategoryDO> categoryDOList = categoryService.selectAllCategoryDO();

        model.addAttribute("categoryDOList", categoryDOList);

        return "index";
    }

    @RequestMapping(value = {"push.htm"})
    public String pushSitemap(HttpServletRequest request, Model model,@RequestParam(value = "is_admin", required = false) String isAdmin) {

        sitemapService.onlinePush();

        return "index";
    }

    @RequestMapping("admin.htm")
    public String test(HttpServletRequest request, Model model) {

        //回传类目列表
        List<CategoryDO> categoryDOList = categoryService.selectAllCategoryDO();

        model.addAttribute("categoryDOList", categoryDOList);

        return "admin";
    }

    @RequestMapping("mianzhe.htm")
    public String mianzhe(HttpServletRequest request, Model model) {

        //回传类目列表
        List<CategoryDO> categoryDOList = categoryService.selectAllCategoryDO();

        model.addAttribute("categoryDOList", categoryDOList);

        return "mianzhe";
    }

    @RequestMapping("categoryAdmin.htm")
    public String categoryAdmin(HttpServletRequest request, Model model) {

        //回传类目列表
        List<CategoryDO> categoryDOList = categoryService.selectAllCategoryDO();

        model.addAttribute("categoryDOList", categoryDOList);

        return "category_admin";
    }

    //新增类目
    @RequestMapping("saveCategory.htm")
    public Category saveCategory(HttpServletRequest request, Model model,
                                @RequestParam(value = "p_cate_id", required = false) int  p_cate_id,
                                @RequestParam(value = "cate_id", required = false) int cate_id,
                                @RequestParam(value = "p_cate_name", required = false) String p_cate_name,
                                @RequestParam(value = "cate_name", required = false) String cate_name,
                                @RequestParam(value = "descrip", required = false) String descrip,
                                @RequestParam(value = "sort_id", required = false) int sort_id) {

        Category category = new Category();

        category.setCate_name(cate_name);
        category.setParent_id(p_cate_id);
        category.setId(cate_id);
        category.setParent_name(p_cate_name);
        category.setDescrip(descrip);
        category.setSort_id(sort_id);

        categoryService.addCategory(category);

        return new Category();
    }


    //返回json
    @ResponseBody
    @RequestMapping("/autoUploadPics.htm")
    public JSONArray list(HttpServletRequest request, Model model,
                          @RequestParam(value = "pics", required = false) String  pics) {

        JSONArray jsonArray = new JSONArray();
        if(StringUtils.isEmpty(pics)){
            return jsonArray;
        }
        String[] images = pics.split("\\|");

        for(String image:images){
//            String imageName = ImageRequest.saveImage(PATH,image);
            String imageName = ImageDownloadUtil.download(PATH,image);

//            System.out.println(imageName);
            JSONObject e = new JSONObject();
            e.put("prevPath", image);
            //生成url能访问的image
            e.put("newPath", IMAGE_URL+imageName);
            jsonArray.add(e);
        }

        return jsonArray;
    }

    /*@RequestMapping("admin.htm")
    public String admin(HttpServletRequest request, Model model) {

        return "admin";
    }*/

    /*@RequestMapping("detail.htm")
    public String detail(HttpServletRequest request, Model model) {

        //回传类目列表
        List<CategoryDO> categoryDOList = categoryService.selectAllCategoryDO();

        model.addAttribute("categoryDOList", categoryDOList);

        //详细页内容
        return "detail";
    }*/
}
