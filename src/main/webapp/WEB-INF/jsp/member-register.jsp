<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 下午12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<main>
    <div class="container card">
        <h1 class="title">会员注册</h1>

        <form action="${basePath}/register" method="post" autocomplete="off" id="register-form">
            <table>
                <tr>
                    <td>
                        <label for="username">用户名</label>
                    </td>
                    <td>
                        <input type="text" id="username" name="username" value="${memberRegisterBean.username}">
                        <span class="alert"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="password">密码</label>
                    </td>
                    <td>
                        <input type="password" id="password" name="password" value="${memberRegisterBean.password}">
                        <span class="alert"></span>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label for="confirm-password">确认密码</label>
                    </td>
                    <td>
                        <input type="password" id="confirm-password" name="confirmPassword" value="${memberRegisterBean.confirmPassword}">
                        <span class="alert"></span>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label for="name">姓名</label>
                    </td>
                    <td>
                        <input type="text" id="name" name="name" value="${memberRegisterBean.name}">
                        <span class="alert"></span>
                    </td>
                </tr>
            </table>
            <p class="alert">${alert}</p>
            <input id="submit-button" class="major-button" value="注册"/>
            <a href="${basePath}/login" class="minor-button">登录</a>

        </form>
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

        var name = $("#name").val();
        var nameAlert = $("#name + .alert");
        var isName = name != "";
        nameAlert.text(isName ? "" : "请输入名称！");

        if (isUsername && isPassword && isConfirmPassword && isName) {
            $("#register-form").submit();
        }
    });
</script>

<%@ include file="include/footer.jsp" %>