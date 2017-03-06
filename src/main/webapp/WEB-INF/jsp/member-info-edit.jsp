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
        <h1 class="title">修改个人资料</h1>
        <form action="${basePath}/info/edit" method="post" autocomplete="off" id="edit-form">
            <div class="grid">
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="id">编号</label>
                    </div>
                    <div class="grid-content">
                        <span id="id">${member.ID}</span>
                    </div>
                </div>

                <div class="grid-row">
                    <div class="grid-label">
                        <label for="username">用户名</label>
                    </div>
                    <div class="grid-content">
                        <span id="username">${member.username}</span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="name">姓名</label>
                    </div>
                    <div class="grid-content">
                        <input type="text" id="name" name="name" value="${member.name}"/>
                        <span class="alert"></span>
                    </div>
                </div>

                <div class="grid-row">
                    <div class="grid-label">
                        <label for="account">银行卡号</label>
                    </div>
                    <div class="grid-content">
                        <input type="text" id="account" name="account" value="${member.account}"/>
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="contact">联系方式</label>
                    </div>
                    <div class="grid-content">
                        <input type="text" id="contact" name="contact" value="${member.contact}"/>
                        <span class="alert"></span>
                    </div>
                </div>
            </div>
            <button class="major-button" type="button" id="submit-button">保存</button>
            <a href="${basePath}/info" class="minor-button">取消</a>
        </form>
    </div>
</main>

<script>
    //validate
    $("#submit-button").click(function () {
        var name = $("#name").val();
        var nameAlert = $("#name + .alert");
        var isName = name != "";
        nameAlert.text(isName ? "" : "请输入姓名！");

        var account = $("#account").val();
        var accountAlert = $("#account + .alert");
        var accountReg = /^[0-9]+$/;
        var isAccount = accountReg.test(account);
        accountAlert.text(isAccount ? "" : "请输入正确银行卡号！");

        var contact = $("#contact").val();
        var contactAlert = $("#contact + .alert");
        var contactReg = /^[0-9]+$/;
        var isContact = contactReg.test(contact);
        contactAlert.text(isContact ? "" : "请输入正确的联系方式！");

        if (isName && isAccount && isContact) {
            $("#edit-form").submit();
        }
    });
</script>

<%@ include file="include/footer.jsp" %>