<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	String path = request.getContextPath();
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 	<form action="<%=path %>/user/add" method="post">
 		<table>
 			<tr>
 				<td>用户名</td>
 				<td>
 					<input type="text" name="userName" />
 				</td>
 			</tr>
 			<tr>
 				<td>性别</td>
 				<td>
 					<input type="text" name="sex" />
 				</td>
 			</tr>
 			<tr>
 				<td>邮箱</td>
 				<td>
 					<input type="text" name="email" />
 				</td>
 			</tr>
 			<tr>
 				<td>年龄</td>
 				<td>
 					<input type="text" name="age" />
 				</td>
 			</tr>
 			<tr>
 				<td colspan="2">
 					<input type="submit" value="提交">
 				</td>
 			</tr>
 		</table>
 	</form>
</body>
</html>