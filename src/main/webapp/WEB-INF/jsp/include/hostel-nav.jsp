<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 下午12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav>
    <div class="container">
        <c:if test="${hostel.state != 'Unopened'}">
            <a href="${basePath}/hostel/info">酒店信息</a>
            <a href="${basePath}/hostel/room">房间管理</a>
            <a href="${basePath}/hostel/order">预定管理</a>
        </c:if>
        <a href="${basePath}/hostel/application">申请管理</a>
        <c:if test="${hostel.state != 'Unopened'}">
            <a href="${basePath}/hostel/statistic">信息统计</a>
        </c:if>
        <div class="clear-fix"></div>
    </div>
</nav>