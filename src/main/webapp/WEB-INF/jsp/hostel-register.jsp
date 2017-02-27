<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/26
  Time: 下午12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<main>
    <div class="container card">
        <h1 class="title">旅舍注册</h1>

        <form action="/hostel/register" method="post" autocomplete="off">
            <table>
                <tr>
                    <td>
                        <label for="username">用户名</label>
                    </td>
                    <td>
                        <input type="text" id="username" name="username" value="${hostelRegisterBean.username}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="password">密码</label>
                    </td>
                    <td>
                        <input type="password" id="password" name="password" value="${hostelRegisterBean.password}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="confirm-password">确认密码</label>
                    </td>
                    <td>
                        <input type="password" id="confirm-password" name="confirmPassword" value="${hostelRegisterBean.confirmPassword}">
                    </td>
                </tr>
            </table>

            <p class="alert">${alert}</p>
            <input type="submit" class="major-button" value="注册"/>
            <a href="/hostel/login" class="minor-button">登录</a>
        </form>
    </div>
</main>

<%@ include file="include/footer.jsp" %>