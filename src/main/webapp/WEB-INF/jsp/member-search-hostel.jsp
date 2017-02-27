<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 下午1:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="bg bg-small">
    <div class="img"></div>
</div>

<main>
    <div class="container">
        <form class="search-small" action="/search" method="get">

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
                    <c:when test="${status.index/3+1 == 1}">
                        <div class="hostel-1">
                            <div class="hostel-wrapper">
                                <div class="img-wrapper">
                                    <div class="img"></div>
                                </div>
                                <div class="info-wrapper">
                                    <a href="/search/${hostel.ID}" class="title">${hostel.name}</a>
                                    <p class="address">地址:${hostel.address}</p>
                                    <p class="intro">${hostel.introduction}</p>
                                    <p class="price">价格</p>
                                </div>

                            </div>
                        </div>
                    </c:when>
                    <c:when test="${status.index/3+1 == 2}">
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
    var checkIn = new Flatpickr($("#check-in-date")[0], {
        minDate: new Date(),
    });


    var checkOut = new Flatpickr($("#check-out-date")[0], {
        minDate: new Date(),
    });

</script>

<%@ include file="include/footer.jsp" %>