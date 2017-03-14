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

<%@ include file="include/manager-nav.jsp" %>

<main>
    <div class="container card">
        <h1>申请详情</h1>
        <div class="grid">
            <div class="grid-row">
                <div class="grid-label">
                    <label for="id">申请编号</label>
                </div>
                <div class="grid-content">
                    <span id="id">${applicationBean.application.ID}</span>
                </div>
            </div>

            <div class="grid-row">
                <div class="grid-label">
                    <label for="appliedTime">申请时间</label>
                </div>
                <div class="grid-content">
                    <span id="appliedTime">${applicationBean.application.appliedTime}</span>
                </div>
            </div>

            <div class="grid-row">
                <div class="grid-label">
                    <label for="checkedTime">审核时间</label>
                </div>
                <div class="grid-content">
                    <span id="checkedTime">${applicationBean.application.checkedTime}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="user">申请人</label>
                </div>
                <div class="grid-content">
                    <span id="user">${applicationBean.hostel.username}(${applicationBean.hostel.ID})</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="state">状态</label>
                </div>
                <div class="grid-content">
                    <span id="state"
                          class="tag tag-${applicationBean.application.state.color}-current">${applicationBean.application.state.name}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="image">酒店图片</label>
                </div>
                <div class="grid-content">
                    <c:choose>
                        <c:when test="${applicationBean.application.imageType != null}">
                            <div id="image" class="image-view-wrapper">
                                <div id="image-view" class="image-view"
                                     style="background-image: url('${basePath}/static/images/application/${applicationBean.application.ID}.${applicationBean.application.imageType}')"></div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            无
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="name">酒店名称</label>
                </div>
                <div class="grid-content">
                    <span id="name">${applicationBean.application.name}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="city">酒店城市</label>
                </div>
                <div class="grid-content">
                    <span id="city" class="tag tag-green-current">${applicationBean.application.city.name}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="address">酒店地址</label>
                </div>
                <div class="grid-content">
                    <span id="address">${applicationBean.application.address}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="introduction">酒店简介</label>
                </div>
                <div class="grid-content">
                    <p id="introduction">${applicationBean.application.introduction}</p>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="facility">服务设施</label>
                </div>
                <div class="grid-content">
                    <span id="facility">${applicationBean.application.facility}</span>
                </div>
            </div>
        </div>
        <div class="book-submit">
            <c:if test="${applicationBean.application.state == 'Unchecked'}">
                <form action="${basePath}/admin/application/${applicationBean.application.ID}/pass" method="post">
                    <button type="submit" class="major-button">通过</button>
                </form>
                <form action="${basePath}/admin/application/${applicationBean.application.ID}/fail" method="post">
                    <button type="submit" class="minor-button">拒绝</button>
                </form>
            </c:if>
            <button type="button" class="minor-button" onclick="history.back()">返回</button>

            <div class="clear-fix"></div>
        </div>
        <div class="clear-fix"></div>
    </div>
</main>

<%@ include file="include/footer.jsp" %>