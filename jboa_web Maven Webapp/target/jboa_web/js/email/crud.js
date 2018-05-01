
function updatePerson() {
	var rows = $('#table').datagrid('getSelections');
			$('#updateDialog').show();
			// 弹出窗口
			$('#updateDialog').dialog({
				// 标题
				title : '人员修改窗口',
				// 是否是可折叠的
				collapsible : true,
				// 是否可最小化窗口
				minimizable : false,
				// 是否可最大化窗口
				maximizable : true,
				// 宽度 
				width : 500,
				// 高度
				height : 300,
				// 模式窗口
				modal : true,
				// 窗口下的按钮
				buttons : [ {
					text : '保存',
					iconCls : 'icon-ok',
					handler : function() {
						

						var data2= {"host": $("#host").val(),"port": $("#port").val(),"username": $("#username").val(),"password":$("#password").val()};
						// 表单提交
						$.ajax({
							// 表单提交的路径
							url : "email.do?method=update",
							data:data2,
							// 当请求成功以后
							success : function(data) {

								$('#updateDialog').dialog('close');
								$('#table').datagrid('reload');

								var rows = $('#table').datagrid('getSelections');
								var myindex = $('#table').datagrid('getRowIndex', rows[0]);
								$('#table').datagrid('updateRow', {
									index : myindex,
									row : {
										host : $("#host").val(),
										port : $("#port").val(),
										username : $("#username").val(),
										password : $("#password").val()
									}
								});
								$('#table').datagrid('clearSelections');
							}
						});
						
						
					}
				}, {
					text : '取消',
					iconCls : 'icon-cancel',
					handler : function() {
						$('#updateDialog').dialog('close');
					}
				} ]
			});
			
			$('#updatePerson').form('load', {
				host : rows[0].host,
				port : rows[0].port,
				username : rows[0].username,
				password : rows[0].password
			});
		}



