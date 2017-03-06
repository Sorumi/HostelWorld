<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 下午2:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/hostel-nav.jsp" %>

<main>
    <div class="container card hostel-room">
        <h1 class="title">房间管理</h1>
        <div class="sub-nav">
            <a class="current" href="${basePath}/hostel/room">房间查看</a>
            <a href="${basePath}/hostel/plan">历史计划</a>
        </div>

        <div class="room-wrapper">
            <div class="horizontal-label-input">
                <label for="date">日期</label>
                <input id="date" name="Date" type="text" value="${hostelSearchRoomBean.date}">
            </div>

            <div id="rooms" class="room-list grid">
                <div class="grid-row grid-row-line">
                    <div class="room-img"></div>
                    <div class="room-name title">名称</div>
                    <div class="room-price title">价格</div>
                    <div class="room-quantity-available title">剩余数量</div>
                    <div class="room-quantity-total title">全部数量</div>
                </div>
                <c:forEach var="room" items="${hostelSearchRoomBean.rooms}">
                    <div class="grid-row grid-row-line">
                        <div class="room-img">
                            <div class="room-img-wrapper">
                                <div class="img"></div>
                            </div>
                        </div>
                        <div class="room-name">${room.name}</div>
                        <div class="room-price">￥ <span class="money">${room.price}</span></div>
                        <div class="room-quantity-available">${room.availableQuantity}</div>
                        <div class="room-quantity-total">${room.totalQuantity}</div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
</main>
<script>
    $(".money").number( true, 2 );

    var date = new Flatpickr($("#date")[0], {
        onChange: function (selectedDates, dateStr, instance) {
            loadRooms();
        },
    });

    function loadRooms() {
        var date = $("#date").val();

        var data = {
            "date": date,
            "hostelID": "${hostel.ID}",
        };

        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            url: "/hostel/${hostel.ID}/roomstock",
            data: JSON.stringify(data),

            success: function (data) {
//                console.log(data);

                $("#rooms").html(' <div class="grid-row grid-row-line">' +
                        '<div class="room-img"></div>' +
                        '<div class="room-name title">名称</div>' +
                        '<div class="room-price title">价格</div>' +
                        '<div class="room-quantity-available title">剩余数量</div>' +
                        '<div class="room-quantity-total title">全部数量</div>' +
                        '</div>');
                for (var i = 0; i < data.length; i++) {
                    $("#rooms").html($("#rooms").html() + '<div class="grid-row grid-row-line">' +
                            '<div class="room-img">' +
                            '<div class="room-img-wrapper">' +
                            '<div class="img"></div>' +
                            '</div>' +
                            '</div>' +
                            '<div class="room-name">' + data[i].name + '</div>' +
                            '<div class="room-price">￥ <span class="money">' + data[i].price + ' </span></div>' +
                            '<div class="room-quantity-available">' + data[i].availableQuantity + '</div>' +
                            '<div class="room-quantity-total">' + data[i].totalQuantity + '</div>' +
                            '</div>' +
                            '</div>')

                }

                $(".money").number( true, 2 );
            }
        });
    }
</script>

<%@ include file="include/footer.jsp" %>