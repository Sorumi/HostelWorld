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
        <a id="nav-application" href="${basePath}/admin/application">申请管理</a>
        <a id="nav-order" href="${basePath}/admin/order">预定管理</a>
        <a id="nav-level" href="${basePath}/admin/level">等级管理</a>
        <a id="nav-hostel" href="${basePath}/admin/statistic/hostel">旅舍统计</a>
        <a id="nav-member" href="${basePath}/admin/statistic/member">会员统计</a>
        <a id="nav-money" href="${basePath}/admin/statistic/money">财务统计</a>
        <div class="clear-fix"></div>
    </div>
</nav>

<script>
    $('#${nav}').addClass('current');
</script>