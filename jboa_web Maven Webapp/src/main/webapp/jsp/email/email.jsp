<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="entity.SysEmployee"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="shior" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="Jquery EasyUI Test">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/easyui/themes/pepper-grinder/easyui.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
<script type="text/javascript" src="js/email/crud.js"></script>
<script type="text/javascript" src="js/email/DataGrid.js"></script>

</head>
<body>
	<div id="table"></div>
	<div id="updateDialog" style="display: none;">
		<form id="updatePerson" method="post" >
			<table align="center">
				<tr >
					<td>服务器名:</td>
					<td><input id="host"  name="host" /></td>
				</tr>
				<tr >
					<td>断口号:</td>
					<td><input id="port"  name="port" /></td>
				</tr>
				<tr >
					<td>用户名:</td>
					<td><input id="username"  name="username" /></td>
				</tr>
				<tr >
					<td>密码:</td>
					<td><input id="password" name="password" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="testa" align="right" style="margin-top: 3px;">
		<!-- 		<input id="message"></input>
 -->
		<!-- <div id="message" style="width:150px">
		<div data-options="name:'item1'">Search Item1</div>
		<div data-options="name:'item2',selected:true">Search Item2</div>
		<div data-options="name:'item3'">Search Item3</div>
		</div> -->
		<%-- <select id="message">
			<c:forEach items="${sessionScope.depts}" var="dept">
				<optgroup label="${dept.name}">
					<c:forEach items="${dept.emps}" var="emp">
						<option value="${emp.sn}">${emp.name}</option>
					</c:forEach>
				</optgroup>
			</c:forEach>
		</select> --%>
		<!-- 	<div id="option" style="width: 120px">
			<div data-options="name:'name'">按填报人查询:</div>
			<div data-options="name:'nextDealSn'">按待处理人查询:</div>
		</div> -->
		<shior:hasPermission name="search">
			<div>
				<select id="searchBy"
					style="  background: transparent;border: 1px solid #ccc;  ">
					<option>按填报人查询:</option>
					<option>按待处理人查询:</option>
				</select> <select onchange="search(this)" id="search">
					<c:forEach items="${sessionScope.SearchDepts}" var="dept">
						<optgroup label="${dept.name}">
							<c:forEach items="${dept.emps}" var="emp">
								<option value="${emp.sn}">${emp.name}</option>
							</c:forEach>
						</optgroup>
					</c:forEach>
				</select>
			</div>
		</shior:hasPermission>
	</div>
</body>
</html>
