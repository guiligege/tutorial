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

    短链接：<input type="text" style="width: 300px" name="search" id="search"/>
    <input type="button"  id="seachButton" value="搜索"/>

    <form  action="" >

        <div style="    margin-left: 400px;margin-top: 100px;">
            <div style="margin-left: 250px;margin-bottom: 30px;font-size: larger;">
                新增教程
            </div>
            <table   cellspacing="0" style="margin-bottom: 10px;">
                <tr>
                    <th>类目</th>
                </tr>
                <tr>
                    <td>
                        <select id="category">
                            <c:forEach var="categoryDO" items="${categoryDOList}" varStatus="index">
                                <c:forEach var="category" items="${categoryDO.children}" varStatus="status">
                                    <option value='${category.id}'>${category.cate_name}</option>
                                </c:forEach>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            <div style="display: none;">短链接：<input type="text" id="short_url" name="short_url" value="" style="width: 400px;margin-bottom: 10px;"/><br/></div>
            教程名：<input type="text" id="tutorial_name" name="name" value="" style="width: 400px;margin-bottom: 10px;"/><br/>
            教程描述：<input type="text" id="tutorial_desc" name="description" value="" style="width: 400px;margin-bottom: 10px;"/><br/>
            排序号（越大越靠后）：<input type="text" id="sort_num" name="sort_num" value="" style="width: 400px;margin-bottom: 10px;"/><br/>

            <!--文本编辑器-->
            内容：
            <textarea id="editor_id" name="content" style="width:700px;height:300px; ">
            </textarea>

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
    <script>

        // 关闭过滤模式，保留所有标签
        KindEditor.options.filterMode = false;
        var kindEditor;
        KindEditor.ready(function(K) {
            kindEditor = K.create('#editor_id');
        });

        //测试 html
        /*function save(){

            alert(kindEditor.html());
            document.getElementById('result').innerHTML=kindEditor.html();


        }*/

        $(document).ready(function(){

            //搜索
            $("#seachButton").click(function(){

                $("#subButton").hide();
                $("#updateButton").show();

                var searchValue = $("#search").val();

                //发起post
                $.post("/tutorial/getBySortUrl.htm",{
                        short_url:searchValue,
                    },
                    function(data){

                        var jsonObj = eval( '(' + data + ')' );  // eval();方法
                        kindEditor.html(jsonObj.content);
                        $("#tutorial_name").val(jsonObj.name);
                        $("#tutorial_desc").val(jsonObj.description);
                        $("#sort_num").val(jsonObj.sort_id);
                        $("#short_url").val(jsonObj.short_url);

                    });
            });

            //更新
            $("#updateButton").click(function(){

                var tutorial_name = $("#tutorial_name").val();
                var tutorial_desc = $("#tutorial_desc").val();
                var sort_num = $("#sort_num").val();
                var short_url = $("#short_url").val();
                //编辑器内容
                var content = kindEditor.html();

                if(tutorial_name == ''){
                    alert("课程名不能为空！");
                    return;
                }
                if(short_url == ''){
                    alert("短链接不能为空！");
                    return;
                }
                if(tutorial_desc == ''){
                    alert("课程描述不能为空！");
                    return;
                }
                if(sort_num == ''){
                    alert("排序不能为空！");
                    return;
                }
                if(content == ''){
                    alert("内容不能为空！");
                    return;
                }

                //发起post
                $.post("/tutorial/update.htm",{
                        name:tutorial_name,
                        description:tutorial_desc,
                        sort_id:sort_num,
                        content:content,
                        short_url:short_url,
                    },
                    function(data){

                        alert("状态：成功！" );
                        // 设置HTML内容
                        kindEditor.html('');
                        $("#tutorial_name").val("");
                        $("#tutorial_desc").val("");
                        $("#sort_num").val("");

                    });
            });

            $("#subButton").click(function(){
                //校验参数

                var cate_id = $("#category").val();
                var cate_name = $("#category option:selected").text();
                var tutorial_name = $("#tutorial_name").val();
                var tutorial_desc = $("#tutorial_desc").val();
                var sort_num = $("#sort_num").val();
                //编辑器内容
                var content = kindEditor.html();

                if(cate_name == ''){
                    alert("类目名不能为空！");
                    return;
                }

                if(tutorial_name == ''){
                    alert("课程名不能为空！");
                    return;
                }
                if(tutorial_desc == ''){
                    alert("课程描述不能为空！");
                    return;
                }
                if(sort_num == ''){
                    alert("排序不能为空！");
                    return;
                }
                if(content == ''){
                    alert("内容不能为空！");
                    return;
                }

                //发起post
                $.post("/tutorial/save.htm",{
                        cate_name:cate_name,
                        cate_id:cate_id,
                        name:tutorial_name,
                        description:tutorial_desc,
                        sort_id:sort_num,
                        content:content,
                },
                function(data){

                    alert("状态：成功！" );
                    // 设置HTML内容
                    kindEditor.html('');
                    $("#tutorial_name").val("");
                    $("#tutorial_desc").val("");
                    $("#sort_num").val("");

                });
            });
        });
    </script>
</html>