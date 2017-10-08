package com.cn.hnust.controller;

import com.cn.hnust.domain.CategoryDO;
import com.cn.hnust.domain.Tutorial;
import com.cn.hnust.domain.TutorialDO;
import com.cn.hnust.service.ICategoryService;
import com.cn.hnust.service.ITutorialService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 教程 controller
 */
@Controller
@RequestMapping("/tutorial")
public class TutorialController {

    @Resource(name = "categoryService")
    private ICategoryService categoryService;

    @Resource(name = "tutorialService")
    private ITutorialService tutorialService;

    @RequestMapping(value = {"/save.htm"}, method = {RequestMethod.POST})
    public @ResponseBody
    Tutorial saveTutorial(HttpServletRequest request, Model model,
                          @RequestParam(value = "cate_name", required = false) String cate_name,
                          @RequestParam(value = "cate_id", required = false) int cate_id,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "sort_id", required = false) int sort_id,
                          @RequestParam(value = "content", required = false) String content) {

        //构造对象
        Tutorial tutorial = new Tutorial();
        tutorial.setCate_id(cate_id);
        tutorial.setCate_name(cate_name);
        tutorial.setContent(content);
        tutorial.setDescription(description);
        tutorial.setSort_id(sort_id);
        tutorial.setName(name);
        //设置随机id

        //保存
        tutorialService.addTutorial(tutorial);

        System.out.println("aa");


        return new Tutorial();
    }

    @RequestMapping(value = {"/update.htm"}, method = {RequestMethod.POST})
    public @ResponseBody
    Tutorial updateTutorial(HttpServletRequest request, Model model,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "sort_id", required = false) int sort_id,
                          @RequestParam(value = "content", required = false) String content,
                            @RequestParam(value = "short_url", required = false) String short_url) {

        //构造对象
        Tutorial tutorial = new Tutorial();
        tutorial.setContent(content);
        tutorial.setDescription(description);
        tutorial.setSort_id(sort_id);
        tutorial.setName(name);
        tutorial.setShort_url(short_url);

        //保存
        tutorialService.updateTutorial(tutorial);

        System.out.println("aa");


        return new Tutorial();
    }

    @RequestMapping(value = {"/getBySortUrl.htm"}, method = {RequestMethod.POST})
    public @ResponseBody Tutorial getTutorial(HttpServletRequest request, Model model,
                         @RequestParam(value = "short_url", required = false) String short_url) {

        if(StringUtils.isEmpty(short_url)){
            return null;
        }
        //构造对象
        Tutorial tutorial = new Tutorial();
        //设置随机id

        tutorial = tutorialService.getCurrentTutorial(short_url);

        return tutorial;
    }

    @RequestMapping(value = {"/detail.htm"}, method = {RequestMethod.GET})
    public String detail(HttpServletRequest request, Model model,
                         @RequestParam(value = "cate_name", required = false) String cate_name,
                         @RequestParam(value = "short_url", required = false) String short_url) {

        if(StringUtils.isEmpty(cate_name) && StringUtils.isEmpty(short_url)){
            //TODO 重定向到异常页
            return "detail";
        }

        //回传类目列表
        List<CategoryDO> categoryDOList = categoryService.selectAllCategoryDO();

        TutorialDO tutorialDO = tutorialService.selectByCateIdAndShortUrl(cate_name,short_url);

        if(CollectionUtils.isNotEmpty(tutorialDO.getTutorialList())){
            //始终取第一个。如果查询类目，返回列表取第一个
            //如果带短链接的查询，那么只有一个返回。也取第一个

            model.addAttribute("tutorial", tutorialDO.getCurrentTutorial());
            model.addAttribute("prev", tutorialDO.getPre_short_url());
            model.addAttribute("next", tutorialDO.getNext_short_url());
            model.addAttribute("tutorialList", tutorialDO.getTutorialList());
        }



        model.addAttribute("categoryDOList", categoryDOList);

        //详细页内容
        return "detail";
    }

}
