$(function() {
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
								url : 'power.do?method=getEmpRole',
								// 行阴影，但目前看没有效果
								striped : true,
								//自适应列宽
								fitColumns : true,
								// 拍序列
							/*	sortName : 'sn',*/
								// 窗口自动大小
								fit : true,
								// 排序规则
								sortOrder : 'desc',
								// 本地排序，不是在服务器端排
								remoteSort : false,
							
								// 标题
								columns : [ [
								{
									title : '员工编号',
									field : 'sn',
									width : 130,
									hidden : true
								},
								  {
									title : '员工名',
									field : 'name',
									width : 130,
									resizable : false
								},{
									title : '部门名',
									field : 'dept',
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
								},
								  {
									title : '拥有角色',
									field : 'rolesdescribes',
									width : 130,
									resizable : false
								}
								
								] ],
								// 待选分页数据条数
								pageList : [ 5, 10, 20 ],
								// 每页显示数据条数       
								pageSize : 5,
								// 分页
								pagination : true,
								// 在左侧是否显示行号
								rownumbers : true
								// 工具栏
						
							});
							

	});