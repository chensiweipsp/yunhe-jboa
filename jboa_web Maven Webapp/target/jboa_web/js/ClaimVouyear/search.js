	function dong() {
		var getSheng = document.getElementById("selectType");
		var getShi = document.getElementById("select");
		var where = getSheng.selectedIndex;
		for ( var j = 0; j < getShi.length; j++) {//清除二级选项
			getShi.remove(j);//用的是select的remove方法，j是option的索引值
			j--;
		}
		if ($("#selectType option:selected").val() == 1) {
			var y2011 = document.createElement('option');
			y2011.value = "2011";
			y2011.text = "2011年";
			getShi.add(y2011);

			var y2012 = document.createElement('option');
			y2012.value = "2012";
			y2012.text = "2012年";
			getShi.add(y2012);

			var y2013 = document.createElement('option');
			y2013.value = "2013";
			y2013.text = "2013年";
			getShi.add(y2013);

			var y2014 = document.createElement('option');
			y2014.value = "2014";
			y2014.text = "2014年";
			getShi.add(y2014);

			var y2015 = document.createElement('option');
			y2015.value = "2015";
			y2015.text = "2015年";
			getShi.add(y2015);

			var y2016 = document.createElement('option');
			y2016.value = "2016";
			y2016.text = "2016年";
			getShi.add(y2016);

			var y2017 = document.createElement('option');
			y2017.value = "2017";
			y2017.text = "2017年";
			getShi.add(y2017);

			var y2018 = document.createElement('option');
			y2018.value = "2018";
			y2018.text = "2018年";
			getShi.add(y2018);

			var y2019 = document.createElement('option');
			y2019.value = "2019";
			y2019.text = "2019年";
			getShi.add(y2019);

			var y2020 = document.createElement('option');
			y2020.value = "2020";
			y2020.text = "2020年";
			getShi.add(y2020);

		}
		if ($("#selectType option:selected").val() == 2) {
			var y = document.createElement('option');
			y.text = "按报销总额从大到小排序";
			y.value = "1";
			getShi.add(y);
			var y2 = document.createElement('option');

			y2.text = "按报销总额从小到大排序";
			y2.value = "2";
			getShi.add(y2);
		}

	}
	function selectChange(data) {
		if (data.value == 1) {
		
				$('#table').datagrid({
				sortOrder : 'desc',
				toolbar:'testa'
			});
		var p = $('#table').datagrid('getPager');

		$(p).pagination({
			// 每页显示多少条记录
			pageSize : 5,
			// 显示分页信息的文字   
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		

		} else if (data.value == 2) {
		
		
				$('#table').datagrid({
				sortOrder : 'asc',
				toolbar:'testa'
			});
		var p = $('#table').datagrid('getPager');

		$(p).pagination({
			// 每页显示多少条记录
			pageSize : 5,
			// 显示分页信息的文字   
			beforePageText : '第',
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		
		
		}
		
		
		 else {
		}
	}