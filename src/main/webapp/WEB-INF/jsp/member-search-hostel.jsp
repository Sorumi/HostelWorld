<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 下午1:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="bg bg-small">
    <div class="img"></div>
</div>

<main>
    <div class="container">
        <form class="search-small" action="${basePath}/search" method="get">

            <div class="row">
                <div class="keyword-column">
                    <div>
                        <div class="label-fix"></div>
                        <input id="keyword" name="keyword" type="text" value="${searchHostelBean.keyword}"/>
                    </div>
                </div>
                <div class="check-in-date-column">
                    <div>
                        <label for="check-in-date">入住日期</label>
                        <input id="check-in-date" name="checkInDate" type="text"
                               value="${searchHostelBean.checkInDate}">
                    </div>
                </div>

                <div class="check-out-date-column">
                    <div>
                        <label for="check-out-date">退房日期</label>
                        <input id="check-out-date" name="checkOutDate" type="text"
                               value="${searchHostelBean.checkOutDate}">
                    </div>
                </div>
                <div class="submit-column">
                    <div>
                        <div class="label-fix"></div>
                        <input id="submit" class="major-button" type="submit" value="搜索">
                    </div>
                </div>
            </div>
        </form>

        <div class="hostel-list">


            <c:forEach var="hostel" items="${searchHostelBean.hostels}" varStatus="status">
                <c:choose>
                    <c:when test="${status.index%3 == 0}">
                        <div class="hostel-1">
                            <div class="hostel-wrapper">
                                <div class="img-wrapper">
                                    <div class="img"></div>
                                </div>
                                <div class="info-wrapper">
                                    <a href="${basePath}/search/${hostel.ID}" class="title">${hostel.name}</a>
                                    <p class="address">地址:${hostel.address}</p>
                                    <p class="intro">${hostel.introduction}</p>
                                    <p class="price">价格</p>
                                </div>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${status.index%3 == 1}">
                        <div class="hostel-2">
                            <div class="hostel-wrapper">
                                <div class="img-wrapper">
                                    <div class="img"></div>
                                </div>
                                <div class="info-wrapper">
                                    <a href="#" class="title">${hostel.name}</a>
                                    <p class="address">地址:${hostel.address}</p>
                                    <p class="intro">${hostel.introduction}</p>
                                    <p class="price">价格</p>
                                </div>

                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="hostel-3">
                            <div class="hostel-wrapper">
                                <div class="img-wrapper">
                                    <div class="img"></div>
                                </div>
                                <div class="info-wrapper">
                                    <a href="#" class="title">${hostel.name}</a>
                                    <p class="address">地址:${hostel.address}</p>
                                    <p class="intro">${hostel.introduction}</p>
                                    <p class="price">价格</p>
                                </div>

                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</main>

<script>

    Date.prototype.addDays = function(days) {
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    }

    var startDatePickr = new Flatpickr($("#check-in-date")[0], {
        minDate: new Date(),
//        defaultDate: new Date(),
        onChange: function(selectedDates, dateStr, instance) {
            changeEnd();
        },
    });


    var endDatePickr = new Flatpickr($("#check-out-date")[0], {
        minDate: new Date().addDays(1),
//        defaultDate: new Date().addDays(1),
        onChange: function(selectedDates, dateStr, instance) {
            changeStart();
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

</script>

<%@ include file="include/footer.jsp" %>