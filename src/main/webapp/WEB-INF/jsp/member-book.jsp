<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/5
  Time: 上午10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="bg bg-small">
    <div class="img"></div>
</div>

<main>
    <div class="container card book-order">
        <h1 class="title">订单预定</h1>
        <form action="/book/submit" method="post">
            <div class="grid">
                <div class="grid-row grid-row-line">
                    <div class="grid-label">
                        <label for="hostel-info">酒店信息</label>
                    </div>
                    <div class="grid-content">
                        <div id="hostel-info">
                            <span class="hostel-name">${orderBean.hostel.name}</span>
                            <span class="hostel-address">${orderBean.hostel.address}</span>
                        </div>
                    </div>
                </div>
                <div class="grid-row grid-row-line">
                    <div class="grid-label">
                        <label for="date">入离日期</label>
                    </div>
                    <div class="grid-content">
                        <div id="date">
                            <span id="check-in-date">${orderBean.bookOrder.checkInDate}</span>
                            <span id="check-out-date">${orderBean.bookOrder.checkOutDate}</span>
                        </div>
                    </div>
                </div>
                <div class="grid-row grid-row-line">
                    <div class="grid-label">
                        <label for="people-quantity">入住人数</label>
                    </div>
                    <div class="grid-content">
                        <div id="people-quantity" class="quantity-select">
                            <button class="minus">-</button>
                            <span>2</span>
                            <button class="add">+</button>
                            <input type="text" name="bookOrder.peopleQuantity" width="30">
                        </div>
                    </div>
                </div>

                <div class="grid-row grid-row-line">
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
                            <c:forEach var="room" items="${orderBean.rooms}" varStatus="status">
                                <div class="grid-row grid-row-line">
                                    <div class="room-img">
                                        <div class="room-img-wrapper">
                                            <div class="img"></div>
                                        </div>
                                    </div>
                                    <div class="room-name">${room.name}</div>
                                    <div class="room-price">${room.price} 元</div>
                                    <div class="room-quantity">${room.quantity}</div>
                                    <div class="room-total">${room.total} 元</div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

            <div class="price-wrapper">
                <div class="row">
                    <label for="origin-price">原价</label>
                    <span id="origin-price">${orderBean.bookOrder.originPrice}元</span>
                    <div class="clear-fix"></div>
                </div>
                <div class="row">
                    <label for="discount">优惠</label>
                    <span id="discount">-70元</span>
                    <div class="clear-fix"></div>
                </div>
                <div class="row">
                    <label for="total-price">总价</label>
                    <span id="total-price">${orderBean.bookOrder.totalPrice}元</span>
                    <div class="clear-fix"></div>
                </div>
            </div>

            <div class="clear-fix"></div>

            <c:choose>
                <c:when test="${orderBean.member.money >= orderBean.bookOrder.totalPrice}">
                    <div class="book-submit">
                        <button class="major-button">确认</button>
                        <div class="clear-fix"></div>
                        <p>使用余额 ${orderBean.member.money}元</p>
                        <div class="clear-fix"></div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="book-submit">
                        <button class="major-button-disabled" disabled="disabled">确认</button>
                        <div class="clear-fix"></div>
                        <p>余额 ${orderBean.member.money}元 不足</p>
                        <div class="clear-fix"></div>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="clear-fix"></div>
        </form>
    </div>
</main>


<%@ include file="include/footer.jsp" %>