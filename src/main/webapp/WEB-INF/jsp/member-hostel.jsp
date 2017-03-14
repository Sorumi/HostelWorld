<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 下午3:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="bg bg-small">
    <div class="img"
            <c:if test="${memberHostelInfoBean.hostel.imageType != null}">
                style="background-image: url('${basePath}/static/images/hostel/${memberHostelInfoBean.hostel.ID}.${memberHostelInfoBean.hostel.imageType}')"
            </c:if>
    ></div>
</div>

<main>
    <div class="container hostel-info">
        <h3 class="hostel-name">${memberHostelInfoBean.hostel.name}</h3>
        <p class="hostel-address">地址:${memberHostelInfoBean.hostel.address}</p>
        <div class="grid">
            <div class="grid-row grid-row-line">
                <div class="grid-label">
                    <label for="introduction">酒店简介</label>
                </div>
                <div class="grid-content">
                    <p id="introduction">${memberHostelInfoBean.hostel.introduction}
                    </p>
                </div>
            </div>
            <div class="grid-row grid-row-line">
                <div class="grid-label">
                    <label for="facility">服务设施</label>
                </div>
                <div class="grid-content">
                    <span id="facility">${memberHostelInfoBean.hostel.facility}</span>
                </div>
            </div>
            <div class="grid-row grid-row-line">
                <div class="grid-label">
                    <label for="rooms">房间列表</label>
                </div>
                <div class="grid-content room-wrapper">
                    <form action="${basePath}/book" method="post">
                        <input type="hidden" value="${memberHostelInfoBean.hostel.ID}">
                        <%--<form action="${basePath}/search/${memberHostelInfoBean.hostel.ID}" method="get">--%>
                        <div class="vertical-label-input check-in-date-column">
                            <div>
                                <label for="check-in-date">入住日期</label>
                                <input id="check-in-date" name="checkInDate" type="text"
                                       value="${memberHostelInfoBean.checkInDate}">
                            </div>
                        </div>

                        <div class="vertical-label-input check-out-date-column">
                            <div>
                                <label for="check-out-date">退房日期</label>
                                <input id="check-out-date" name="checkOutDate" type="text"
                                       value="${memberHostelInfoBean.checkOutDate}">
                            </div>
                        </div>

                        <div class="vertical-label-input submit-column">
                            <div>
                                <div class="label-fix"></div>
                                <button id="submit-button" class="major-button-disabled" type="submit" disabled="disabled">预定</button>
                            </div>
                        </div>

                        <div id="rooms" class="room-list grid">
                            <div class="grid-row grid-row-line">
                                <div class="room-img"></div>
                                <div class="room-name title">名称</div>
                                <div class="room-price title">价格</div>
                                <div class="room-quantity title">数量</div>
                                <div class="room-operation title">操作</div>
                            </div>
                            <c:forEach var="room" items="${memberHostelInfoBean.roomStocks}" varStatus="status">
                                <div class="grid-row grid-row-line room">
                                    <div class="room-img">
                                        <div class="room-img-wrapper">
                                            <div class="img"
                                                    <c:if test="${room.imageType != null}">
                                                        style="background-image: url('${basePath}/static/images/hostelroom/${memberHostelInfoBean.hostel.ID}/${room.ID}.${room.imageType}')"
                                                    </c:if>
                                            ></div>
                                        </div>
                                    </div>
                                    <div class="room-name">${room.name}</div>
                                    <div class="room-price">￥ <span class="money">${room.price}</span></div>
                                    <div class="room-quantity">${room.availableQuantity}</div>
                                    <div class="room-operation">
                                        <c:choose>
                                            <c:when test="${room.availableQuantity > 0}">
                                                <button type="button" class="add-button minor-button">加入</button>
                                                <div class="number-picker quantity-select"
                                                     name="bookQuantity[${status.index}]" min="0"
                                                     max="${room.availableQuantity}"></div>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="button" class="minor-button-disabled">已定完</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </form>
                </div>
            </div>
            <div class="price-wrapper">
                <div class="row">
                    <label for="total-price">总价</label>
                    <span id="total-price">￥ <span class="money">0</span></span>
                    <div class="clear-fix"></div>
                </div>
            </div>

            <div class="clear-fix"></div>

        </div>
    </div>
</main>

