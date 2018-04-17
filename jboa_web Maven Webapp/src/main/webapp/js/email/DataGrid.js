$(function() {
	// 生成表格
	$('#table').datagrid({
		// 表格的标题
		title : '邮箱服务器维护',
		// 表格标题前面的图标
		iconCls : 'icon-save',
		// 宽度
		width : 700,
		// 高度
		height : 350,
		// 窗口是否可以收缩
		collapsible : false,
		// 请求数据的url
		url : 'email.do?method=getemail',
		// 行阴影，但目前看没有效果
		striped : true,
		// 自适应列宽
		fitColumns : true,
		// 拍序列
		/* sortName : 'sn', */
		// 窗口自动大小
		fit : true,
		// 排序规则
		sortOrder : 'desc',
		// 本地排序，不是在服务器端排
		remoteSort : false,

		// 标题
		columns : [ [ {
			title : '邮箱服务器',
			field : 'host',
			width : 130,
		}, {
			title : '端口号',
			field : 'port',
			width : 130,
			resizable : false
		}, {
			title : '用户名',
			field : 'username',
			width : 130,
			resizable : false
		}, {
			title : '密码',
			field : 'password',
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
		toolbar : [ {
			id : 'update',
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				var rows = $('#table').datagrid('getSelections');
				if (rows.length != 1) {
					$.messager.alert('提示', '请选中一条您要修改的记录');
				} else {
					updatePerson();
				}
			}
		}

		]

	});

});



