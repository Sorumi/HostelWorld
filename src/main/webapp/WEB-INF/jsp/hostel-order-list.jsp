<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 上午10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/hostel-nav.jsp" %>

<main>
    <div class="container">
        <div class="card order-search">
            <h1 class="title">预定管理</h1>
            <div class="right-buttons">
                <a href="${basePath}/hostel/order/book" class="major-button-small">非会员入住</a>
            </div>
            <div class="tags left-nav">
                <a href="${basePath}/hostel/order" class="tag tag-yellow
                <c:if test="${orderState == null}">
                    tag-yellow-current
                </c:if>">全部</a>
                <a href="${basePath}/hostel/order?state=UnCheckIn" class="tag tag-orange
                <c:if test="${orderState == 'UnCheckIn'}">
                    tag-orange-current
                </c:if>
                ">未入住</a>

                <a href="${basePath}/hostel/order?state=CheckIn" class="tag tag-blue
                <c:if test="${orderState == 'CheckIn'}">
                    tag-blue-current
                </c:if>
                ">已入住</a>

                <a href="${basePath}/hostel/order?state=CheckOut" class="tag tag-green
                <c:if test="${orderState == 'CheckOut'}">
                    tag-green-current
                </c:if>
                ">已退房</a>

                <a href="${basePath}/hostel/order?state=Cancelled" class="tag tag-pink
                <c:if test="${orderState == 'Cancelled'}">
                    tag-pink-current
                </c:if>
                ">已退订</a>

                <a href="${basePath}/hostel/order?state=Expired" class="tag tag-purple
                <c:if test="${orderState == 'Expired'}">
                    tag-purple-current
                </c:if>
                ">已过期</a>
            </div>
            <div class="clear-fix"></div>
        </div>
        <c:forEach var="order" items="${orderList}">
            <div class="order-wrapper">
                <div class="order-img-wrapper">
                    <div class="img"></div>
                </div>
                <div class="info-wrapper card">
                    <span class="order-id">订单编号:${order.bookOrder.ID}</span>
                    <span class="order-date">订单日期:${order.bookOrder.bookedTime}</span>
                    <span class="order-state tag tag-${order.bookOrder.state.color}-current">${order.bookOrder.state.name}</span>
                    <c:if test="${order.bookOrder.memberID == null}">
                        <span class="order-not-member">非会员</span>
                    </c:if>
                    <span class="order-hotel-name">${order.member.name}</span>
                    <!--<p>地址</p>-->
                    <span class="order-check-date">入离日期:${order.bookOrder.checkInDate} - ${order.bookOrder.checkOutDate}</span>
                    <span class="order-room">
                        <c:forEach var="room" items="${order.rooms}">
                            ${room.name} × ${room.quantity}
                        </c:forEach>
                    </span>
                    <c:if test="${order.bookOrder.state == 'CheckOut'}">
                        <c:choose>
                            <c:when test="${order.bookOrder.accountedTime != null}">
                                <span class="order-accounted accounted">已结算</span>
                            </c:when>
                            <c:otherwise>
                                <span class="order-accounted unaccounted">未结算</span>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <span class="order-price">￥ <span class="money">${order.bookOrder.totalPrice}</span></span>
                    <a href="${basePath}/hostel/order/${order.bookOrder.ID}" class="major-button info-button">详情</a>
                </div>
            </div>
        </c:forEach>

    </div>
</main>

<script>
    $(".money").number( true, 2 );
</script>

<%@ include file="include/footer.jsp" %>