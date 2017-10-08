<%--
  Created by IntelliJ IDEA.
  User: zhengdongxiao
  Date: 2017/10/5
  Time: 下午7:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>下拉框与下拉框之间的联动效果</title>
    <script language="JavaScript">
        function setSel(obj){
            //alert(obj.value+"        1111222222");
            var k1=obj.value;//下拉框一的值
            var k2=document.getElementById("test2");

            //var obj2=document.getElementsByName("inps");   //获取相同name值的属性
            //var obj2=document.getElementsByTagName("input");   //获取所有相同的标签元素

            var len_k2=k2.length;
            for(var i=0;i<len_k2;i++){//此处没删除一次，长度值就会发生变化，所以需要用一个变量来表示该长度值
                k2.remove(0);//因为每次删除后，值得顺序都发生了变化，所以最好每次都删第一个元素
            }

            var obj2=document.getElementsByName(k1);   //将一级科目的下拉框的值作为获取相同name值的变量
            for(var i=0;i<obj2.length;i++){   //一定要用var作为声明类型
                k2.options.add(new Option(obj2[i].value,obj2[i].value));
            }
        }
    </script>
</head>

<body>
<form>
    <!--
    基本方式有以下两种：
    方式1：
    给出菜单1的菜单数据，当点击某个菜单项后，把该项的值传给服务端，由服务端返回新的菜单数据到菜单2.
    方式2：
    菜单数据静态存储在js或者html元素中，当点击某个菜单项后，直接把相应的菜单数据到菜单2.
    -->
    <fieldset style="width:500px;margin-left:32%;margin-top:10%;">
        <legend >下拉框与下拉框之间的联动效果</legend>
        <table border="1" cellpadding="5px" cellspacing="0" align="center" style="margin-top:10%;margin-bottom:10%;" width="300px" height="150px">
            <tr>
                <th>一级科目</th>
                <th>二级科目</th>
            </tr>
            <tr>
                <td align="center">
                    <select id="test1" onchange="setSel(this)">
                        <option value='1'>1</option>
                        <option value='2'>2</option>
                    </select>
                </td>
                <td align="center">
                    <select id="test2">
                    </select>
                </td>
            </tr>
        </table>
    </fieldset>
</form>

<input name="1" type="hidden" value="张三"/>
<input name="1" type="hidden" value="李四"/>
<input name="1" type="hidden" value="王五"/>
<input name="1" type="hidden" value="李六"/>

<input name="2" type="hidden" value="AAA"/>
<input name="2" type="hidden" value="BBB"/>
<input name="2" type="hidden" value="CCC"/>
<input name="2" type="hidden" value="DDD"/>

</body>
</html>
