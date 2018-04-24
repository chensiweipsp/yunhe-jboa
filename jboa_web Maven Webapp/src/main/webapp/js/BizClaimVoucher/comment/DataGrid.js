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
				url : 'workflowAction.do?method=getComment&ByClaimVoucherId='+scriptArgs+'',
				// 行阴影，但目前看没有效果
				striped : true,
				//自适应列宽
				fitColumns : true,
				// 拍序列
				sortName : 'id',
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
					{
						title : '批注人',
						field : 'type',
						width : 130,
						fit : true,
						resizable : false,
					},
					{
						title : '批注内容',
						field : 'fullMessage',
						width : 130,
						fit : true,
						resizable : false,
					},
					 {
						title : '审核时间',
						field : 'time',
						width : 130,
						formatter : formatterdate,
						resizable : false
					}] ],
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
							id : 'back',
							text : '回退',
							iconCls : 'icon-back',
							handler : function() {
								history.back(-1);
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
	$('#pass').form({
		// 表单提交的路径
		url : "ClaimVoucher.do?method=ClaimVouchercomm",
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
					taskid: $("#taskid").val(),
					createName: $("#updatecreateName").val(),
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

