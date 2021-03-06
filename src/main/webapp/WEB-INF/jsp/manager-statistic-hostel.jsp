<%--
  Created by IntelliJ IDEA.
  User: Sorumi
  Date: 17/3/12
  Time: 上午10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@ include file="include/header.jsp" %>

<div class="top-fix"></div>

<%@ include file="include/manager-nav.jsp" %>

<main>
    <div class="container">
        <div class="card statistic">
            <h1 class="title">旅舍统计</h1>
            <div class="select-row">
                <span>旅舍</span>
                <div class="select-wrapper">
                    <select id="hostel-select">
                        <c:forEach var="hostel" items="${hostels}">
                            <option value="${hostel.ID}">${hostel.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="select-row">
                <span>月份</span>
                <div class="month-picker date-select"></div>
            </div>
            <div id="myChart">
            </div>
        </div>
    </div>
</main>

<script src="${basePath}/js/select2.full.min.js"></script>
<script src="${basePath}/js/jquery-ui.min.js"></script>
<script src="${basePath}/js/month-picker.js"></script>
<script src="${basePath}/js/zingchart.min.js"></script>
<script>
    zingchart.MODULESDIR = "https://cdn.zingchart.com/modules/";
    ZC.LICENSE = ["569d52cefae586f634c54f86dc99e6a9", "ee6b7db5b51705a13dc2339db3edaf6d"];
</script>

<script>
    $("#hostel-select").select2();

    $(".money").number( true, 2 );

    $('.month-picker').monthpicker({
        'max': $.datepicker.formatDate("yy-mm", new Date()),
        'onChange': function (date) {
            loadStatistics();
        }
    });

    $('#hostel-select').on('change',function() {
        loadStatistics();
    });

    function loadStatistics() {
        var month = $('.month-picker input').val();
        var hostelID =  $('#hostel-select').val();
        var data = { "hostelID" : hostelID };

        $.ajax({
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            url:"${basePath}/admin/statistic/hostel/" + hostelID + "/" + month,
            data: JSON.stringify(data),

            success: function (data) {

                refreshStatistic(data);
            }
        });
    }
    function refreshStatistic(data) {
        var myConfig = {
            type: 'line',
            backgroundColor: 'transparent',
            title: {
                //text: '统计',
                adjustLayout: true,
                fontColor: "#888888",
                marginTop: 7,
                fontFamily: 'PingFang SC',
                fontWeight: '200',
            },
            legend: {
                align: 'center',
                verticalAlign: 'top',
                backgroundColor: 'none',
                borderWidth: 0,
                item: {
                    fontColor: '#888888',
                    cursor: 'hand'
                },
                marker: {
                    type: 'circle',
                    borderWidth: 0,
                    cursor: 'hand'
                }
            },
            plotarea: {
                margin: 'dynamic 70'
            },
            plot: {
                aspect: 'spline',
                lineWidth: 2,
                marker: {
                    borderWidth: 0,
                    size: 3
                }
            },
            scaleX: {
                lineColor: '#DDDDDD',
                zooming: true,
                zoomTo: [0, 15],
                minValue: data.time,
                step: 'day',
                tick: {
                    lineColor: '#888888'
                },
                minorTick: {
                    lineColor: '#888888'
                },
                item: {
                    fontColor: '#888888'
                },
                transform: {
                    type: 'date',
                    all: '%M %d'
                }
            },
            scaleY: {
                minorTicks: 1,
                lineColor: '#DDDDDD',
                tick: {
                    lineColor: '#888888'
                },
                minorTick: {
                    lineColor: '#888888'
                },
                minorGuide: {
                    visible: true,
                    lineWidth: 1,
                    lineColor: '#E3E3E5',
                    alpha: 0.7,
                    lineStyle: 'dashed'
                },
                guide: {
                    lineStyle: 'dashed',
                },
                item: {
                    fontColor: '#888888'
                }
            },
            tooltip: {
                fontColor: "#ffffff",
                borderWidth: 0,
                borderRadius: 3
            },
            preview: {
                adjustLayout: true,
                borderColor: '#DDDDDD',
                mask: {
                    backgroundColor: '#E3E3E5'
                }
            },
            crosshairX: {
                plotLabel: {
                    multiple: true,
                    borderRadius: 3
                },
                scaleLabel: {
                    backgroundColor: '#53535e',
                    borderRadius: 3
                },
                marker: {
                    size: 7,
                    alpha: 0.5
                }
            },
            crosshairY: {
                lineColor: '#E3E3E5',
                type: 'multiple',
                scaleLabel: {
                    decimals: 2,
                    borderRadius: 3,
                    offsetX: -5,
                    fontColor: "#FFF",
                    bold: true
                }
            },
            shapes: [
                {
                    type: 'rectangle',
                    id: 'view_all',
                    height: '20px',
                    width: '75px',
                    borderColor: '#E3E3E5',
                    borderWidth: 0,
                    borderRadius: 3,
                    x: '85%',
                    y: '12%',
                    backgroundColor: '#dddddd',
                    cursor: 'hand',
                    label: {
                        text: '查看全部',
                        fontColor: '#ffffff',
                        fontSize: 12,
                        bold: false
                    },
                }
            ],
            series: [
                {
                    text: "预定",
                    values: data.booked,
                    lineColor: '#FD9C4B',
                    marker: {
                        backgroundColor: '#FD9C4B'
                    },
                },
                {
                    text: "入住",
                    values: data.checkIn,
                    lineColor: '#12B7F3',
                    marker: {
                        backgroundColor: '#12B7F3'
                    }
                },
                {
                    text: "退房",
                    values: data.checkOut,
                    lineColor: '#00CCCC',
                    marker: {
                        backgroundColor: '#00CCCC'
                    }
                },
                {
                    text: "退订",
                    values: data.cancelled,
                    lineColor: '#FC537D',
                    marker: {
                        backgroundColor: '#FC537D'
                    }
                },
                {
                    text: "过期",
                    values: data.expired,
                    lineColor: '#BC52FD',
                    marker: {
                        backgroundColor: '#BC52FD'
                    }
                }
            ]
        };

        zingchart.render({
            id: 'myChart',
            data: myConfig,
            height: '500',
            width: '960'
        });

        zingchart.shape_click = function (p) {
            if (p.shapeid == "view_all") {
                zingchart.exec(p.id, 'viewall');
            }
        };
    }

    //    loadStatistics();
</script>
