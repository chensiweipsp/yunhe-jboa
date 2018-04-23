
$(function() {


	var scriptArgs = document.getElementById('testScript').getAttribute('data');

	// 生成表格
	$('#table')
	.datagrid(
			{
				// 表格的标题
				title : '人员信息维护',
				// 表格标题前面的图标
				iconCls : 'icon-save',
				// 宽度
				width : 700,
				// 高度
				height : 350,
				// 窗口是否可以收缩
				collapsible : false,
				// 请求数据的url
				url : 'ClaimVoucher.do?method=ClaimVouchershow&permissions='+scriptArgs+',',
				// 行阴影，但目前看没有效果
				striped : true,
				//自适应列宽
				fitColumns : true,
				// 拍序列
				sortName : 'code',
				// 窗口自动大小
				fit : true,
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

					/*	{
									title : '填报人',
									field : 'create',
									width : 130,
									fit : true,
									resizable : false,
									hidden : true,
									formatter : function(value, row, index) {
										if (value.sn) {
											return value.sn;

										} else {
											return value;
										}
									}
								},*/
					{
						title : 'taskid',
						field : 'taskid',
						width : 130,
						fit : true,
						resizable : false,
						hidden:true
					},
					{
						title : '填报人',
						field : 'createSn',
						width : 130,
						fit : true,
						resizable : false,
						formatter : function(value, row, index) {
							if (value.name) {
								return value.name;

							} else {
								return value;
							}
						}
					}, {
						title : '待处理人',
						field : 'nextDealSn',
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
						title : '填写时间',
						field : 'createTime',
						width : 130,
						formatter : formatterdate,
						resizable : false
					}, {
						title : '事由',
						field : 'event',
						width : 130,
						resizable : false
					}, {
						title : '总金额',
						field : 'totalAccount',
						width : 130,
						resizable : false
					}, {
						title : '状态',
						field : 'status',
						width : 130,
						resizable : false
					} ] ],
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
							id : 'add',
							// 工具条上显示的文字
							text : '添加',
							// 图标
							iconCls : 'icon-add',
							// 单击图标时显示的事件
							handler : function() {
								addPerson();
							}
						},
						{
							id : 'update',
							text : '修改',
							iconCls : 'icon-edit',
							handler : function() {
								var rows = $('#table').datagrid(
								'getSelections');
								if (rows.length != 1) {
									$.messager.alert('提示',
									'请选中一条您要修改的记录');
								} else {
									updatePerson();
								}
							}
						},
						{
							id : 'delete',
							text : '删除',
							disabled : false,
							iconCls : 'icon-remove',
							handler : function() {
								var rows = $('#table').datagrid(
								'getSelections');
								var deleteVar = "";
								if (rows.length == 0) {
									$.messager.alert('提示',
									'选中要删除的记录');
								} else {
									for ( var i = 0; i < rows.length; i++) {
										if (i == (rows.length - 1)) {
											deleteVar += rows[i].id;
										} else {
											deleteVar += rows[i].id
											+ ",";
										}
									}
									$
									.post(
											"ClaimVoucher.do?method=delete",
											{
												deleteIDs : deleteVar
											},
											function(result) {
												var options = $(
												'#table')
												.datagrid(
												'getPager')
												.data(
												"pagination").options;
												var currentPageNumber = options.pageNumber;
												var rowSize = $(
												'#table')
												.datagrid(
												'getRows').length;
												if (currentPageNumber != 1
														&& rowSize == rows.length) {
													var queryParams = $(
													'#table')
													.datagrid(
													'options').queryParams;
													queryParams.previous = 'true';
													$(
													'#table')
													.datagrid(
													'reload');
													queryParams.previous = '';
												} else {
													$(
													'#table')
													.datagrid(
													'reload');
												}
											});
								}
							}
						},
						{
							id : 'search',
							text : '全部数据',
							iconCls : 'icon-search',
							handler : function() {
								var queryParams = $('#table')
								.datagrid('options').queryParams;
								queryParams.name = "";
								queryParams.nextDealSn = "";
								$('#table').datagrid('load');
								$('#message').searchbox('setValue',
								'');
							}
						},
						{
							id : 'gitStatus',
							text : '查看报销单状态流程',
							iconCls : 'icon-search',
							handler : function() {
								var rows = $('#table').datagrid(
								'getSelections');
								if (rows.length != 1) {
									$.messager.alert('提示',
									'请选中一条您要查看报销单的记录');


								}else if(rows[0].status == "已通过审核")
								{
									$.messager.alert('提示',
									'该报销单已经通过了审核');
								}

								else {
									showStatus();
								}
							}
						},

						{
							id : 'print',
							text : '打印当前页PDF报表',
							iconCls : 'icon-print',
							handler : function() {
								var grid = $('#table');
								var options = grid.datagrid(
								'getPager').data(
								"pagination").options;
								var curr = options.pageNumber;
								var total = options.pageSize;
								/* 	window.location.href = "http://localhost:8080/jboa_web/ClaimVoucher.do?method=getpdfReport&pagesize="
														+ curr
														+ "&pagenum="
														+ total + ""; */
								var item = $('#table').datagrid('getRows');


								post("ClaimVoucher.do?method=getpdfReport",item);       

							}
						},



						{
							id : 'print',
							text : '打印当前页XLS报表',
							iconCls : 'icon-print',
							handler : function() {
								var grid = $('#table');
								var options = grid.datagrid(
								'getPager').data(
								"pagination").options;
								var curr = options.pageNumber;
								var total = options.pageSize;
								/*	window.location.href = "http://localhost:8080/jboa_web/ClaimVoucher.xls?method=getxlsReport&pagesize="
														+ curr
														+ "&pagenum="
														+ total + "";*/

								var item = $('#table').datagrid('getRows');


								post("ClaimVoucher.xls?method=getxlsReport",item);    
							}
						},
						{
							id : 'print',
							text : '打印当前页HTML报表',
							iconCls : 'icon-print',
							handler : function() {
								var grid = $('#table');
								var options = grid.datagrid(
								'getPager').data(
								"pagination").options;
								var curr = options.pageNumber;
								var total = options.pageSize;
								/*	window.location.href = "http://localhost:8080/jboa_web/ClaimVoucher.do?method=gethtmlReport&pagesize="
														+ curr
														+ "&pagenum="
														+ total + "";*/

								var item = $('#table').datagrid('getRows');


								post("ClaimVoucher.do?method=gethtmlReport",item); 
							}
						},
						{
							id : 'print',
							text : '打印当前页CVS报表',
							iconCls : 'icon-print',
							handler : function() {
								var grid = $('#table');
								var options = grid.datagrid(
								'getPager').data(
								"pagination").options;
								var curr = options.pageNumber;
								var total = options.pageSize;
								/*	window.location.href = "http://localhost:8080/jboa_web/ClaimVoucher.csv?method=getcvsReport&pagesize="
														+ curr
														+ "&pagenum="
														+ total + "";*/

								var item = $('#table').datagrid('getRows');


								post("ClaimVoucher.csv?method=getcvsReport",item); 
							}
						}

						]
			});

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


	$(".datagrid-toolbar").append($("#testa"));





	// 表单提交
	$('#addperson').form({
		// 表单提交的路径
		url : "ClaimVoucher.do?method=saveorUpdate",
		// 表单提交前
		onSubmit : function() {
			// 判断所有的验证是不是通过啦
			var isValid = $(this).form('validate');
			return isValid;
		},
		// 当请求成功以后
		success : function(data) {

			$('#dialog').dialog('close');
			$('#table').datagrid('load');
			$('#addperson').form("clear");
		}
	});

	// 表单提交
	$('#updatePerson').form({
		// 表单提交的路径
		url : "ClaimVoucher.do?method=saveorUpdate",
		// 表单提交前
		onSubmit : function() {
			// 判断所有的验证是不是通过啦
			var isValid = $(this).form('validate');
			return isValid;
		},
		// 当请求成功以后
		success : function(data) {

			var mytotalAccount = $("#updatetotalAccount").attr("checked");
			var mystatus = $("#updatestatus").attr("checked");
			var mypingpang = $("#updatepingpang").attr("checked");

			if (mytotalAccount == 'checked') {
				mytotalAccount = true;
			} else {
				mytotalAccount = false;
			}

			if (mystatus == 'checked') {
				mystatus = true;
			} else {
				mystatus = false;
			}

			if (mypingpang == 'checked') {
				mypingpang = true;
			} else {
				mypingpang = false;
			}

			$('#updateDialog').dialog('close');
			$('#table').datagrid('reload');

			var rows = $('#table').datagrid('getSelections');
			var myindex = $('#table').datagrid('getRowIndex', rows[0]);
			$('#table').datagrid('updateRow', {
				index : myindex,
				row : {
					createSn : $("#updatecreateSn").val(),
					nextDealSn : $("#updatenextDealSn").val(),
					updatestatus : $("#updatestatus").val(),
					createTime : $("#updatecreateTime").val(),
					totalAccount : $("#updatetotalAccount").val(),
					status : $("#updatestatus").val(),
					event : $("#updateevent").val()
				}
			});
			$('#table').datagrid('clearSelections');
		}
	});

});