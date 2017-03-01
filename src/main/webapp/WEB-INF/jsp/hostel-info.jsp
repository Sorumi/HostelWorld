<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 下午1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="bg bg-large">
    <div class="img"></div>
</div>

<main>
    <div class="container hostel-info">
        <h3 class="hostel-name">${hostel.name}</h3>
        <p class="hostel-address">地址:${hostel.address}</p>
        <div class="grid">
            <div class="grid-row grid-row-line">
                <div class="grid-label">
                    <label for="introduction">酒店简介</label>
                </div>
                <div class="grid-content">
                    <p id="introduction">${hostel.introduction}
                    </p>
                </div>
            </div>
            <div class="grid-row grid-row-line">
                <div class="grid-label">
                    <label for="facility">服务设施</label>
                </div>
                <div class="grid-content">
                    <span id="facility">${hostel.facility}</span>
                </div>
            </div>
        </div>
        <a href="/hostel/order" class="major-button">管理</a>
        <a href="/hostel/application/edit/add" class="minor-button">修改</a>
    </div>

</main>


<%@ include file="include/footer.jsp" %>