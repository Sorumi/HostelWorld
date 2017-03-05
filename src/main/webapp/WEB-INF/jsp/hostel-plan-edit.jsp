<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 下午3:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/hostel-nav.jsp" %>

<main>
    <div class="container card plan-edit">
        <h1 class="title">发布计划</h1>
        <form action="${basePath}/hostel/plan/add" method="post" autocomplete="off" id="add-form" enctype="multipart/form-data">
            <div class="grid">
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="image">房间图片</label>
                    </div>
                    <div class="grid-content">
                        <div class="buttons">
                            <button id="upload-image" type="button" class="major-button">上传图片</button>
                            <button id="remove-image" type="button" class="minor-button" style="display:none;">移除图片</button>
                        </div>
                        <div id="image">
                            <input type="file" id="image-file" name="image" accept="image/png, image/jpeg, image/gif" style="display: none">
                            <input type="hidden" id="image-change" name="imageChanged" value="0">
                        </div>

                        <div id="image-view-wrapper" class="image-view-wrapper" style="display: none">
                            <div id="image-view" class="image-view"></div>
                        </div>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="name">房间名称</label>
                    </div>
                    <div class="grid-content">
                        <input id="name" type="text" name="name" value="${hostelRoom.name}">
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="price">价格</label>
                    </div>
                    <div class="grid-content">
                        <input id="price" type="text" name="price" value="${hostelRoom.price}"/>
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="quantity">数量</label>
                    </div>
                    <div class="grid-content">
                        <input id="quantity" type="text" name="quantity" value="${hostelRoom.quantity}"/>
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="start-date">开始日期</label>
                    </div>
                    <div class="grid-content">
                        <input type="text" id="start-date" name="startDate" value="${hostelRoom.startDate}"/>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="end-date">截止日期</label>
                    </div>
                    <div class="grid-content">
                        <input type="text" id="end-date" name="endDate" value="${hostelRoom.endDate}"/>
                    </div>
                </div>
            </div>
            <p>${alert}</p>
            <button class="major-button" type="button" id="submit-button">确认</button>
            <a href="${basePath}/hostel/plan" class="minor-button">取消</a>
        </form>
    </div>
</main>

<script src="${basePath}/js/upload-image.js"></script>
<script>
    //validate
    $("#submit-button").click(function () {
        var name = $("#name").val();
        var nameAlert = $("#name + .alert");
        var isName = name != "";
        nameAlert.text(isName ? "" : "请输入名称！");

        var price = $("#price").val();
        var priceAlert = $("#price + .alert");
        var priceReg = /^[0-9]+(.[0-9]?[0-9]?)?$/;
        var isPrice = priceReg.test(price);
        priceAlert.text(isPrice ? "" : "请输入正确的价格！");

        var quantity = $("#quantity").val();
        var quantityAlert = $("#quantity + .alert");
        var quantityReg = /^[1-9][0-9]*$/;
        var isQuantity = quantityReg.test(quantity);
        quantityAlert.text(isQuantity ? "" : "请输入正确的数量！");

        if (isName && isPrice && isQuantity) {
            $("#add-form").submit();
        }
    });

    //
    Date.prototype.addDays = function(days) {
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    }

    var startDatePickr = new Flatpickr($("#start-date")[0], {
        minDate: new Date(),
        defaultDate: new Date(),
        onChange: function(selectedDates, dateStr, instance) {
            changeEnd();
        },
    });


    var endDatePickr = new Flatpickr($("#end-date")[0], {
        minDate: new Date().addDays(1),
        defaultDate: new Date().addDays(30),
        onChange: function(selectedDates, dateStr, instance) {
            changeStart();
        },
    });

    function changeEnd() {
        var startDate = new Date($("#start-date")[0].value);
        var endDate = new Date($("#end-date")[0].value);
        if (startDate > endDate) {
            endDatePickr.setDate(startDate.addDays(1));
        }
    }

    function changeStart() {
        var startDate = new Date($("#start-date")[0].value);
        var endDate = new Date($("#end-date")[0].value);
        if (startDate > endDate) {
            startDatePickr.setDate(endDate.addDays(-1));
        }
    }

</script>

<%@ include file="include/footer.jsp" %>