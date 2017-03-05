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
    <div class="container card">
        <h1 class="title">修改密码</h1>
        <form action="${basePath}/password" method="post" autocomplete="off" id="password-form">
            <div class="grid">
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="old-password">原密码</label>
                    </div>
                    <div class="grid-content">
                        <input type="password" id="old-password" name="oldPassword"/>
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="new-password">新密码</label>
                    </div>
                    <div class="grid-content">
                        <input type="password" id="new-password" name="newPassword"/>
                        <span class="alert"></span>
                    </div>
                </div>
                <div class="grid-row">
                    <div class="grid-label">
                        <label for="confirm-password">确认密码</label>
                    </div>
                    <div class="grid-content">
                        <input type="password" id="confirm-password" name="confirmPassword"/>
                        <span class="alert"></span>
                    </div>
                </div>
            </div>
            <p class="alert">${alert}</p>
            <button id="submit-button" type="button" class="major-button">确认</button>
            <a href="${basePath}/info" class="minor-button">取消</a>
        </form>
    </div>
</main>

<script>
    $("#submit-button").click(function () {
        var oldPassword = $("#old-password").val();
        var oldPasswordAlert = $("#old-password + .alert");
        var isOldPassword = oldPassword != "";
        oldPasswordAlert.text(isOldPassword ? "" : "请输入旧密码！");

        var newPassword = $("#new-password").val();
        var newPasswordAlert = $("#new-password + .alert");
        var isNewPassword = newPassword != "";
        newPasswordAlert.text(isNewPassword ? "" : "请输入新密码！");

        var confirmPassword = $("#confirm-password").val();
        var confirmPasswordAlert = $("#confirm-password + .alert");
        var isConfirmPassword = newPassword == confirmPassword;
        confirmPasswordAlert.text(isConfirmPassword ? "" : "与新密码不一致！");

        if (isOldPassword && isNewPassword && isConfirmPassword) {
            $("#password-form").submit();
        }
    });
</script>

<%@ include file="include/footer.jsp" %>