<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/2/28
  Time: 下午4:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/manager-nav.jsp" %>

<main>
    <div class="container">
        <div class="card level-management">
            <h1 class="title">等级管理</h1>
            <div class="right-buttons">
                <button id="add-button" type="button" class="major-button-small">新增等级</button>
            </div>
            <div class="grid level-list">
                <div class="grid-row">
                    <div class="level-num title">等级</div>
                    <div class="level-points title">需要点数</div>
                    <div class="level-discount title">积分</div>
                    <div class="level-operation title">操作</div>
                </div>
                <c:forEach var="level" items="${levelList}" varStatus="status">
                    <div id="level-1" class="grid-row level">
                        <div class="level-num">${level.ID}</div>
                        <div class="level-points">${level.points}</div>
                        <div class="level-discount">${level.discount}</div>
                        <div class="level-operation">
                            <button type="button" class="major-button-small edit-button">编辑</button>
                            <c:if test="${status.last}">
                                <%--<form action="/admin/level/${level.ID}/" method="post" class="inline" id="delete-form">--%>
                                <%--<input type="hidden" name="_method" value="DELETE">--%>
                                <button type="button" class="minor-button-small delete-button">删除</button>
                                <%--</form>--%>
                            </c:if>

                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</main>

<script>

    function refreshEditButtons() {
        $('.edit-button').click(function () {

            var parent = $(this).parents('.level');
            var num = parent.find('.level-num').text();
            var points = parent.find('.level-points').text();
            var discount = parent.find('.level-discount').text();

            parent.html('<form action="/admin/level/' + num + '/edit" class="inline">' +
                    '<div class="level-num">' + num + '</div>' +
                    '<div class="level-points"><input type="text" name="points" value="' + points + '"></div>' +
                    '<div class="level-discount"><input type="text" name="discount" value="' + discount + '"></div>' +
                    '<div class="level-operation">' +
                    '<button type="button" class="major-button-small save-button">保存</button>' +
                    ' <button type="button" class="major-button-small cancel-button">取消</button>' +
                    '<span class="alert"></span>' +
                    '</div>' +
                    '</form>');

            refreshSaveButtons();
            parent.find('.cancel-button').click(function () {
                console.log('cancel' + num);
                var newParent = $(this).parents('.level');
//                var num = parent.find('.level-num').text();
                newParent.html('<div class="level-num">' + num + '</div>' +
                        ' <div class="level-points">' + points + '</div>' +
                        '<div class="level-discount">' + discount + '</div>' +
                        '<div class="level-operation">' +
                        '<button type="button" class="major-button-small edit-button">编辑</button>' +
                        '</div>');

                var list = $('.level-list');
                var length = list.children('.level').length;
                if (num == length) {
                    $('#level-' + num).find('.level-operation').append(
                            '<button type="button" class="minor-button-small delete-button">删除</button>'
                    );
                }

                refreshEditButtons();
            });
        });
    }

    function refreshSaveButtons() {
        $('.save-button').click(function () {
            var parent = $(this).parents('.level');
            var alert = parent.find('.alert');
            var num = parent.find('.level-num').text();
            var points = parent.find('.level-points input').val();
            var discount = parent.find('.level-discount input').val();

            var pointsReg = /^[1-9][0-9]*$/;
            var isPoints = pointsReg.test(points);
            if (!isPoints) {
                alert.text('请输入正确的积分!');
                return;
            }

            var discountReg = /^0.[0-9][0-9]?$/;
            var isDiscount = discountReg.test(discount);
            if (!isDiscount) {
                alert.text('请输入正确的优惠折扣!');
                return;
            }

            var form = parent.find('form');
            form.submit();
        });
    }

    function refreshDeleteButtons() {
        $('.delete-button').click(function () {
            var parent = $(this).parents('.level');
            var num = parent.find('.level-num').text();

            $.ajax({
                type: "DELETE",
                dataType: "json",
                contentType: "application/json",
                url: "/admin/level/" + num,
                data: {},

                complete: function (data) {
                    console.log(data);
                    if (data.responseText == 'SUCCESS') {
                        parent.remove();
                    }
                }

            });

        });
    }

    $('#add-button').click(function () {

        var cancelAddButton = $('.cancel-add-button');
        if (cancelAddButton.length > 0) {
            return;
        }

        var list = $('.level-list');
        var num = list.children('.level').length + 1;

        $('#delete-form').remove();
        list.append('<div id="level-' + num + '" class="grid-row level">' +
                ' <form action="/level/' + num + '/add" class="inline">' +
                '<div class="level-num">' + num + '</div>' +
                ' <div class="level-points"><input type="text" name="points" value=""></div>' +
                '<div class="level-discount"><input type="text" name="discount" value=""></div>' +
                '<div class="level-operation">' +
                '<button type="button" class="major-button-small save-button">保存</button>' +
                '<button type="button" class="major-button-small cancel-add-button">取消</button>' +
                '<span class="alert"></span>' +
                '</div>' +
                '</form>' +
                '</div>');

        refreshSaveButtons();

        $('.cancel-add-button').click(function () {
            var num = list.children('.level').length;
            $('#level-' + num).remove();
            num--;
            var lastLevel = $('#level-' + num).find('.level-operation');

            if (lastLevel.find('.cancel-button').length <= 0) {
                lastLevel.append(
                        '<button type="button" class="minor-button-small delete-button">删除</button>'
                );
            }
        });
    });

    refreshEditButtons();
    refreshDeleteButtons();
</script>

<%@ include file="include/footer.jsp" %>