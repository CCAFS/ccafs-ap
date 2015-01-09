[#ftl]
[#assign title = "Activity Deliverables List" /]
[#assign globalLibs = ["jquery", "dataTable", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/activityDeliverableListReporting.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "status" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
    
<section class="content">
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] ([@s.text name="reporting.activityDeliverablesList" /] ${currentReportingLogframe.year?c})</h1>    

    <table id="deliverableList">
      <thead>
        <tr>
          <th id="title">[@s.text name="reporting.activityDeliverablesList.title" /]</th>
          <th id="type">[@s.text name="reporting.activityDeliverablesList.type" /]</th>
          <th id="year">[@s.text name="reporting.activityDeliverablesList.year" /]</th>
          <th id="status">[@s.text name="reporting.activityDeliverablesList.status" /]</th>
        </tr>
      </thead>
      <tbody>
        [#list activity.deliverables as product]
          <tr>
            <td class="left">
              <a href="
              [@s.url action='deliverables' includeParams='get']
                [@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param]
                [@s.param name='${deliverableRequestParameter}']${activity.id?c}[/@s.param]
              [/@s.url]
              " title="${product.description}">
                [#if activity.title?length < 50] ${product.description}</a> [#else] [@utilities.wordCutter string=product.description maxPos=50 /]...</a> [/#if]
            </td>
            <td> ${product.type.name}</td>
            <td> ${product.year} </td>
            <td> ${product.status.name} </td>               
          </tr>
        [/#list]  
      </tbody>
      <ul></ul>
    </table>
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]