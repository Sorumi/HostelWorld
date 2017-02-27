<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 下午12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path ;
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hostel World</title>
    <link rel="stylesheet" type="text/css" href="<%= basePath %>/css/flatpickr.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%= basePath %>/css/main.css"/>

    <script src="<%= basePath %>/js/jquery-3.1.1.min.js"></script>
    <script src="<%= basePath %>/js/jquery.validate.js"></script>
    <script src="<%= basePath %>/js/flatpickr.min.js"></script>
</head>
<body>

<header>
    <div class="container">
        <a class="title" href="#">Hostel World</a>
        <div class="nav">
            <%--<a href="/logout">登出</a>--%>
            <%--<span>一个图标</span>--%>
        </div>
    </div>
</header>