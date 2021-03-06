<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="entity.SysEmployee"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="shior" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
<script type="text/javascript" src="js/BizClaimVoucher/formatter.js"></script>
<script type="text/javascript"
	src="js/BizClaimVoucher/LookThrough/search.js"></script>
<script type="text/javascript" src="js/BizClaimVoucher/jasper.js"></script>
<script type="text/javascript"
	src="js/BizClaimVoucher/LookThrough/crud.js"></script>
<script type="text/javascript"
	src="js/BizClaimVoucher/LookThrough/DataGrid.js"></script>
</head>
<body>
	<div id="table"></div>
	<div id="updateDialog" style="display: none;">
		<form id="pass" method="post">
			<table align="center">
				<tr style="display: none;">
					<td>编号:</td>
					<td><input class="easyui-validatebox" id="id" name="id"
						readonly="readonly"
						data-options="required:true,missingMessage:'输入数据不能为空!',tipPosition:'right'"></input>
					</td>
				</tr>
				
				<tr style="display: none;">
					<td>taskid:</td>
					<td><input id="taskid" name="taskid"></input></td>
				</tr>
				
				<tr style="display: none;">
					<td><input id="updatecreateSn" name="createSn"
						style="display: none;" /></td>
					<td></td>
				</tr>

				<tr>
					<td>填报人:</td>
					<td><input disabled="disabled" id="updatecreateName"
						name="createName" /></td>
				</tr>
				<tr style="display: none" >
					<td>填报人:</td>
					<td><input id="updatecreateSn" 
						name="createSn" /></td>
				</tr>
				
				<shior:lacksRole name="generalmanager">
				<tr>
					<td>待处理人:</td>
					<td><select id="updatenextDealSn" name="nextDealSn">
							<optgroup label="${sessionScope.AddDepts.name}">
								<c:forEach items="${sessionScope.AddDepts.emps}" var="emp">
									<option value="${emp.sn}">${emp.name}</option>
								</c:forEach>
							</optgroup>
					</select></td>
				</tr>
				</shior:lacksRole>
				
				
				
				
				
				<tr>
					<td>填写时间:</td>
					<td><input disabled="disabled" class="easyui-datebox"
						id="updatecreateTime" required="required" name="createTime"
						data-options="required:true,validType:'length[2,25]',missingMessage:'输入数据不能为空!',invalidMessage:'长度在8-25字符之间!',tipPosition:'right'"></input>
				</tr>
				<tr>
					<td>事由:</td>
					<td><input disabled="disabled" class="easyui-validatebox"
						id="updateevent" name="event"
						data-options="required:true,validType:'length[2,25]',missingMessage:'输入数据不能为空!',invalidMessage:'长度在8-25字符之间!',tipPosition:'right'"></input>
					</td>
				</tr>
				<tr>
					<td>总金额:</td>
					<td><input disabled="disabled" class="easyui-validatebox"
						id="updatetotalAccount" name="totalAccount"
						data-options="required:true,validType:'length[2,25]',missingMessage:'输入数据不能为空!',invalidMessage:'长度在8-25字符之间!',tipPosition:'right'"></input>
					</td>
				</tr>
				<tr>
					<td>审核理由:</td>
					<td><input class="easyui-validatebox" id="updatecomm"
						name="comm"
						data-options="required:true,validType:'length[2,25]',missingMessage:'输入数据不能为空!',invalidMessage:'长度在8-25字符之间!',tipPosition:'right'"></input>
					</td>
				</tr>
				<input id="ispass" name="ispass" style="display: none;" value="yes" />
				<%--
				<tr>
					<td>状态:</td>
					<td><input class="easyui-validatebox" id="updatestatus"
						name="status"
						data-options="required:true,validType:'status',missingMessage:'输入数据不能为空!',invalidMessage:'请输入合法状态!',tipPosition:'right'"></input>
					</td>
				</tr>
			--%>
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
		<div>
			<select id="searchBy"
				style="background: transparent; border: 1px solid #ccc;">
				<option>按填报人查询:</option>
				<option>按待处理人查询:</option>
			</select> <select onchange="search(this)" id="search">
				<c:forEach items="${sessionScope.depts}" var="dept">
					<optgroup label="${dept.name}">
						<c:forEach items="${dept.emps}" var="emp">
							<option value="${emp.sn}">${emp.name}</option>
						</c:forEach>
					</optgroup>
				</c:forEach>
			</select>
		</div>
	</div>
</body>
</html>
