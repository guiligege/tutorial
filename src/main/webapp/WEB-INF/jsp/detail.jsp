<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <link type="image/x-icon" href="http://www.51jingxuan.com:8080/images/icon/jingxuan.ico" rel="shortcut icon" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta property="qc:admins" content="465267610762567726375" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>${tutorial == null ?"":tutorial.name} | 精选教程</title>

  <link rel="canonical" href="/tutorial/detail-${tutorial.cate_name}-${tutorial.short_url}.htm" />
  <meta name="keywords" content="${tutorial == null ?"":tutorial.name},${tutorial == null ?"":tutorial.name}-入门教程">
  <meta name="description" content="${tutorial == null ?"":tutorial.description}">

  <link rel="stylesheet" href="/css/themes/style.css?v=1.12" type="text/css" media="all" />
  <link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" media="all" />
  <!--[if gte IE 9]><!-->
  <script src="//cdn.bootcss.com/jquery/2.0.3/jquery.min.js"></script>
  <!--<![endif]-->
  <!--[if lt IE 9]>
  <script src="//cdn.bootcss.com/jquery/1.9.1/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
  <![endif]-->
  <meta name="apple-mobile-web-app-title" content="精选教程">
</head>
<body>

<!--头部-->
<div class="page-container" id="nav">
  <div id="logo" class="logo">
    <a href="http://www.51jingxuan.com/"><img src="/images/jingxuan02.png" title="精选教程网" /></a>
  </div>

  <div class="g-menu-mini l">
    <ul class="nav-item l">
      <li><a href="/" target="_self" title="精选编程网-入门教程-IT编程入门学习平台">首页</a></li>
      <%--<li><a href="http://class.imooc.com" target="_self" class="program-nav">职业路径<i class="icn-new"></i></a></li>--%>
      <li><a href="/tutorial/detail-HTML-.htm" class="active" target="_self" title="精选编程网-前端教程">前端教程</a></li>
      <li><a href="/tutorial/detail-Python-.htm" target="_self" title="精选编程网-服务端教程">服务端教程</a></li>
      <li><a href="javascript:void(0);" target="_self" title="精选编程网-数据库教程">数据库教程</a></li>
      <li><a href="javascript:void(0);" target="_self" title="精选编程网-运维教程">运维教程</a></li>
      <li><a href="javascript:void(0);" target="_self" title="精选编程网-测试教程">测试教程</a></li>
    </ul>
  </div>
</div>

