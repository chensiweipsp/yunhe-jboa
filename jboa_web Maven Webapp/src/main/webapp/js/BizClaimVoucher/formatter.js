	// easyui datagrid dateFormatter
		function formatterdate(val, row) {
			if (val != null) {
				var date = new Date(val);
				return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
						+ date.getDate();
			}
		}
		// easyui datagrid dateFormatter
		function dispalyformatterdate(val) {
			if (val != null) {
				var date = new Date(val);
				return (date.getMonth() + 1) + '/' + date.getDate() + '/'
						+ date.getFullYear();
			}
		}