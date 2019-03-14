<%--
  Created by IntelliJ IDEA.
  User: mengchen
  Date: 2018/2/8
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<form action="/user/register" method="post">
    username:<input type="text" name="username" /><br/>
    account：<input type="text" name="account" /><br/>
    password:<input type="text" name="password" /><br/>
    <input type="submit" value="注册" />
</form>
</body>
</html>
