<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 上午10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/member-nav.jsp" %>

<main>
    <div class="container card member-info">
        <h1 class="title">个人资料</h1>

        <div class="grid">
            <div class="grid-row">
                <div class="grid-label">
                    <label for="id">编号</label>
                </div>
                <div class="grid-content">
                    <span id="id">${memberInfoBean.member.ID}</span>
                </div>
            </div>

            <div class="grid-row">
                <div class="grid-label">
                    <label for="username">用户名</label>
                </div>
                <div class="grid-content">
                    <span id="username">${memberInfoBean.member.username}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="name">姓名</label>
                </div>
                <div class="grid-content">
                    <span id="name">${memberInfoBean.member.name}</span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="password">密码</label>
                </div>
                <div class="grid-content">
                    <a href="${basePath}/password" id="password" class="major-button-small">
                        修改密码
                    </a>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="state">状态</label>
                </div>
                <div class="grid-content">
                    <span id="state"
                          class="tag tag-${memberInfoBean.member.state.color}-current">${memberInfoBean.member.state.name}</span>
                    <c:if test="${memberInfoBean.member.state == 'Inactive'}">
                        <c:choose>
                            <c:when test="${memberInfoBean.account == null}">
                                <button type="button" class="major-button-small-disabled" disabled="disabled">激活
                                </button>
                                <span class="alert">填写银行卡号后可激活账户！</span>
                            </c:when>
                            <c:otherwise>
                                <form action="${basePath}/info/activate" method="post" class="inline">
                                    <button id="activate-button" type="submit" class="major-button-small">激活</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${memberInfoBean.member.state == 'Pause'}">
                        <form action="${basePath}/info/resume" method="post" class="inline">
                            <button id="resume-button" type="submit" class="major-button-small">恢复</button>
                        </form>
                    </c:if>
                    <c:if test="${memberInfoBean.member.state == 'Normal'}">
                        <form action="${basePath}/info/stop" method="post" class="inline">
                            <button id="stop-button" type="submit" class="major-button-small">停止</button>
                        </form>
                    </c:if>
                    <%--<button class="major-button-small">激活/恢复/停止</button>--%>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="level">级别</label>
                </div>
                <div class="grid-content">
                    <span id="level">${memberInfoBean.level}</span>
                    <span class="purchased-amount">消费金额:￥ <span
                            class="money">${memberInfoBean.purchasedAmount}</span></span>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="point">积分</label>
                </div>
                <div class="grid-content">
                    <span id="point">${memberInfoBean.member.point}</span>
                    <c:if test="${memberInfoBean.member.state == 'Normal'}">
                        <button type="button" class="major-button-small">兑换金额￥ <span
                                class="money">${memberInfoBean.member.point / 100}</span></button>
                    </c:if>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="money">余额</label>
                </div>
                <div class="grid-content">
                    <span id="money">￥ <span class="money">${memberInfoBean.member.money}</span></span>
                    <c:if test="${memberInfoBean.account != null}">
                        <button id="deposit-button" type="button" class="major-button-small"
                                data-mfp-src="#deposit-popup">充值
                        </button>
                    </c:if>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="account">银行卡号</label>
                </div>
                <div class="grid-content">
                    <c:choose>
                        <c:when test="${memberInfoBean.account == null}">
                            <span id="account">无</span>
                        </c:when>
                        <c:otherwise>
                            <span id="account">${memberInfoBean.account}</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="grid-row">
                <div class="grid-label">
                    <label for="contact">联系方式</label>
                </div>
                <div class="grid-content">
                    <span id="contact">${memberInfoBean.member.contact}</span>
                </div>
            </div>
        </div>

        <a href="${basePath}/info/edit" class="major-button">修改</a>
    </div>
</main>


<div id="deposit-popup" class="popup mfp-hide">
    <form action="${basePath}/info/deposit" method="post" id="deposit-form">
        <div class="money-popup">
            <div class="popup-row">
                <span>充值金额</span>
                <input id="deposit-money" type="text" name="money">
            </div>

            <div class="popup-row">
                <span>银行账户余额</span>
                <p>￥ <span class="money">${memberInfoBean.accountMoney}</span></p>
            </div>

            <div class="popup-buttons">
                <button type="button" id="deposit-cancel" class="minor-button left popup-close">取消</button>
                <button type="button" id="deposit-submit" class="major-button right">确定</button>
                <div class="clear-fix"></div>
                <span id="deposit-alert" class="alert"></span>
            </div>
        </div>
    </form>
</div>


<script src="${basePath}/js/jquery.magnific-popup.min.js"></script>
<script>
    $(".money").number(true, 2);

//    $('#activate-button').magnificPopup({
//        type: 'inline',
//        midClick: true
//    });

        $('#deposit-button').magnificPopup({
            type: 'inline',
            midClick: true
        });

    $('#deposit-submit').click(function () {
        var money = $('#deposit-money').val();
        var moneyAlert = $("#deposit-alert");
        var moneyReg = /^[0-9]+(.[0-9]?[0-9]?)?$/;
        var isMoney = moneyReg.test(money) && money <= ${memberInfoBean.accountMoney};
        moneyAlert.text(isMoney ? "" : "请输入正确的数额！");

        if (isMoney) {
            $('#deposit-form').submit();
        }

    });

    $('.popup-close').click(function () {
        $.magnificPopup.close();
    });

</script>

<%@ include file="include/footer.jsp" %>