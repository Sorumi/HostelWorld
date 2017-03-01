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
            <a href="/hostel/info">酒店信息</a>
            <a href="/hostel/room">房间管理</a>
            <a href="/hostel/order">预定管理/入离登记</a>
        </c:if>
        <a href="/hostel/application">申请管理</a>
        <c:if test="${hostel.state != 'Unopened'}">
            <a href="/hostel/statistics">信息统计</a>
        </c:if>
        <div class="clear-fix"></div>
    </div>
</nav>