<!--  内容  -->
<div class="container main">
  <!-- 中间 -->
  <div class="row">

    <div class="col left-column">
      <div class="tab">${tutorial == null ?"":tutorial.name}	<a data-cate="13" href="javascript:void(0);" title="夜间模式"  id="moon"><i class="fa fa-moon-o" aria-hidden="true" style="font-size: 1.4em;float: right;margin: 4px 6px 0;"></i></a>
        <a data-cate="13" style="display:none;" href="javascript:void(0);" title="日间模式"  id="sun" ><i class="fa fa-sun-o" aria-hidden="true" style="font-size: 1.4em;float: right;margin: 4px 6px 0;"></i></a>
      </div>
      <div class="sidebar-box gallery-list">
        <div class="design" id="leftcolumn">

          <c:forEach var="oneTutorial" items="${tutorialList}" varStatus="index">
              <a target="_top" title=" ${oneTutorial.name}	"  href="/tutorial/detail-${oneTutorial.cate_name}-${oneTutorial.short_url}.htm" >
                ${oneTutorial.name}			</a>
          </c:forEach>

        </div>
      </div>
    </div>

    <div class="col middle-column">


    <div class="article">

      <!--ad-->
      <%--<div class="article-heading-ad">
        <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
        <!-- 移动版 自动调整 -->
        <ins class="adsbygoogle"
             style="display:inline-block;min-width:300px;max-width:970px;width:100%;height:90px"
             data-ad-client="ca-pub-5751451760833794"
             data-ad-slot="1691338467"
             data-ad-format="horizontal"></ins>
        <script>
            (adsbygoogle = window.adsbygoogle || []).push({});
        </script>
      </div>--%>
      <!--js 替换-->
      <div class="previous-next-links">
        <div class="previous-design-link"><i style="font-size:16px;" class="fa fa-arrow-left" aria-hidden="true"></i> <a href="javascript:void(0);" rel="prev"> </a> </div>
        <div class="next-design-link"><a href="javascript:void(0);" rel="next"> </a> <i style="font-size:16px;" class="fa fa-arrow-right" aria-hidden="true"></i></div>
      </div>


        <div class="article-body">

          <div class="article-intro" id="content">
            <!--内容-->
              <c:if test="${!empty tutorial}">
                    ${tutorial.content}
              </c:if>
              <!--内容结束-->
          </div>
        </div>



      <div class="previous-next-links">
        <div class="previous-design-link"><i style="font-size:16px;" class="fa fa-arrow-left" aria-hidden="true"></i> <a href="javascript:void(0);" rel="prev"> </a> </div>
        <div class="next-design-link"><a href="javascript:void(0);" rel="next"></a> <i style="font-size:16px;" class="fa fa-arrow-right" aria-hidden="true"></i></div>
      </div>



      <link rel="stylesheet" href="/css/themes/qa.css?1.39">

        <!--广告-->
      <div class="sidebar-box ad-box ad-box-large">
        <div class="ad-336280" style="display: none;">

        </div>
      </div>

    </div>
  </div>


    <!-- 右边栏 -->
    <div class="fivecol last right-column">

      <style>
        .sidebar-tree .double-li {
          width:300px;
        }
        .sidebar-tree .double-li li {
          width: 44%;
          line-height: 1.5em;
          border-bottom: 1px solid #ccc;
          float: left;
          display: inline;
        }
      </style>


      <!--右侧类目导航-->
      <div class="sidebar-box ad-box ad-box-large">
        <div class="sidebar-box advertise-here" style="margin: 0 auto;">

          <a href="javascript:void(0);" style="font-size: 16px; color:black;font-weight:bold;"> <i class="fa fa-list" aria-hidden="true"></i>  分类导航</a>
        </div>
        <div class="sidebar-box sidebar-cate">

          <div class="sidebar-tree" >
            <ul>

              <c:forEach var="categoryDO" items="${categoryDOList}" varStatus="index">
                <li style="margin: 0;">
                    <a href="javascript:void(0);" class="tit"> ${categoryDO.category.cate_name}</a>
                    <ul class="double-li">
                      <c:forEach var="category" items="${categoryDO.children}" varStatus="index">
                        <li><a title="${category.cate_name}" href="/tutorial/detail-${category.cate_name}-.htm">${category.cate_name}</a></li>
                      </c:forEach>
                    </ul>
                </li>
              </c:forEach>

            </ul>
          </div>

        </div>
      </div>
      <br>

      <!--广告-->
      <%--<div class="sidebar-box ad-box ad-box-large">
        <div class="sidebar-box advertise-here">
          <a href="javascript:void(0);">Advertisement</a>
        </div>
        <div class="ad-600160">
          <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
          <!-- 侧栏1 -->
          <ins class="adsbygoogle"
               style="display:inline-block;width:160px;height:600px"
               data-ad-client="ca-pub-5751451760833794"
               data-ad-slot="4106274865"></ins>
          <script>
              (adsbygoogle = window.adsbygoogle || []).push({});
          </script>
        </div>
      </div>--%>
    </div></div>

</div>

<!-- 底部 -->


