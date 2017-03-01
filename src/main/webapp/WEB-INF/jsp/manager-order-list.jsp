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

<%@ include file="include/manager-nav.jsp" %>

<main>
    <div class="container">
        <div class="card order-search">
            <h1 class="title">预定管理</h1>
            <div class="tags">
                <a href="/admin/order" class="tag tag-yellow
                <c:if test="${orderState == null}">
                    tag-yellow-current
                </c:if>">全部</a>
                <a href="/admin/order?state=UnCheckIn" class="tag tag-orange
                <c:if test="${orderState == 'UnCheckIn'}">
                    tag-orange-current
                </c:if>
                ">未入住</a>

                <a href="/admin/order?state=CheckIn" class="tag tag-blue
                <c:if test="${orderState == 'CheckIn'}">
                    tag-blue-current
                </c:if>
                ">已入住</a>

                <a href="/admin/order?state=CheckOut" class="tag tag-green
                <c:if test="${orderState == 'CheckOut'}">
                    tag-green-current
                </c:if>
                ">已退房</a>

                <a href="/admin/order?state=Cancelled" class="tag tag-pink
                <c:if test="${orderState == 'Cancelled'}">
                    tag-pink-current
                </c:if>
                ">已退订</a>

                <a href="/admin/order?state=Expired" class="tag tag-purple
                <c:if test="${orderState == 'Expired'}">
                    tag-purple-current
                </c:if>
                ">已过期</a>
            </div>

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
                    <span class="order-hotel-name">${order.hostel.name} （${order.hostel.ID}）</span>
                    <!--<p>地址</p>-->
                    <span class="order-check-date">入离日期:${order.bookOrder.checkInDate} - ${order.bookOrder.checkOutDate}</span>
                    <span class="order-room">
                        <c:forEach var="room" items="${order.rooms}">
                            ${room.name} × ${room.quantity}
                        </c:forEach>
                    </span>
                    <span class="order-price">￥ <span class="money">${order.bookOrder.totalPrice}</span></span>
                    <c:if test="${order.bookOrder.state == 'CheckOut'}">
                        <c:choose>
                            <c:when test="${order.bookOrder.accounted}">
                                <span class="order-accounted accounted">已结算</span>
                            </c:when>
                            <c:otherwise>
                                <span class="order-accounted unaccounted">未结算</span>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <a href="/admin/order/${order.bookOrder.ID}" class="major-button info-button">详情</a>
                </div>
            </div>
        </c:forEach>

    </div>
</main>

<script>
    $(".money").number( true, 2 );
</script>

<%@ include file="include/footer.jsp" %>