var myChart = null;
	var names = []; //类别数组（实际用来盛放X轴坐标值）
	var serie = []; //销量数组（实际用来盛放Y坐标值）

	$.ajax({
		type : "post",
		async : true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		url : "ClaimVouyear.do?method=AllClaimVouyear", //请求发送到TestServlet处
		data : {},
		dataType : "json", //返回数据形式为json
		success : function(result) {
			//请求成功时执行该函数内容，result即为服务器返回的json对象
			if (result) {

				for ( var i = 0; i < result.rows.length; i++) {
					names.push(result.rows[i].emp.name); //挨个取出类别并填入类别数组
				}

				for ( var i = 0; i < result.rows.length; i++) {
					var item = {
						name : result.rows[i].emp.name,
						value : result.rows[i].totalCount
					};
					serie.push(item);
				}

				// 路径配置
				require.config({
					paths : {
						'echarts' : 'js/echarts',
						'echarts/chart/pie' : 'js/echarts'
					}
				});

				// 使用
				require([ 'echarts', 'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
				], function(ec) {
					// 基于准备好的dom，初始化echarts图表
					myChart = ec.init(document.getElementById('main'));

					option = {
						title : {
							text : '部门报销年度统计',
							subtext : '员工年度报销总额',
							x : 'center'
						},
						tooltip : {
							trigger : 'item',
							formatter : "{a} <br/>{b} : {c} ({d}%)"
						},
						legend : {
							orient : 'vertical',
							x : 'left',
							data : names
						},
						toolbox : {
							show : true,
							feature : {
								mark : {
									show : true
								},
								dataView : {
									show : true,
									readOnly : false
								},
								restore : {
									show : true
								},
								saveAsImage : {
									show : true
								}
							}
						},
						calculable : true,
						series : [ {
							name : '年度报销总额',
							type : 'pie',
							radius : '65%',
							center : [ '50%', '60%' ],

							data : serie,

							itemStyle : {
								emphasis : {
									shadowBlur : 10,
									shadowOffsetX : 0,
									shadowColor : 'rgba(0, 0, 0, 0.5)'
								}
							}
						} ]
					};

					// 为echarts对象加载数据 
					myChart.setOption(option);
				});

			}

		},
		error : function(errorMsg) {
			//请求失败时执行该函数
			alert("图表请求数据失败!");
			myChart.hideLoading();
		}
	});