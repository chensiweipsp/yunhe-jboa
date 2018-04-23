<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="entity.SysEmployee" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
  <head>
<!--     <title>ystep流程、步骤插件 —— Powerd By YangYuan</title>
    <meta name="keywords" content="ystep,jQuery流程、步骤插件"/>
    <meta name="description" content="ystep,jQuery流程、步骤插件"/> -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入ystep样式 -->
    <link rel="stylesheet" href="css/ystep.css">
	
	<style>
	
	img {
    width: 100px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: inline-block;
}
	
div#authorization_box,div#auth_confirm {
    marguin:0 auto;
    overflow:hidden;
}

div#authorization_box {
    position:fixed;
    z-index:9999;
    top:50%;
    left:45%;
    width:450px; /* 按需改变数值 */
    height:50px; /* 按需改变数值 */
    margin-top:-75px; /* height数值的一半 */
    margin-left:-125px; /* width数值的一半 */
    -webkit-box-shadow:0 0 3px rgba(0,0,0,.2);
    -moz-box-shadow:0 0 3px rgba(0,0,0,.2);
    box-shadow:0 0 3px rgba(0,0,0,.2);
    background:#fff;
    padding:10px;
}
</style>
  </head>
  <body>
  
  <br><br><br><br><br>
  <!-- ystep容器 -->
 <div id="authorization_box"  >
 <div id="auth_confirm" >
    <div class="ystep2"></div>

 </div>
</div>
  
  
  
  <!-- 引入jquery -->
  <script src="js/jquery.min.js"></script>
  <!-- 引入ystep插件 -->
  <script src="js/ystep.js"></script>
  <script>
    //根据jQuery选择器找到需要加载ystep的容器
    //loadStep 方法可以初始化ystep
   

    $(".ystep2").loadStep({
      size: "large",
      steps: [
      {
        color: "blue",
        title: "发起",
        content: "该报销单为待审核的状态"
      }
      ,
      {
        color: "blue",
        title: "部门经理",
        content: "部门经理已经确认通过审核"
      },{
        color: "blue",
        title: "财务",
        content: "财务已经确认通过审核"
      },{
        color: "green",
        title: "总经理",
        content: "总经理已经确认通过审核"
      }]
    });
    
    
   var temp ="<%= (Integer)request.getAttribute("schedule")%>";
    $(".ystep2").setStep(Number(temp)   );
  </script>
  
  
  <img style="position:fixed;bottom:0px;left:0px" src="images/timg.jpg" border="0" onclick="javascript:history.back(-1);" title="返回上一页"> 
 
  </body>
</html>