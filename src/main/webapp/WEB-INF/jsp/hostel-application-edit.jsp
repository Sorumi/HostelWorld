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
    <div class="container card member-info">
        <h1 class="title">申请修改信息</h1>
        <c:choose>
            <c:when test="${application.type == 'Open'}">
                <form action="${basePath}/hostel/application/open/add" method="post" autocomplete="off" id="add-form">
            </c:when>
            <c:otherwise>
                <form action="${basePath}/hostel/application/edit/add" method="post" autocomplete="off" id="add-form">
            </c:otherwise>
        </c:choose>
            <div class="grid">
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="type">申请类型</label>
                    </div>
                    <div class="grid-content">
                        <span id="type">${application.type.name}</span>
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="name">酒店名称</label>
                    </div>
                    <div class="grid-content">
                        <input id="name" type="text" name="name" value="${application.name}">
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="address">酒店地址</label>
                    </div>
                    <div class="grid-content">
                        <input id="address" type="text" name="address" value="${application.address}">
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="introduction">酒店简介</label>
                    </div>
                    <div class="grid-content">
                        <textarea id="introduction" name="introduction">${application.introduction}</textarea>
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="facility">服务设施</label>
                    </div>
                    <div class="grid-content">
                        <textarea id="facility" name="facility">${application.facility}</textarea>
                        <span class="alert"></span>
                    </div>
                </div>
            </div>
            <input id="submit-button" class="major-button" value="提交"/>
            <button class="minor-button" type="button" onclick="history.back()">取消</button>
        </form>
    </div>
</main>

<script>
    $("#submit-button").click(function () {
        var name = $("#name").val();
        var nameAlert = $("#name + .alert");
        var isName = name != "";
        nameAlert.text(isName ? "" : "请输入名称！");

        var address = $("#address").val();
        var addressAlert = $("#address + .alert");
        var isAddress = address != "";
        addressAlert.text(isAddress ? "" : "请输入地址！");

        var introduction = $("#introduction").val();
        var introductionAlert = $("#introduction + .alert");
        var isIntroduction = introduction != "";
        introductionAlert.text(isIntroduction ? "" : "请输入简介！");

        var facility = $("#facility").val();
        var facilityAlert = $("#facility + .alert");
        var isFacility = facility != "";
        facilityAlert.text(isFacility ? "" : "请输入服务设施！");

        if (isName && isAddress && isIntroduction && isFacility) {
            $("#add-form").submit();
        }
    });
</script>

<%@ include file="include/footer.jsp" %>