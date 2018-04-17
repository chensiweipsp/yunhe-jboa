<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>北大青鸟办公自动化管理系统</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

body {
	font: 12px 宋体;
	background: #4BB8EF url(images/bg.gif) repeat-x;
}

img {
	border: 0;
}

.login-top {
	width: 100%;
	height: 186px;
	margin: 147px auto 0;
	background: url(images/login_01.gif) no-repeat center 0;
}

.login-area {
	width: 100%;
	height: 140px;
	margin: 0 auto;
	background: url(images/login_02.gif) no-repeat center 0;
}

.login-area form {
	width: 290px;
	margin: 0 auto;
}

.login-area label {
	clear: left;
	float: left;
	margin-top: 13px;
	width: 60px;
	font: 600 14px 宋体;
}

.login-area  input {
	width: 122px;
	height: 16px;
	margin-top: 11px;
	border: 1px #767F94 solid;
	font: 12px/16px 宋体;
}

input.login-sub {
	width: 104px;
	height: 34px;
	border: 0;
	background: url(images/login_sub.gif) no-repeat 0px 1px; *
	margin-top: 5px;
}

.login-copyright {
	width: 100%;
	height: 30px;
	margin: 18px auto 0;
	background: url(images/copyright.gif) no-repeat center 0;
}
</style>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript">
            <%
            String msg =(String)request.getAttribute("msg");
			if ("loginError".equals(msg)) {
				out.print("alert('用户名密码错误，请重新登录');");
			}
			if ("validateCodeError".equals(msg)) {
				out.print("alert('验证码错误，请重新输入');");
			}%>
</script>
<body>
	<div class="login-top"></div>
	<div class="login-area">
		<form action="emp.do?method=login" method="post" >
			<table>
				<tr>
					<td>工号:</td>
					<td><input type="text" name="username" />
					</td>
				</tr>
				<td>密码:</td>
				<td><input type="password" name="password" />
				</td>
				<tr>
					<td>验证码:</td>
					<td><input type="text" name="random" size="6" /> <img
						id="validateCode" src="jsp/Number.jsp" width="66" height="27" alt="" />
						<a id="changeCode" href="javascript:void(0);" title="">看不清</a></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" class="login-sub"
						value="" /></td>
				</tr>

			</table>
		</form>
	</div>
	<div class="login-copyright"></div>
</body>
</html>