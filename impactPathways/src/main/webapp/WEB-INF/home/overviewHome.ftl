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
      <p>[@s.text name="home.overview.deliverablesByType"/]</p>
      ${jsonDeliverable}
    </div>
    [#-- Expected Deliverables Per Year --]
    <div id="ExpectedDeliverablesPerYear">
      <br/><br/>
      <p>[@s.text name="home.overview.expectedDeliverablesPerYear"/]</p>
      <div id="containerExpectedDeliverable" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
      <div id="graph"></div>
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
      console.log(dataexpected.name);
      console.log(dataexpected.data);
      console.log(years[0]);
      // --------- Drawing the Expected Deliverable per Year Graphic
      $(function () {
        $('#containerExpectedDeliverable').highcharts({
            chart: {
                type: 'area'
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
      
      
      </script>
    </div>
  </div>
  
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]