<%--
  Created by IntelliJ IDEA.
  User: JacKeTUs
  Date: 11.04.2018
  Time: 2:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<form method="post" action="register">
    <b>Имя пользователя</b>
    <p><input name="username"></p>
    <b>Пароль</b>
    <p><input type="password" name="password"></p>
    <b>Повторите пароль</b>
    <p><input type="password" name="password_r"></p>
    <p><button type="submit" name="register_button" value="register_button1">Зарегистрироваться</button></p>
</form>
</body>
</html>
