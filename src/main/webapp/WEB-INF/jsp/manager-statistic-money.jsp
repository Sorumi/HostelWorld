<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/3/11
  Time: 下午2:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/manager-nav.jsp" %>

<main>
    <div class="container">
        <div class="card statistic">
            <h1 class="title">财务统计</h1>
            <div class="select-row">
                <span>财务总额</span>
                <div class="">￥ <div class="money">${app.money}</div></div>
            </div>
            <div class="select-row">
                <span>抽成比率</span>
                <div class=""><div class="money">${app.commission}</div></div>
            </div>
            <div class="grid finance-list">
                <div class="grid-row">
                    <div class="finance-time title">时间</div>
                    <div class="finance-order title">订单编号</div>
                    <div class="finance-event title">动作</div>
                    <div class="finance-money-change title">变化金额</div>
                    <div class="finance-money-result title">结果金额</div>
                </div>
                <c:forEach var="record" items="${financeRecords}">
                    <div class="grid-row">
                        <div class="finance-time">${record.financeRecord.time}</div>
                        <div class="finance-order"><a href="${basePath}/admin/order/${record.financeRecord.orderID}">${record.financeRecord.orderID}</a></div>
                        <div class="finance-event"><span class="tag tag-${record.financeRecord.type.color}-current">${record.financeRecord.type.name}</span></div>
                        <div class="finance-money-change">
                            <c:choose>
                                <c:when test="${record.financeRecord.type == 'Book'}">
                                    +
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                            ￥<span class="money">${record.financeRecord.money}</span></div>
                        <div class="finance-money-result">
                            <c:if test="${record.resultMoney < 0}">
                            -
                            </c:if>
                            ￥<span class="money">${record.resultMoney}</span></div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</main>


<script>
    $(".money").number( true, 2 );
</script>

<%@ include file="include/footer.jsp" %>