<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/24
  Time: 下午9:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<%--<form:form commandName="member" method="POST" action="/member/add">--%>
    <%--<form:input path="name"/>--%>
    <%--<form:input path="ID"/>--%>
    <%--<input type="submit" value="Submit"/>--%>
<%--</form:form>--%>

<form method="post" action="/member/add">
    <input type="text" name="name">
    <input type="text" name="ID">
    <input type="submit">
</form>
</body>
</html>
