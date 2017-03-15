<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 下午12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav>
    <div class="container">
        <a id="nav-search" href="${basePath}/home">搜索酒店</a>
        <a id="nav-order" href="${basePath}/order">预定管理</a>
        <a id="nav-info" href="${basePath}/info">个人资料</a>
        <a id="nav-statistic" href="${basePath}/statistic">信息统计</a>
        <div class="clear-fix"></div>
    </div>
</nav>

<script>
    $('#${nav}').addClass('current');
</script>