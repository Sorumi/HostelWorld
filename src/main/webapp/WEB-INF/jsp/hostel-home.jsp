<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 下午12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="bg bg-large">
    <div class="img"></div>
</div>

<%@ include file="include/hostel-nav.jsp" %>

<main>
    <div class="container card">
        <c:if test="${hostelInfoBean.openApplication.state == 'Unchecked'}">
            <span>您的申请正在审核中！</span>
            <a href="${basePath}/hostel/application/${hostelInfoBean.openApplication.ID}" class="major-button-small">查看</a>
        </c:if>
        <c:if test="${hostelInfoBean.openApplication.state == 'Failed'}">
            <span>您的申请未通过！</span>
            <a href="${basePath}/hostel/application/${hostelInfoBean.openApplication.ID}" class="major-button-small">查看</a>
        </c:if>

        <c:if test="${hostelInfoBean.openApplication == null || hostelInfoBean.openApplication.state == 'Failed'}">
            <a href="${basePath}/hostel/application/open/add" class="major-button">申请开店</a>
        </c:if>
    </div>
</main>

<%@ include file="include/footer.jsp" %>