<div id="footer" class="mar-t50">
  <%--<div class="runoob-block">
    <div class="runoob cf">
      <dl>
        <dt>
          在线实例
        </dt>
        &lt;%&ndash;
         <dd>
           &middot;<a target="_blank" href="/java/java-examples.html">Java 实例</a>
         </dd>
         &ndash;%&gt;

      </dl>
      <dl>
        <dt>
          字符集&工具
        </dt>
        &lt;%&ndash;
         <dd>
           &middot; <a target="_blank" href="//c.runoob.com/front-end/53">JSON 格式化工具</a>
         </dd>
         &ndash;%&gt;
      </dl>
      <dl>
        <dt>
          最新更新
        </dt>
        &lt;%&ndash;<dd>
          &middot;
          <a href="http://www.runoob.com/w3cnote/python-round-func-note.html" title="Python 中关于 round 函数的小坑">Python 中关于 r...</a>
        </dd>&ndash;%&gt;

      </dl>
      <dl>
        <dt>
          站点信息
        </dt>
        <dd>
          &middot;
          <a target="_blank" href="javascript:void(0);" rel="external nofollow">意见反馈</a>
        </dd>
        <dd>
          &middot;
          <a target="_blank" href="/disclaimer">免责声明</a>
        </dd>
        <dd>
          &middot;
          <a target="_blank" href="/aboutus">关于我们</a>
        </dd>
        <dd>
          &middot;
          <a target="_blank" href="/archives">文章归档</a>
        </dd>

      </dl>
      <div class="search-share">
        <div class="app-download">
          <div>
            <strong>关注</strong>
          </div>
        </div>
      </div>
    </div>
  </div>--%>
  <div class="w-1000 copyright" style="border-top: 1px solid #e4e4e4;">
    Copyright &copy; 2015-2017    <strong><a href="//www.51jingxuan.com/" target="_blank">精选教程</a></strong>&nbsp;
    <strong><a href="//www.51jingxuan.com/" target="_blank">51jingxuan.com</a></strong> All Rights Reserved. 备案号：沪ICP备14006207号-3
  </div>
</div>
<div class="fixed-btn">
  <a class="go-top" href="javascript:void(0)" title="返回顶部"> <i class="fa fa-angle-up"></i></a>
  <%--<a class="qrcode"  href="javascript:void(0)" title="关注我们"><i class="fa fa-qrcode"></i></a>
  <a class="writer" target="_blank" href="//mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=ssbDyoOAgfLU3crf09venNHd3w" style="font-size: 12px;" title="Bug 反馈"><i class="fa fa-envelope-o" style="font-size: 20px;"></i></a>
  <div id="bottom-qrcode" class="modal panel-modal hide fade in">
    <h4>微信关注</h4>
    <div class="panel-body"><img alt="微信关注" width="128" height="128" src="/wp-content/themes/runoob/assets/images/qrcode.png"></div>
  </div>--%>
</div>
<div style="display:none;">

  <%--<script>
      var _hmt = _hmt || [];
      (function() {
          var hm = document.createElement("script");
          hm.src = "//hm.baidu.com/hm.js?8e2a116daf0104a78d601f40a45c75b4";
          var s = document.getElementsByTagName("script")[0];
          s.parentNode.insertBefore(hm, s);
      })();
  </script>--%>
