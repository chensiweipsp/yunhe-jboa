
function addPerson() {

/*	$("#addselect option[value='" + cnname + "']").attr(
			"selected", "selected");*/
	// 让默认看不到的层显示出来   
	$('#dialog').show();
	// 弹出窗口
	$('#dialog').dialog({
		// 标题
		title : '人员添加窗口',
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
				$('#addperson').submit();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#dialog').dialog('close');
			}
		} ]
	});
}


function getJsonObjLength(jsonObj) {
	var Length = 0;
	for (var item in jsonObj) {
		Length++;
	}
	return Length;
}

function updatePerson() {
	var rows = $('#table').datagrid('getSelections');
	$.ajax({
		url:"ClaimVoucher.do?method=getnextDealSn&cnname=" +rows[0].createSn.sn+"",
		type:"post",
		dataType:"json",
		success: function (data)
		{
			//清空select
			$("#updatenextDealSn").empty(); 
			$("#updatenextDealSn").append("<optgroup label='"+data.name+"'/>");
			/*			alert(Object.keys(data.emps[0]).size);
			 */
			for(var i=0;i< data.empsLength;i++)
			{
				$("#updatenextDealSn").append("<option value='"+data.emps[i].sn+"'>"+data.emps[i].name+"</option>");
			}
		/*	$("#addcreateSn option[value='" + rows[0].createSn.sn + "']").attr(
					"selected", "selected");*/
			
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
					text : '保存',
					iconCls : 'icon-ok',
					handler : function() {
						$('#updatePerson').submit();
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
				id : rows[0].id,
				createSn : rows[0].createSn.sn,
				nextDealSn : rows[0].nextDealSn.sn,
				createTime : dispalyformatterdate(rows[0].createTime),
				event : rows[0].event,
				totalAccount : rows[0].totalAccount,
				status : rows[0].status,
				taskid:rows[0].taskid
			});
		}
	,
	error:function()
	{
		alert("网络异常");
	}
	});
}





function showStatus()
{
	var rows = $('#table').datagrid('getSelections');

	var id=rows[0].id;
	var taskid=rows[0].taskid;

	var temp = document.createElement("form");        
	temp.action = "workflowAction.do?method=getStatus&taskid="+taskid+"";        
	temp.method = "post";        
	temp.style.display = "none";        
	var size=0; 

	var opt = document.createElement("textarea");     
	opt.name="id";
	opt.value =  id;
	temp.appendChild(opt);        

	document.body.appendChild(temp);        
	temp.submit();        

}


