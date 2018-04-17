
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<script type="text/javascript" src="js/esl.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/easyui/themes/pepper-grinder/easyui.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
<script type="text/javascript" src="js/ClaimVouyear/search.js"></script>
<script type="text/javascript" src="js/ClaimVouyear/echarts.js"></script></head>

<script type="text/javascript">
	/*    $("#selIndustyType option[value='1']").attr("selected", "selected"); 
	 */
	$(function() {
		// 生成表格
		$('#table').datagrid(
				{
					// 表格的标题
					title : '人员信息维护',
					// 表格标题前面的图标
					iconCls : 'icon-save',
					// 宽度
					width : '100%',
					// 高度
					height : 250,
					// 窗口是否可以收缩
					collapsible : false,
					// 请求数据的url
					url : 'ClaimVouyear.do?method=AllClaimVouyear',
					// 行阴影，但目前看没有效果
					striped : true,
					//自适应列宽
					fitColumns : true,
					// 拍序列
					sortName : 'totalCount',
					// 窗口自动大小
					fit : false,
					// 排序规则
					sortOrder : 'desc',
					// 本地排序，不是在服务器端排
					remoteSort : false,
					// 这个其实和column差不多，只不过是放在表格的左边
					frozenColumns : [ [ {
						field : 'id',
						checkbox : true,
						hidden : true
					}, {
						title : '编号',
						field : 'id',
						width : 75,
						sortable : true,
						resizable : false,
						hidden : true
					} ] ],
					// 标题
					columns : [ [
					/*{colspan:5},
					{title:'总金额',colspan:3,width:130,align:'center',resizable:false}
					],[ */
					{
						title : '填报人',
						field : 'emp',
						width : 130,
						resizable : false,
						formatter : function(value, row, index) {

							if (value.name) {
								return value.name;

							} else {
								return value;
							}
						}
					}, {
						title : '报销总额',
						field : 'totalCount',
						width : 130,
						fit : true,
						resizable : false
					}, {
						title : '年份',
						field : 'year',
						width : 130,
						resizable : false
					}, {
						title : '部门',
						field : 'dept',
						width : 130,
						resizable : false,
						formatter : function(value, row, index) {
							if (value.dept.name) {
								return value.dept.name;
							} else {
								return value;
							}
						}
					},

					] ],
					// 待选分页数据条数
					pageList : [ 5, 10, 20 ],
					// 每页显示数据条数       
					pageSize : 5,
					// 分页
					pagination : true,
					// 在左侧是否显示行号
					rownumbers : true,
					// 工具栏
					toolbar : [
							{
								id : 'search',
								text : '全部数据',
								iconCls : 'icon-search',
								handler : function() {
									var queryParams = $('#table').datagrid(
											'options').queryParams;
									queryParams.name = "";
									queryParams.nextDealSn = "";
									$('#table').datagrid('load');
									$('#message').searchbox('setValue', '');
								}
							},
							{
								id : 'print',
								text : '打印当前页PDF报表',
								iconCls : 'icon-print',
								handler : function() {
									var grid = $('#table');
									var options = grid.datagrid('getPager')
											.data("pagination").options;
									var curr = options.pageNumber;
									var total = options.pageSize;
									/* 	window.location.href = "http://localhost:8080/jboa_web/ClaimVoucher.do?method=getpdfReport&pagesize="
												+ curr
												+ "&pagenum="
												+ total + ""; */
									var item = $('#table').datagrid('getRows');
									post("ClaimVoucher.do?method=getpdfReport",
											item);
								}
							}
					]
				});
				
	    $(".datagrid-toolbar").append($("#testa"));
		// 显示分页信息的底部显示栏
		var p = $('#table').datagrid('getPager');

		$(p).pagination({
			// 每页显示多少条记录
			pageSize : 5,
			// 显示分页信息的文字   
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});


	});


</script>
<body>
	<div id="table"></div>
	<div id="main" style="height:250"></div>
	<div id="testa" align="right" style=":margin-top:  3px;">
		<div>
			<select id="selectType" onchange="dong()">
				<option selected="selected">--选择查询方式--</option>
				<option value="1">按年度查询</option>
				<option value="2">按报销总额查询</option>
			</select> <select id="select" onchange="selectChange(this)"></select>

		</div>
	</div>
</body>
</html>
