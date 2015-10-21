[#ftl]
[#assign title = "Overview - CCAFS P&R" /]
[#assign globalLibs = ["jquery", "noty", "jreject", "highcharts"] /]
[#assign customJS = ["${baseUrl}/js/home/overviewHome.js"] /]

[#assign currentSection = "overview" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<article id="overviewHome">
  <h1 class="">P&R Overview</h1>
  <div class="borderBox">
    [#-- Deliverables By Type --]
    <div id="DeliverablesByType">
      <div id="containerDeliverableByType" style="min-width: 310px; height: 500px; margin: 0 auto"></div>
    </div>
    [#-- Expected Deliverables Per Year --]
    <div id="ExpectedDeliverablesPerYear">
      <br/><br/>
      <div id="containerExpectedDeliverable" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
      
    </div>
  </div>
  
</article>
<script language="javaScript">
      // -------- Organizing the JSON variable expectedDeliverable to match the format of HighCharts ------------
      var expected = ${jsonexpectedDeliverable};
      var dataexpected= ["name","data"];
      var years = new Array();
      dataexpected.name = new Array();
      dataexpected.data = new Array();
      dataexpected.name[0] = "Expected Deliverables per Year";
      dataexpected.data[0] = new Array();
      for(var i in expected)
      {
           years[i] = parseInt(expected[i].year);
           dataexpected.data[i] = parseInt(expected[i].count);
      }
      // --------- Drawing the Expected Deliverable per Year Graphic ------------
      $(function () {
        $('#containerExpectedDeliverable').highcharts({
            chart: {
                type: 'area'
            },
            plotOptions: {
            series: {
                color: '#058DC7'
                }
            },
            title: {
                text: 'Expected Deliverables per year'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                allowDecimals: false,
                labels: {
                    formatter: function () {
                        return this.value; // clean, unformatted number for year
                    }
                }
            },
            yAxis: {
                title: {
                    text: '# Expected Deliverables'
                },
                labels: {
                    formatter: function () {
                        return this.value ;
                    }
                }
            },
            tooltip: {
                pointFormat: '<b>{point.y:,.0f}</b> {series.name} <br/> {point.x}'
            },
            plotOptions: {
                area: {
                    pointStart: years[0],
                    marker: {
                        enabled: false,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                }
            },
            series: [{
              name : dataexpected.name[0],
              data : dataexpected.data
            }]
            
          });
        });
      
      // -------- Organizing the JSON variable DeliverablebyType to match the format of HighCharts ------------
      var deliverablesByType = ${jsonDeliverable}
      var dataByType = ["name","data"];
      var types = new Array();
      dataByType.name = new Array();
      dataByType.data = new Array();
      dataByType.name[0] = "Deliverables by type";
      dataByType.data[0] = new Array();
      for(var i in deliverablesByType)
      {
           types[i] = deliverablesByType[i].name;
           dataByType.data[i] = parseInt(deliverablesByType[i].count);
      }
      // -------- Drawing the Deliverables By Type Graphic
      $(function () {
        Highcharts.setOptions({
        colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4']
        });
        $('#containerDeliverableByType').highcharts({
          chart: {
            type: 'bar'
          },
          title: {
            text: 'Deliverables by type'
          },
          subtitle: {
            text: ''
          },
          xAxis: {
            categories: types,
            title: {
              text: null
            }
          },
          plotOptions: {
            series: {
                colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4']
              }
          },
          yAxis: {
            min: 0,
            title: {
              text: '# of Deliverables',
              align: 'high'
            },
            labels: {
              overflow: 'justify'
            }
          },
          tooltip: {
            valueSuffix: ' '
          },
          plotOptions: {
            bar: {
              dataLabels: {
                enabled: true
              }
            }
          },
          legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -40,
            y: 80,
            floating: true,
            borderWidth: 1,
            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
            shadow: true
          },
          credits: {
            enabled: false
          },
          series: [{
            name : dataByType.name[0],
            data: dataByType.data
          }]
        });
      });
      </script>
[#include "/WEB-INF/global/pages/footer.ftl"]