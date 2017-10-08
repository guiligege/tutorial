package com.cn.hnust.controller;

import com.cn.hnust.domain.CategoryDO;
import com.cn.hnust.service.ICategoryService;
import com.cn.hnust.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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


    @RequestMapping(value = {"index.htm","/"})
    public String toIndex(HttpServletRequest request, Model model) {

        //回传类目列表
        List<CategoryDO> categoryDOList = categoryService.selectAllCategoryDO();

        model.addAttribute("categoryDOList", categoryDOList);

        return "index";
    }

    @RequestMapping("admin.htm")
    public String test(HttpServletRequest request, Model model) {

        //回传类目列表
        List<CategoryDO> categoryDOList = categoryService.selectAllCategoryDO();

        model.addAttribute("categoryDOList", categoryDOList);

        return "admin";
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
