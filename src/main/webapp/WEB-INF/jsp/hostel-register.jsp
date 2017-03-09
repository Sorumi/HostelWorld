<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 下午12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="include/header.jsp" %>


<main class="auth">
    <div class="container register">

        <div class="absolute-card left-card">
            <div class="card-container">
                <h1 class="web-title">HostelWorld</h1>
                <h2>Meet the world</h2>
                <a href="${basePath}/hostel/login" class="minor-button">登录</a>
            </div>
        </div>

        <div class="absolute-card right-card">
            <div class="card-container">
                <h1>旅舍注册</h1>
                <form action="${basePath}/hostel/register" method="post" autocomplete="off" id="register-form">
                    <div class="grid">
                        <div class="grid-row">
                            <div class="grid-label">
                                <label for="username">用户名</label>
                            </div>
                            <div class="grid-content">
                                <input type="text" id="username" name="username" value="${hostelRegisterBean.username}">
                                <p class="alert"></p>
                            </div>
                        </div>
                        <div class="grid-row">
                            <div class="grid-label">
                                <label for="password">密码</label>
                            </div>
                            <div class="grid-content">
                                <input type="password" id="password" name="password" value="${hostelRegisterBean.password}">
                                <p class="alert"></p>
                            </div>
                        </div>
                        <div class="grid-row">
                            <div class="grid-label">
                                <label for="confirm-password">确认密码</label>
                            </div>
                            <div class="grid-content">
                                <input type="password" id="confirm-password" name="confirmPassword" value="${hostelRegisterBean.confirmPassword}">
                                <p class="alert"></p>
                            </div>
                        </div>
                    </div>
                    <p class="alert">${alert}</p>
                    <button id="submit-button" type="button" class="major-button">注册</button>
                </form>
            </div>
        </div>

    </div>
</main>

<script>
    //validate
    $("#submit-button").click(function () {
        var username = $("#username").val();
        var usernameAlert = $("#username + .alert");
        var isUsername = username != "";
        usernameAlert.text(isUsername ? "" : "请输入用户名！");

        var password = $("#password").val();
        var passwordAlert = $("#password + .alert");
        var isPassword = password != "";
        passwordAlert.text(isPassword ? "" : "请输入密码！");

        var confirmPassword = $("#confirm-password").val();
        var confirmPasswordAlert = $("#confirm-password + .alert");
        var isConfirmPassword = password == confirmPassword;
        confirmPasswordAlert.text(isConfirmPassword ? "" : "与密码不一致！");

        if (isUsername && isPassword && isConfirmPassword) {
            $("#register-form").submit();
        }
    });
</script>
<%@ include file="include/footer.jsp" %>