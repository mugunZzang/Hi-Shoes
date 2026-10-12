<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<jsp:include page="../adminHeader.jsp" />

<script src="<%= ctxPath%>/Highcharts-10.3.1/code/highcharts.js"></script>
<script src="<%= ctxPath%>/Highcharts-10.3.1/code/modules/exporting.js"></script>
<script src="<%= ctxPath%>/Highcharts-10.3.1/code/modules/export-data.js"></script>
<script src="<%= ctxPath%>/Highcharts-10.3.1/code/modules/accessibility.js"></script>

<style type="text/css">

.highcharts-figure,
.highcharts-data-table table {
    min-width: 310px;
    max-width: 800px;
    margin: 1em auto;
}

#container {
    height: 400px;
}

.highcharts-data-table table {
    font-family: Verdana, sans-serif;
    border-collapse: collapse;
    border: 1px solid #ebebeb;
    margin: 10px auto;
    text-align: center;
    width: 100%;
    max-width: 500px;
}

.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}

.highcharts-data-table th {
    font-weight: 600;
    padding: 0.5em;
}

.highcharts-data-table td,
.highcharts-data-table th,
.highcharts-data-table caption {
    padding: 0.5em;
}

.highcharts-data-table thead tr,
.highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}

.highcharts-data-table tr:hover {
    background: #f1f7ff;
}



</style>
		
<script type="text/javascript">

$(function(){
	$.ajax({
	    url: "<%= ctxPath%>/admin/chart/chartJSON.go",
	    dataType: "json",
	
	    success: function(json) {
	
	        // 업체별 총금액 저장
	        let suppliers = [];
	        let amounts = [];
	
	        $.each(json, function(index, item) {
	
	            let supplier = item.supname;
	            let amount = Number(item.totalamount);
	
	            // 이미 등록된 업체인지 확인
	            let idx = $.inArray(supplier, suppliers);
	
	            if (idx == -1) {
	                // 새로운 업체라면 업체명과 금액 추가
	                suppliers.push(supplier);
	                amounts.push(amount);
	            }
	            else {
	                // 이미 있는 업체라면 기존 금액에 합산
	                amounts[idx] += amount;
	            }
	        });
	
	        // 차트 데이터 구성
	        let resultArr = [{
	            name: '총금액',
	            data: amounts
	        }];
	
	        // Highcharts 차트 생성
	        Highcharts.chart('chart_container', {
	            chart: {
	                type: 'column'
	            },
	
	            title: {
	                text: '업체별 입고 완료 총금액'
	            },
	
	            xAxis: {
	                categories: suppliers,
	                title: {
	                    text: '업체명'
	                }
	            },
	
	            yAxis: {
	                min: 0,
	                title: {
	                    text: '총금액 (원)'
	                }
	            },
	
	            tooltip: {
	                valueSuffix: '원',
	                valueDecimals: 0
	            },
	
	            plotOptions: {
	                column: {
	                    borderWidth: 0
	                }
	            },
	
	            series: resultArr
	        });
	    },
	
	    error: function(request, status, error){
	        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	      }
	}); // 1번째 차트 끝
	
	$.ajax({
        url: "<%= ctxPath%>/admin/chart/chartJSON2.go",
        dataType: "json",

        success: function(json) {

            let suppliers = [];
            let quantities = [];

            $.each(json, function(index, item) {
                suppliers.push(item.supname);
                quantities.push(Number(item.totalqty));
            });

            let resultArr = [{
                name: '총수량',
                data: quantities
            }];

            Highcharts.chart('chart_container2', {
                chart: {
                    type: 'column'
                },

                title: {
                    text: '업체별 입고 완료 총수량'
                },

                xAxis: {
                    categories: suppliers,
                    title: {
                        text: '업체명'
                    }
                },

                yAxis: {
                    min: 0,
                    title: {
                        text: '총수량 (개)'
                    }
                },

                tooltip: {
                    valueSuffix: '개',
                    valueDecimals: 0
                },

                plotOptions: {
                    column: {
                        borderWidth: 0
                    }
                },

                series: resultArr
            });
        },
        error: function(request, status, error){
	        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	      }
    });
	

		
		
});// end of $(function(){


</script>
<div class="container-fluid"
     id="container"
     style="position: relative;
            top: 90px;
            padding: 0% 7%;">

    <!-- 페이지 제목 + 브레드크럼 -->
    <div class="d-flex justify-content-between align-items-center"
         style="padding: 20px 0px;
                margin-bottom: 15px;">

        <p class="mb-0 fs-4 fw-semibold">
            통계차트
        </p>

        <nav style="--bs-breadcrumb-divider: '>';">

            <ol class="breadcrumb mb-0">

                <li class="breadcrumb-item">
                    <a href="#"
                       class="text-decoration-none">
                        Home
                    </a>
                </li>

                <li class="breadcrumb-item active"
                    aria-current="page">
                    통계차트
                </li>

            </ol>

        </nav>

    </div>
    
    <div class="tab-content" style="background-color: #ffffff;
                width: 100%;
                min-height: 500px;
                border: 1px solid #eeeeee;
                padding: 25px;">
                
                <div id="chart_container"></div>
                <div id="chart_container2" ></div>
                
                
    </div>
</div>


<jsp:include page="../adminFooter.jsp" />