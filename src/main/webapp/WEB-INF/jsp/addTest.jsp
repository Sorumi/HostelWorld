<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 上午10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/member/test/add" method="post">
    <input type="text" name="name">
    <input type="text" name="nums[0]">
    <input type="text" name="nums[1]">
    <input type="submit" value="submit">
</form>
</body>
</html>
