<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="../../js/easyui/themes/pepper-grinder/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="../../js/jquery-easyui-1.5.1/demo/demo.css" type="text/css"></link>
</head>
<body>
	<h2>Reports using TreeGrid</h2>
	<p>Using TreeGrid to show complex reports.</p>
	<div style="margin:20px 0;"></div>
	<table title="Reports using TreeGrid" class="easyui-treegrid"
		style="width:700px;height:250px"
		data-options="
    				url: '<%=basePath %>ClaimVoucher.do?method=CheckClaimVouchershow',
    				method: 'get',
    				rownumbers: true,
    				showFooter: true,
    				idField: 'id',
    				treeField: 'region'
    			">
		<thead frozen="true">
			<tr>
				<th field="region" width="200">Region</th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th colspan="4">2009</th>
				<th colspan="4">2010</th>
			</tr>
			<tr>
				<th field="f1" width="60" align="right">1st qrt.</th>
				<th field="f2" width="60" align="right">2st qrt.</th>
				<th field="f3" width="60" align="right">3st qrt.</th>
				<th field="f4" width="60" align="right">4st qrt.</th>
				<th field="f5" width="60" align="right">1st qrt.</th>
				<th field="f6" width="60" align="right">2st qrt.</th>
				<th field="f7" width="60" align="right">3st qrt.</th>
				<th field="f8" width="60" align="right">4st qrt.</th>
			</tr>
		</thead>
	</table>

</body>
</html>