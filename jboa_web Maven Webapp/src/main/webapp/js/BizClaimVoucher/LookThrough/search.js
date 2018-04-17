function search(data) {
		/* 		  alert(data.value);
		 alert($("#searchBy").val());	 */
		var searchby = $("#searchBy").val();
		if (searchby == "按填报人查询:") {
			$.ajax({
				url : "ClaimVoucher.do?method=CheckClaimVouchershow&createSn="
						+ data.value + "&page=1&rows=999999",
				type : "get",
				/*如果不加上datatype=json 则JS 无法使用data.total 这种方式去解析JSON  */
				dataType : "json",
				success : function(data) {

					var item = $('#table').datagrid('getRows');
					if (item) { 
						for ( var i = item.length - 1; i >= 0; i--) {
							var index = $('#table').datagrid('getRowIndex',
									item[i]);
							$('#table').datagrid('deleteRow', index);
						}
					}

					if (0 == data.rows.length) {
						$('#table').datagrid('load');
						alert("没有相关的信息");
					} else {
						for ( var i = 0; i <= data.rows.length - 1; i++) {
							var row_data = {
								id : data.rows[i].id,
								createSn : data.rows[i].createSn,
								nextDealSn : data.rows[i].nextDealSn,
								createTime : data.rows[i].createTime,
								event : data.rows[i].event,
								totalAccount : data.rows[i].totalAccount,
								status : data.rows[i].status,
								modifyTime : data.rows[i].modifyTime,

							};
							$('#table').datagrid('appendRow', row_data);
						}

					}

				},
				error : function() {
					alert("网络异常");
				}

			});
		} else {
			$.ajax({
				url : "ClaimVoucher.do?method=CheckClaimVouchershow&nextDeal="
						+ data.value + "&page=1&rows=999999",
				type : "get",
				/*如果不加上datatype=json 则JS 无法使用data.total 这种方式去解析JSON  */
				dataType : "json",
				success : function(data) {
					var item = $('#table').datagrid('getRows');
					if (item) {
						for ( var i = item.length - 1; i >= 0; i--) {
							var index = $('#table').datagrid('getRowIndex',
									item[i]);
							$('#table').datagrid('deleteRow', index);
						}
					}

					if (0 == data.rows.length) {
						$('#table').datagrid('load');
						alert("没有相关的信息");
					} else {
						for ( var i = 0; i <= data.rows.length - 1; i++) {
							var row_data = {
								id : data.rows[i].id,
								createSn : data.rows[i].createSn,
								nextDealSn : data.rows[i].nextDealSn,
								createTime : data.rows[i].createTime,
								event : data.rows[i].event,
								totalAccount : data.rows[i].totalAccount,
								status : data.rows[i].status,
								modifyTime : data.rows[i].modifyTime,

							};
							$('#table').datagrid('appendRow', row_data);
						}

					}

				},
				error : function() {
					alert("网络异常");
				}

			});
		}

	}