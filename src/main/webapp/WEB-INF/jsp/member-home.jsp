<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 下午1:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="include/header.jsp" %>

<div class="bg bg-large">
    <div class="img"></div>
</div>

<main>
    <div class="container">
        <form class="search-large" action="/search" method="get">
            <input id="keyword" name="keyword" type="text"/>
            <div class="row">
                <div class="check-in-date-column">
                    <div>
                        <label for="check-in-date">入住日期</label>
                        <input id="check-in-date" name="checkInDate" type="text" value="${searchHostelBean.checkInDate}">
                    </div>
                </div>

                <div class="check-out-date-column">
                    <div>
                        <label for="check-out-date">退房日期</label>
                        <input id="check-out-date" name="checkOutDate" type="text" value="${searchHostelBean.checkOutDate}">
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
        defaultDate: new Date(),
        onChange: function(selectedDates, dateStr, instance) {
            changeEnd();
        },
    });


    var endDatePickr = new Flatpickr($("#check-out-date")[0], {
        minDate: new Date().addDays(1),
        defaultDate: new Date().addDays(1),
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