</div>
<style>
  ol,ul{list-style:none}.cd-switcher a{text-decoration:none;outline:0}.cd-switcher a:hover{text-decoration:underline}a:focus{outline:0;-moz-outline:0}.main_nav{width:300px;height:60px;margin:60px auto 10px auto}.main_nav li{float:left;width:60px;margin-right:10px;font-size:16px;padding:.6em 1em;border-radius:3em;background:#2f889a;text-align:center}.main_nav li a{color:#fff}.errtip{background-color:#fceaea;color:#db5353;padding:8px 15px;font-size:14px;border:1px solid #fc9797;border-radius:5px}.cd-user-modal{position:fixed;top:0;left:0;width:100%;height:100%;background:rgba(52,54,66,0.9);z-index:3;overflow-y:auto;cursor:pointer;visibility:hidden;opacity:0;-webkit-transition:opacity .3s 0,visibility 0 .3s;-moz-transition:opacity .3s 0,visibility 0 .3s;transition:opacity .3s 0,visibility 0 .3s}.cd-user-modal.is-visible{visibility:visible;opacity:1;-webkit-transition:opacity .3s 0,visibility 0 0;-moz-transition:opacity .3s 0,visibility 0 0;transition:opacity .3s 0,visibility 0 0}.cd-user-modal.is-visible .cd-user-modal-container{-webkit-transform:translateY(0);-moz-transform:translateY(0);-ms-transform:translateY(0);-o-transform:translateY(0);transform:translateY(0)}.cd-user-modal-container{position:relative;width:90%;max-width:500px;background:#FFF;margin:3em auto 4em;cursor:auto;border-radius:.25em;-webkit-transform:translateY(-30px);-moz-transform:translateY(-30px);-ms-transform:translateY(-30px);-o-transform:translateY(-30px);transform:translateY(-30px);-webkit-transition-property:-webkit-transform;-moz-transition-property:-moz-transform;transition-property:transform;-webkit-transition-duration:.3s;-moz-transition-duration:.3s;transition-duration:.3s}.cd-user-modal-container .cd-switcher:after{content:"";display:table;clear:both}.cd-user-modal-container .cd-switcher li{width:50%;float:left;text-align:center}.cd-user-modal-container .cd-switcher li:first-child a{border-radius:.25em 0 0 0}.cd-user-modal-container .cd-switcher li:last-child a{border-radius:0 .25em 0 0}.cd-user-modal-container .cd-switcher a{font-size:1.2em;font-weight:bold;display:block;width:100%;height:50px;line-height:50px;background:#e8f1e2;color:#96b880}.cd-user-modal-container .cd-switcher a.selected{background:#FFF;color:#505260}@media only screen and (min-width:600px){.cd-user-modal-container{margin:4em auto}.cd-user-modal-container .cd-switcher a{height:70px;line-height:70px}}.cd-form{padding:1.4em}.cd-form .fieldset{position:relative;margin:1.4em 0}.cd-form .fieldset:first-child{margin-top:0}.cd-form .fieldset:last-child{margin-bottom:0}.cd-form label{font-size:16px;font-size:.875rem}.cd-form label.image-replace{display:inline-block;position:absolute;left:15px;top:50%;bottom:auto;-webkit-transform:translateY(-50%);-moz-transform:translateY(-50%);-ms-transform:translateY(-50%);-o-transform:translateY(-50%);transform:translateY(-50%);height:20px;width:20px;overflow:hidden;text-indent:100%;white-space:nowrap;color:transparent;text-shadow:none;background-position:50% 0}.cd-form label.cd-username{}.cd-form label.cd-email{}.cd-form label.cd-password{}.cd-form input{margin:0;padding:0;border-radius:.25em}.cd-form input.full-width{width:80%}.cd-form input.full-width2{width:94%}.cd-form input.has-padding{padding:12px 20px 12px 50px}.cd-form input.has-border{border:1px solid #d2d8d8;-webkit-appearance:none;-moz-appearance:none;-ms-appearance:none;-o-appearance:none;appearance:none}.cd-form input.has-border:focus{border-color:#98b880;box-shadow:0 0 5px rgba(52,54,66,0.1);outline:0}.cd-form input.has-error{border:1px solid #d76666}.cd-form input[type=password]{padding-right:65px}.cd-form input[type=submit]{padding:16px 0;cursor:pointer;background:#96b97d;color:#FFF;font-weight:bold;border:0;-webkit-appearance:none;-moz-appearance:none;-ms-appearance:none;-o-appearance:none;appearance:none;font-size:1.2em;font-weight:bold}.no-touch .cd-form input[type=submit]:hover,.no-touch .cd-form input[type=submit]:focus{background:#3599ae;outline:0}@media only screen and (min-width:600px){.cd-form{padding:2em}.cd-form .fieldset{margin:2em 0}.cd-form .fieldset:first-child{margin-top:0}.cd-form .fieldset:last-child{margin-bottom:0}.cd-form input.has-padding{padding:16px 20px 16px 50px}.cd-form input[type=submit]{padding:16px 0}}.cd-close-form{display:block;position:absolute;width:40px;height:40px;right:0;top:-40px;) no-repeat center center;text-indent:100%;white-space:nowrap;overflow:hidden}@media only screen and (min-width:1170px){}#cd-login,#cd-signup,#cd-reset-password{display:none}#cd-login.is-selected,#cd-signup.is-selected,#cd-reset-password.is-selected{display:block}
</style>

<script src="/js/themes/main.js?v=1.147"></script>
<script src="/js/asserts/run_prettify.js"></script>
</body>
</html>