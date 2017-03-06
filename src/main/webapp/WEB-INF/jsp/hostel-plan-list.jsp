<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 下午2:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/hostel-nav.jsp" %>
<main>
    <div class="container card hostel-room">
        <h1 class="title">房间管理</h1>
        <div class="right-buttons">
            <a href="${basePath}/hostel/plan/add" class="major-button-small">发布计划</a>
        </div>
        <div class="sub-nav left-nav">
            <a href="${basePath}/hostel/room">房间查看</a>
            <a class="current" href="${basePath}/hostel/plan">房间计划</a>
        </div>

        <div class="clear-fix"></div>
        <div class="room-wrapper">
            <div id="rooms" class="room-list grid">
                <div class="grid-row grid-row-line">
                    <div class="room-img"></div>
                    <div class="room-plan-name title">名称</div>
                    <div class="room-plan-price title">价格</div>
                    <div class="room-plan-quantity title">数量</div>
                    <div class="room-plan-start-date title">起始日期</div>
                    <div class="room-plan-end-date title">结束日期</div>
                    <div class="room-plan-operation title">操作</div>
                </div>
                <c:forEach var="room" items="${hostelRoomListBean.hostelRooms}">
                    <div class="grid-row grid-row-line">
                        <div class="room-img">
                            <div class="room-img-wrapper">
                                <div class="img"></div>
                            </div>
                        </div>
                        <div class="room-plan-name">${room.name}</div>
                        <div class="room-plan-price">￥ <span class="money">${room.price}</span></div>
                        <div class="room-plan-quantity">${room.quantity}</div>
                        <div class="room-plan-start-date">${room.startDate}</div>
                        <div class="room-plan-end-date">${room.endDate}</div>
                        <div class="room-plan-operation">
                            <button class="major-button-small">修改</button>
                            <button class="minor-button-small">删除</button>
                        </div>
                    </div>
                </c:forEach>

                <%--<div class="grid-row grid-row-line">--%>
                    <%--<div class="room-img">--%>
                        <%--<div class="room-img-wrapper">--%>
                            <%--<div class="img"></div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="room-plan-name">大床房</div>--%>
                    <%--<div class="room-plan-price">300元</div>--%>
                    <%--<div class="room-plan-quantity">10</div>--%>
                    <%--<div class="room-plan-start-date">2017-03-30</div>--%>
                    <%--<div class="room-plan-end-date">2017-04-30</div>--%>
                    <%--<div class="room-plan-operation">--%>
                        <%--<button class="major-button-small">修改</button>--%>
                        <%--<button class="minor-button-small">删除</button>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
        </div>
    </div>
</main>

<script>
    $(".money").number( true, 2 );
</script>
<%@ include file="include/footer.jsp" %>