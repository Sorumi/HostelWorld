<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/28
  Time: 下午4:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/hostel-nav.jsp" %>

<main>
    <div class="container">
        <div class="card application-search">
            <h1 class="title">申请管理</h1>
            <div class="right-buttons">
                <c:if test="${hostel.state == 'Unopened'}">
                    <a href="${basePath}/hostel/application/open/add" class="major-button-small">申请开店</a>
                </c:if>
                <c:if test="${hostel.state == 'Opening'}">
                    <a href="${basePath}/hostel/application/edit/add" class="major-button-small">申请修改</a>
                </c:if>
            </div>
            <div class="sub-nav left-nav">
                <a
                        <c:if test="${applicationType == null}">
                            class="current"
                        </c:if>
                        href="${basePath}/hostel/application">全部申请</a>
                <a
                        <c:if test="${applicationType == 'Open'}">
                            class="current"
                        </c:if>
                        href="${basePath}/hostel/application?type=Open">开店申请</a>
                <a
                        <c:if test="${applicationType == 'Edit'}">
                            class="current"
                        </c:if>
                        href="${basePath}/hostel/application?type=Edit">修改申请</a>
            </div>

            <div class="clear-fix"></div>
        </div>

        <c:forEach var="application" items="${applicationList}">
            <div class="application-wrapper">

                <div class="application-img-wrapper">
                    <div class="img"
                            <c:if test="${application.imageType != null}">
                                style="background-image: url('${basePath}/static/images/application/${application.ID}.${application.imageType}')"
                            </c:if>
                    ></div>
                </div>
                <div class="info-wrapper card">
                    <span class="application-type">${application.type.name}</span>
                    <span class="application-id">申请编号:${application.ID}</span>
                    <span class="application-date">申请时间:${application.appliedTime}</span>
                    <span class="application-state tag tag-${application.state.color}-current">${application.state.name}</span>
                    <span class="application-hostel-name">${application.name}</span>
                    <span class="application-hostel-address">${application.city.name} ${application.address}</span>
                    <p class="application-hostel-introduction">${application.introduction}</p>
                    <a href="${basePath}/hostel/application/${application.ID}" class="major-button info-button">详情</a>
                </div>

            </div>
        </c:forEach>
    </div>
</main>

<%@ include file="include/footer.jsp" %>