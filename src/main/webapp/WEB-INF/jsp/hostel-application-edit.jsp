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
    <div class="container card application-edit">
        <h1 class="title">申请修改信息</h1>
        <c:choose>
        <c:when test="${application.type == 'Open'}">
        <form action="${basePath}/hostel/application/open/add" method="post" autocomplete="off" id="add-form" enctype="multipart/form-data">
            </c:when>
            <c:otherwise>
            <form action="${basePath}/hostel/application/edit/add" method="post" autocomplete="off" id="add-form"
                  enctype="multipart/form-data">
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
                            <label for="image">酒店图片</label>
                        </div>
                        <div class="grid-content">
                            <div class="buttons">
                                <button id="upload-image" type="button" class="major-button">上传图片</button>
                                <button id="remove-image" type="button" class="minor-button" style="display:none;">
                                    移除图片
                                </button>
                            </div>
                            <div id="image">
                                <input type="file" id="image-file" name="image" accept="image/png, image/jpeg, image/gif" style="display: none">
                                <input type="hidden" id="image-change" name="imageChanged" value="0">
                            </div>

                            <div id="image-view-wrapper" class="image-view-wrapper" style="display: none">
                                <div id="image-view" class="image-view"
                                        <c:if test="${hostel.imageType != null}">
                                            style="background-image: url('${basePath}/static/images/hostel/${hostel.ID}.${hostel.imageType}')"
                                        </c:if>
                                ></div>
                            </div>
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
                            <label for="city">酒店城市</label>
                        </div>
                        <div class="grid-content">
                            <%--<input id="city" type="text" name="city" value="">--%>
                            <div class="select-wrapper">
                                <select id="city" name="city">
                                    <c:forEach var="city" items="${cities}">
                                        <option value="${city.toString()}"
                                                <c:if test="${hostel.city == city}">
                                                    selected="selected"
                                                </c:if>
                                        >${city.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
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

<script src="${basePath}/js/select2.full.min.js"></script>
<script src="${basePath}/js/upload-image.js"></script>
<script>
    $("#city").select2();

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

<script>
    <c:if test="${hostel.imageType != null}">
    $('#image-view-wrapper').show();
    $("#remove-image").show();
    </c:if>
</script>

<%@ include file="include/footer.jsp" %>