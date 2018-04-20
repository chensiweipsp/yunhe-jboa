

function updatePerson() {
	var rows = $('#table').datagrid('getSelections');


	$("#addcreateSn option[value='" + rows[0].createSn.sn + "']").attr(
			"selected", "selected");

	$("#updatenextDealSn option[value='" + rows[0].nextDealSn.sn + "']")
	.attr("selected", "selected");

	// 让默认看不到的层显示出来   
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
			text : '通过',
			iconCls : 'icon-ok',
			handler : function() {
				$('#pass').submit();
			}
		}, 
		{
			text : '拒绝',
			iconCls : 'icon-ok',
			handler : function() {
				$("#ispass").val("no");
				$('#pass').submit();
			}
		},
		{
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#updateDialog').dialog('close')			}
		} ]
	});

	$('#pass').form('load', {
		taskid: rows[0].taskid,
		id: rows[0].id,
		createName:rows[0].createSn.name,
		createSn : rows[0].createSn.sn,
		nextDealSn : rows[0].nextDealSn.sn, 
		createTime : dispalyformatterdate(rows[0].createTime),
		event : rows[0].event,
		totalAccount : rows[0].totalAccount,
		status : rows[0].status
	});

}