<script>

    $(".money").number(true, 2);

    function calculateTotal() {
        var rooms = $('.room');
        var total = 0;
        for (var i = 0; i < rooms.length; i++) {
            var room = rooms[i];

            var price = $(room).find('.room-price .money').text();
            var quantity = $(room).find('.number-picker input').val();
            if (quantity != undefined) {
                total += price * quantity;
            }
        }
        $('#total-price .money').text(total).number(true, 2);

        var button = $("#submit-button");
        if (total > 0) {
            button.addClass('major-button').removeClass('major-button-disabled').removeAttr('disabled');
        } else {
            button.addClass('major-button-disabled').removeClass('major-button').attr('disabled', 'disabled');
        }
    }

    //

    function refreshButtons() {
        $('.add-button').click(function () {
            var button = $(this);
            $(this).siblings('.number-picker').numberpicker({
                'onChange': function () {
                    calculateTotal();
                },
                'onDestroy': function () {
                    button.show();
                }
            });
            $(this).hide();
            calculateTotal();
        });
    }

    //
    function refreshRoomStock() {

        var checkInDate = $("#check-in-date").val();
        var checkOutDate = $("#check-out-date").val();

        var data = {
            "checkInDate": checkInDate,
            "checkOutDate": checkOutDate,
            "hostelID": "${memberHostelInfoBean.hostel.ID}",
        };

        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            url:"${basePath}/search/${memberHostelInfoBean.hostel.ID}/roomstock",
            data: JSON.stringify(data),

            success: function (data) {
                console.log(data);

                $("#rooms").html(' <div class="grid-row grid-row-line">' +
                        '<div class="room-img"></div>' +
                        '<div class="room-name title">名称</div>' +
                        '<div class="room-price title">价格</div>' +
                        '<div class="room-quantity title">数量</div>' +
                        '<div class="room-operation title">操作</div>' +
                        '</div>');
                for (var i = 0; i < data.length; i++) {
                    if (data[i].availableQuantity > 0) {
                        $("#rooms").html($("#rooms").html() +
                                '<div class="grid-row grid-row-line room">' +
                                '<div class="room-img">' +
                                '<div class="room-img-wrapper">' +
                                '<div class="img"></div>' +
                                '</div>' +
                                '</div>' +
                                '<div class="room-name">' + data[i].name + '</div>' +
                                '<div class="room-price">￥ <span class="money">' + data[i].price + '</span></div>' +
                                '<div class="room-quantity">' + data[i].availableQuantity + '</div>' +
                                '<div class="room-operation">' +
                                '<button type="button" class="add-button minor-button">加入</button>' +
                                '<div class="number-picker quantity-select" name="bookQuantity[' + i + ']" min="0" max="' + data[i].availableQuantity + '"></div>' +
                                '</div>' +
                                '</div>');
                    } else {
                        $("#rooms").html($("#rooms").html() +
                                '<div class="grid-row grid-row-line room-wrapper">' +
                                '<div class="room-img">' +
                                '<div class="room-img-wrapper">' +
                                '<div class="img"></div>' +
                                '</div>' +
                                '</div>' +
                                '<div class="room-name">' + data[i].name + '</div>' +
                                '<div class="room-price">￥ <span class="money">' + data[i].price + '</span></div>' +
                                '<div class="room-quantity">' + data[i].availableQuantity + '</div>' +
                                '<div class="room-operation">' +
                                '<button type="button" class="minor-button-disabled">已定完</button>' +
                                '</div>' +
                                '</div>');
                    }

                }
                refreshButtons();
                $(".money").number(true, 2);
            }
        });
    }

    Date.prototype.addDays = function (days) {
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    }

    var startDatePickr = new Flatpickr($("#check-in-date")[0], {
        minDate: new Date(),
        defaultDate: new Date(),
        onChange: function (selectedDates, dateStr, instance) {
            changeEnd();
            refreshRoomStock();
        },
    });


    var endDatePickr = new Flatpickr($("#check-out-date")[0], {
        minDate: new Date().addDays(1),
        defaultDate: new Date().addDays(1),
        onChange: function (selectedDates, dateStr, instance) {
            changeStart();
            refreshRoomStock();

        },
    });

    function changeEnd() {
        var startDate = new Date($("#check-in-date")[0].value);
        var endDate = new Date($("#check-out-date")[0].value);
        if (startDate >= endDate) {
            endDatePickr.setDate(startDate.addDays(1));
        }
    }

    function changeStart() {
        var startDate = new Date($("#check-in-date")[0].value);
        var endDate = new Date($("#check-out-date")[0].value);
        if (startDate >= endDate) {
            startDatePickr.setDate(endDate.addDays(-1));
        }
    }

    refreshButtons();
</script>

<%@ include file="include/footer.jsp" %>