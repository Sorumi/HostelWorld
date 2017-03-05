<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 下午1:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="include/header.jsp" %>

<main class="auth">
    <div class="container login">

        <div class="absolute-card left-card">
            <div class="card-container">
                <h1>会员登录</h1>
                <form action="${basePath}/login" method="post" id="login-form" autocomplete="off">
                    <div class="grid">
                        <div class="grid-row">
                            <div class="grid-label">
                                <label for="username">用户名</label>
                            </div>
                            <div class="grid-content">
                                <input type="text" id="username" name="username" value="${memberRegisterBean.username}"/>
                                <p class="alert"></p>
                            </div>
                        </div>
                        <div class="grid-row">
                            <div class="grid-label">
                                <label for="password">密码</label>
                            </div>
                            <div class="grid-content">
                                <input type="password" id="password" name="password" value="${memberRegisterBean.password}"/>
                                <p class="alert"></p>
                            </div>
                        </div>
                    </div>
                    <p class="alert">${alert}</p>
                    <button type="button" id="submit-button" class="major-button">登录</button>
                </form>
            </div>
        </div>

        <div class="absolute-card right-card">
            <div class="card-container">
                <h1 class="web-title">HostelWorld</h1>
                <h2>Meet the world</h2>
                <a href="${basePath}/register" class="minor-button">注册</a>
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

        if (isUsername && isPassword) {
            $("#login-form").submit();
        }
    });
</script>

<%@ include file="include/footer.jsp" %>