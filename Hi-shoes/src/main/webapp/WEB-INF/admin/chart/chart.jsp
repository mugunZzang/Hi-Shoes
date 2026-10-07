<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<jsp:include page="../adminHeader.jsp" />

<script src="<%= ctxPath%>/Highcharts-10.3.1/code/highcharts.js"></script>
<script src="<%= ctxPath%>/Highcharts-10.3.1/code/modules/data.js"></script>
<script src="<%= ctxPath%>/Highcharts-10.3.1/code/modules/drilldown.js"></script>
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
  			url:"<%= ctxPath%>/admin/chart/ChartJSON.go",
  			dataType:"json",
  			success:function(json){
  				// console.log(JSON.stringify(json));
  				
  			  
  			  let resultArr1 = [];
  			  let resultArr2 = [];
  			  
  			  for(let i=0;i<json.length; i++){
  				  
  				  let month_arr = [];
  				  month_arr.push(Number(json[i].m_01));
  				  month_arr.push(Number(json[i].m_02));
  				  month_arr.push(Number(json[i].m_03));
  				  month_arr.push(Number(json[i].m_04));
  				  month_arr.push(Number(json[i].m_05));
  			 	  month_arr.push(Number(json[i].m_06));
  				  month_arr.push(Number(json[i].m_07));
  				  month_arr.push(Number(json[i].m_08));
  				  month_arr.push(Number(json[i].m_09));
  				  month_arr.push(Number(json[i].m_10));
  				  month_arr.push(Number(json[i].m_11));
  				  month_arr.push(Number(json[i].m_12));
  				  
  				  let obj = {name: json[i].cname,
  						     data: month_arr};
  				  
  				  resultArr.push(obj); // 배열속에 객체를 넣기
  			  }// end of for 
  			  
  			  //////////////////////////////////////////////////////////////////
  			Highcharts.chart('chart_container', {
  			    chart: {
  			        type: 'column'
  			    },
  			    title: {
  			        align: 'left',
  			        text: '업체별 신발 횟수'
  			    },
  			    subtitle: {
  			        align: 'left',
  			        text: ''
  			    },
  			    accessibility: {
  			        announceNewData: {
  			            enabled: true
  			        }
  			    },
  			    xAxis: {
  			        type: 'category'
  			    },
  			    yAxis: {
  			        title: {
  			            text: 'Total percent market share'
  			        }

  			    },
  			    legend: {
  			        enabled: false
  			    },
  			    plotOptions: {
  			        series: {
  			            borderWidth: 0,
  			            dataLabels: {
  			                enabled: true,
  			                format: '{point.y:.1f}%'
  			            }
  			        }
  			    },

  			    tooltip: {
  			        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
  			        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
  			    },

  			    series: ,
  			    drilldown: {
  			        breadcrumbs: {
  			            position: {
  			                align: 'right'
  			            }
  			        },
  			        series: 
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
                
                
    </div>
</div>


<jsp:include page="../adminFooter.jsp" />