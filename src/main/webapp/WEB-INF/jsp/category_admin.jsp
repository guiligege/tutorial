<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="utf-8">
        <title>管理页面</title>
        <script language="JavaScript">
            function setSel(obj){

                //get k1 value
                var k1=obj.value;//下拉框一的值
                var k2=document.getElementById("test2");

                //delete k2 history
                var len_k2=k2.length;
                for(var i=0;i<len_k2;i++){//此处没删除一次，长度值就会发生变化，所以需要用一个变量来表示该长度值
                    k2.remove(0);//因为每次删除后，值得顺序都发生了变化，所以最好每次都删第一个元素
                }

                // add new options
                var obj2=document.getElementsByName(k1);   //将一级科目的下拉框的值作为获取相同name值的变量
                for(var i=0;i<obj2.length;i++){   //一定要用var作为声明类型
                    k2.options.add(new Option(obj2[i].value,obj2[i].value));
                }
            }
        </script>
    </head>

    <body>

    <form  action="" >

        <div style="    margin-left: 400px;margin-top: 100px;">
            <div style="margin-left: 250px;margin-bottom: 30px;font-size: larger;">
                新增类目
            </div>
            <table   cellspacing="0" style="margin-bottom: 10px;">
                <tr>
                    <th>类目</th>
                </tr>
                <tr>
                    <td>
                        <select id="category">
                            <c:forEach var="categoryDO" items="${categoryDOList}" varStatus="index">
                                    <option value='${categoryDO.category.id}'>${categoryDO.category.cate_name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            类目Id：<input type="text" id="cate_id" name="name" value="" style="width: 400px;margin-bottom: 10px;"/><br/>
            类目名：<input type="text" id="cate_name" name="name" value="" style="width: 400px;margin-bottom: 10px;"/><br/>
            类目描述：<input type="text" id="descrip" name="description" value="" style="width: 400px;margin-bottom: 10px;"/><br/>
            <!--排序号（越大越靠后）：<input type="text" id="sort_id" name="sort_num" value="" style="width: 400px;margin-bottom: 10px;"/><br/>
            -->
            <input type="button" onclick="javascript:void(0);" id="subButton" value="保存" style="width: 200px;height: 100px;margin-top: 10px;">
            <input type="button" onclick="javascript:void(0);" id="updateButton" value="更新" style="width: 200px;height: 100px;margin-top: 10px;display:none;">
        </div>

    </form>

    <div id ="result">
    </div>

    </body>

    <script src="//cdn.bootcss.com/jquery/2.0.3/jquery.min.js"></script>
    <script charset="utf-8" src="js/kind_editer/kindeditor-all.js"></script>
    <script charset="utf-8" src="js/kind_editer/lang/zh-CN.js"></script>

    <script >

        $("#subButton").click(function(){
        //校验参数

        var p_cate_id = $("#category").val();
        var p_cate_name = $("#category option:selected").text();
        var cate_name = $("#cate_name").val();
        var descrip = $("#descrip").val();
        var cate_id = $("#cate_id").val();
        var sort_id = cate_id;

        if(p_cate_name == ''){
            alert("父类目名不能为空！");
            return;
        }

        if(cate_name == ''){
            alert("类目名不能为空！");
            return;
        }
        if(cate_id == ''){
            alert("类目id不能为空！");
            return;
        }
        if(descrip == ''){
            alert("描述不能为空！");
            return;
        }
        if(sort_id == ''){
            alert("排序不能为空！");
            return;
        }
        if(p_cate_id == ''){
            alert("父id不能为空！");
            return;
        }

        //发起post
        $.post("/saveCategory.htm",{
                p_cate_name:p_cate_name,
                cate_id:cate_id,
                cate_name:cate_name,
                descrip:descrip,
                sort_id:sort_id,
                p_cate_id:p_cate_id,
            },
            function(data){

                alert("保存成功！");
            });
    });

    </script>

</html>