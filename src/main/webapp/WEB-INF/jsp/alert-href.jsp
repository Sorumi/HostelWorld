<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 上午9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="bg bg-large">
    <div class="img"></div>
</div>

<main>
    <div class="container card alert-page">
        <p id="alert-message">${alertBean.message}</p>
        <a href="${basePath}/${alertBean.url}" id="alert-button" class="major-button">${alertBean.button}</a>
    </div>
</main>

<%@include file="include/footer.jsp" %>