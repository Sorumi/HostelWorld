<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 上午10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/manager-nav.jsp" %>

<main>
    <div class="container card order-info">
        <h1 class="title">订单详情</h1>

        <div class="grid">
            <div class="grid-row">
                <div class="grid-label">
                    <label for="id">订单编号</label>
                </div>
                <div class="grid-content">
                    <%--<c:choose>--%>
                    <%--<c:when test="${order.bookOrder.ID == null}">--%>
                    <%--<span id="id">非会员</span>--%>
                    <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                    <span id="id">${order.bookOrder.ID}</span>
                    <c:if test="${order.bookOrder.memberID == null}">
                        <span class="order-not-member">非会员</span>
                    </c:if>
                    <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="booked-time">预定时间</label>
                </div>
                <div class="grid-content">
                    <span id="booked-time">${order.bookOrder.bookedTime}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="hostel">酒店信息</label>
                </div>
                <div class="grid-content">
                    <div id="hostel">
                        <span class="hostel-name">${order.hostel.name}</span>
                        <span class="hostel-address">${order.hostel.address}</span>
                    </div>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="member">会员信息</label>
                </div>
                <div class="grid-content">
                    <div id="member">
                        <span class="member-name">${order.member.name}</span>
                        <span class="member-contact">${order.member.contact}</span>
                    </div>
                </div>
            </div>

            <div class="grid-row">
                <div class="grid-label">
                    <label for="state">订单状态</label>
                </div>
                <div class="grid-content">
                    <span id="state"
                          class="tag tag-${order.bookOrder.state.color}-current">${order.bookOrder.state.name}</span>
                </div>
            </div>

            <c:if test="${order.bookOrder.state == 'CheckOut'}">
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="accounted">订单状态</label>
                    </div>
                    <div class="grid-content">
                        <c:choose>
                            <c:when test="${order.bookOrder.accounted}">
                                <span id="accounted" class="tag tag-green-current">已结算</span>
                            </c:when>
                            <c:otherwise>
                                <span id="accounted" class="tag tag-red-current">未结算</span>
                                <form action="${basePath}/admin/order/${order.bookOrder.ID}/account" method="post" class="inline">
                                    <button type="submit" class="major-button-small">结算</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:if>

            <div class="grid-row">
                <div class="grid-label">
                    <label for="check-date">预计入离日期</label>
                </div>
                <div class="grid-content">
                    <span id="check-date">${order.bookOrder.checkInDate} - ${order.bookOrder.checkOutDate}</span>
                </div>
            </div>


            <div class="grid-row">
                <div class="grid-label">
                    <label for="check-in-time">实际入住时间</label>
                </div>
                <div class="grid-content">
                    <div id="check-in-time">
                        <c:choose>
                            <c:when test="${order.bookOrder.checkInTime == null}">
                                <span>未入住</span>
                            </c:when>
                            <c:otherwise>
                                <span>${order.bookOrder.checkInTime}</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>


            <div class="grid-row">
                <div class="grid-label">
                    <label for="check-out-time">实际退房时间</label>
                </div>
                <div class="grid-content">
                    <div id="check-out-time">
                        <c:choose>
                            <c:when test="${order.bookOrder.checkOutTime == null}">
                                <span>未退房</span>
                            </c:when>
                            <c:otherwise>
                                <span>${order.bookOrder.checkOutTime}</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>


            <div class="grid-row">
                <div class="grid-label">
                    <label for="people-quantity">入住人数</label>
                </div>
                <div class="grid-content">
                    <span id="people-quantity">${order.bookOrder.peopleQuantity}</span>
                </div>
            </div>

            <div class="grid-row">
                <div class="grid-label">
                    <label for="rooms">房间列表</label>
                </div>
                <div class="grid-content room-wrapper">

                    <div id="rooms" class="room-list">
                        <div class="grid-row grid-row-line">
                            <div class="room-img"></div>
                            <div class="room-name title">名称</div>
                            <div class="room-price title">价格</div>
                            <div class="room-quantity title">数量</div>
                            <div class="room-total title">小计</div>
                        </div>
                        <c:forEach var="room" items="${order.rooms}">
                            <div class="grid-row grid-row-line">
                                <div class="room-img">
                                    <div class="room-img-wrapper">
                                        <div class="img"></div>
                                    </div>
                                </div>
                                <div class="room-name">${room.name}</div>
                                <div class="room-price">￥ <span class="money">${room.price}</span></div>
                                <div class="room-quantity">${room.quantity}</div>
                                <div class="room-total">￥ <span class="money">${room.total}</span></div>
                            </div>
                        </c:forEach>

                    </div>
                </div>
            </div>
        </div>

        <div class="price-wrapper">
            <div class="row">
                <label for="origin-price">原价</label>
                <span id="origin-price">￥ <span class="money">${order.bookOrder.originPrice}</span></span>
                <div class="clear-fix"></div>
            </div>
            <div class="row">
                <label for="discount">折扣</label>
                <span id="discount">
                     <c:choose>
                         <c:when test="${order.bookOrder.discount} < 1">
                             <span class="money">${order.bookOrder.discount}</span>
                         </c:when>
                         <c:otherwise>
                             无
                         </c:otherwise>
                     </c:choose>
                </span>
                <div class="clear-fix"></div>
            </div>
            <div class="row">
                <label for="total-price">总价</label>
                <span id="total-price">￥ <span class="money">${order.bookOrder.totalPrice}</span></span>
                <div class="clear-fix"></div>
            </div>
        </div>

        <div class="clear-fix"></div>
        <div class="book-submit">
            <button class="major-button" onclick="history.back()">返回</button>
        </div>
        <div class="clear-fix"></div>
    </div>
</main>

<script>
    $(".money").number(true, 2);
</script>
<%@ include file="include/footer.jsp" %>
