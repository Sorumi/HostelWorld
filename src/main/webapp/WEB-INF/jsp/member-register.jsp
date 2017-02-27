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

        <form action="/register" method="post" autocomplete="off">
            <table>
                <tr>
                    <td>
                        <label for="username">用户名</label>
                    </td>
                    <td>
                        <input type="text" id="username" name="username" value="${memberRegisterBean.username}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="password">密码</label>
                    </td>
                    <td>
                        <input type="password" id="password" name="password" value="${memberRegisterBean.password}">
                    </td>
                </tr>

                <tr>
                    <td>
                        <label for="confirm-password">确认密码</label>
                    </td>
                    <td>
                        <input type="password" id="confirm-password" name="confirmPassword" value="${memberRegisterBean.confirmPassword}">
                    </td>
                </tr>

                <tr>
                    <td>
                        <label for="name">姓名</label>
                    </td>
                    <td>
                        <input type="text" id="name" name="name" value="${memberRegisterBean.name}">
                    </td>
                </tr>
            </table>
            <p class="alert">${alert}</p>
            <input type="submit" class="major-button" value="注册"/>
            <a href="/login" class="minor-button">登录</a>

        </form>
    </div>
</main>

<%@ include file="include/footer.jsp" %>