<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 下午12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path ;
    request.setAttribute("basePath", basePath);
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hostel World</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/flatpickr.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/magnific-popup.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/font-awesome.min.css"/>

    <script src="${basePath}/js/jquery-3.1.1.min.js"></script>
    <script src="${basePath}/js/jquery.number.js"></script>
    <script src="${basePath}/js/flatpickr.min.js"></script>
    <script src="${basePath}/js/number-picker.js"></script>
</head>
<body>

<header>
    <div class="container">
        <a class="title" href="#">Hostel World</a>
        <div class="nav">
            <div class="nav">
                <c:if test="${member == null && hostel == null && manager == null}" >
                    <a href="${basePath}/hostel/register">旅舍注册</a>
                    <a href="${basePath}/hostel/login">旅舍登录</a>

                    <a href="${basePath}/register">注册</a>
                    <a href="${basePath}/login">登录</a>
                </c:if>
                <c:if test="${member != null}" >
                    <a href="${basePath}/logout">注销</a>
                    <a href="${basePath}/info">个人</a>
                </c:if>
                <c:if test="${hostel != null}" >
                    <a href="${basePath}/hostel/logout">注销</a>
                    <a href="${basePath}/hostel/room">管理</a>
                </c:if>
                <c:if test="${manager != null}" >
                    <a href="${basePath}/admin/logout">注销</a>
                </c:if>
            </div>
        </div>
    </div>
</header>