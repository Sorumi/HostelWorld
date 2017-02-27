<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/25
  Time: 下午1:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<main>
    <div class="container card">
        <h1 class="title">会员登录</h1>

        <form action="/login" method="post">
            <table>
                <tr>
                    <td>
                        <label for="username">用户名</label>
                    </td>
                    <td>
                        <input type="text" id="username" name="username" value="${memberRegisterBean.username}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="password">密码</label>
                    </td>
                    <td>
                        <input type="password" id="password" name="password" value="${memberRegisterBean.password}"/>
                    </td>
                </tr>
            </table>
            <p class="alert">${alert}</p>
            <input type="submit" class="major-button" value="登录"/>
            <a href="/register" class="minor-button">注册</a>
        </form>
    </div>
</main>

<%@ include file="include/footer.jsp" %>