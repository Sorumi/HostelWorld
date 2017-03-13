<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/28
  Time: 下午2:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/hostel-nav.jsp" %>

<main>
    <div class="container card">
        <h1>申请详情</h1>
        <div class="grid">
            <div class="grid-row">
                <div class="grid-label">
                    <label for="id">申请编号</label>
                </div>
                <div class="grid-content">
                    <span id="id">${application.ID}</span>
                </div>
            </div>

            <div class="grid-row">
                <div class="grid-label">
                    <label for="appliedTime">申请时间</label>
                </div>
                <div class="grid-content">
                    <span id="appliedTime">${application.appliedTime}</span>
                </div>
            </div>

            <div class="grid-row">
                <div class="grid-label">
                    <label for="checkedTime">审核时间</label>
                </div>
                <div class="grid-content">
                    <span id="checkedTime">${application.checkedTime}</span>
                </div>
            </div>
            <%--<div class="grid-row">--%>
                <%--<div class="grid-label">--%>
                    <%--<label for="user">申请人</label>--%>
                <%--</div>--%>
                <%--<div class="grid-content">--%>
                    <%--<span id="user">sorumi</span>--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="state">状态</label>
                </div>
                <div class="grid-content">
                    <span id="state" class="tag tag-${application.state.color}-current">${application.state.name}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="name">酒店名称</label>
                </div>
                <div class="grid-content">
                    <span id="name">${application.name}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="city">酒店城市</label>
                </div>
                <div class="grid-content">
                    <span id="city" class="tag tag-green-current">${application.city.name}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="address">酒店地址</label>
                </div>
                <div class="grid-content">
                    <span id="address">${application.address}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="introduction">酒店简介</label>
                </div>
                <div class="grid-content">
                    <p id="introduction">${application.introduction}</p>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="facility">服务设施</label>
                </div>
                <div class="grid-content">
                    <span id="facility">${application.facility}</span>
                </div>
            </div>
        </div>
        <button class="minor-button" type="button" onclick="history.back()">返回</button>
    <%--<div class="book-submit">--%>
            <%--<button class="major-button">通过</button>--%>
            <%--<button class="minor-button">拒绝</button>--%>
        <%--</div>--%>
        <div class="clear-fix"></div>
    </div>
</main>

<%@ include file="include/footer.jsp" %>