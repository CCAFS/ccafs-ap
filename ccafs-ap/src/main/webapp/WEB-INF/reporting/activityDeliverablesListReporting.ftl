[#ftl]
[#assign title = "Activity Deliverables List" /]
[#assign globalLibs = ["jquery", "dataTable", "noty"] /]
[#assign customJS = ["${baseUrl}/js/reporting/activityDeliverableListReporting.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "deliverables" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
    
<section class="content">
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    [#include "/WEB-INF/reporting/activitiesReportingSubMenu.ftl" /]
    <h1>[#if currentUser.leader??]${currentUser.leader.name}[/#if] ([@s.text name="reporting.activityDeliverablesList" /] ${currentReportingLogframe.year?c})</h1>    
    <div id="filterBy">
      <div id="filter-title" class="filter" style="display:none">
      <h4>Filter by [@s.text name="reporting.activityDeliverablesList.title" /]:<h4>
      </div>
      <div id="filter-type" class="filter">
      <h4>Filter by [@s.text name="reporting.activityDeliverablesList.type" /]:<h4>
      </div>
      <div id="filter-year" class="filter">
      <h4>Filter by [@s.text name="reporting.activityDeliverablesList.year" /]:<h4>
      </div>
      <div id="filter-status" class="filter">
       <h4>Filter by [@s.text name="reporting.activityDeliverablesList.status" /]:<h4>
      </div>
    </div>

    <table id="deliverableList">  
      <thead>
        <tr>
          <th id="title">[@s.text name="reporting.activityDeliverablesList.description" /]</th>
          <th id="type">[@s.text name="reporting.activityDeliverablesList.type" /]</th>
          <th id="year">[@s.text name="reporting.activityDeliverablesList.year" /]</th>
          <th id="status">[@s.text name="reporting.activityDeliverablesList.status" /]</th>
          <th id=""></th>
          <th id=""></th>
        </tr>
      </thead> 
      
      <tbody>
        [#list activity.deliverables as product]
          [#assign isNewDeliverable = !product.expected && product.year == currentReportingLogframe.year]
          <tr>
            <td class="left">
              [#assign title]
                [#if product.description?has_content]${product.description}[#else][@s.text name="reporting.activityDeliverablesList.notDefined" /][/#if]
              [/#assign]
              <a href="
              [@s.url action='deliverables' includeParams='get']
                [@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param]
                [@s.param name='${deliverableRequestParameter}']${product.id?c}[/@s.param]
              [/@s.url]
              " title="${title}">
                [#if title?length < 50] ${title}</a> [#else] [@utilities.wordCutter string=title maxPos=50 /]...</a> [/#if]
            </td> 
            <td> ${product.type.name}</td>
            <td> ${product.year} </td>
            <td> ${product.status.name} </td>
            <td> [#if isNewDeliverable] [@s.text name="reporting.activityDeliverablesList.new" /] [#else] [@s.text name="reporting.activityDeliverablesList.planned" /] [/#if]</td>  
            <td>
              [#if isNewDeliverable] 
                <a href="
                [@s.url action='removeDeliverable' includeParams='get'] 
                  [@s.param name='${activityRequestParameter}']${activity.id?c}[/@s.param]
                  [@s.param name='${deliverableRequestParameter}']${product.id?c}[/@s.param]
                [/@s.url]
                " title="">
                  <img src="${baseUrl}/images/global/trash.png"> 
                </a>
              [#else]
                <img src="${baseUrl}/images/global/trash_disable.png"> 
              [/#if]
                              
            </td> 
          </tr>
        [/#list]  
      </tbody> 
    </table>
    


  [@s.form action="addNewDeliverable"]
    [#if canSubmit]
      <div class="addButtonSubmit">
        <input type="hidden" name="activityID" value="${activityID}" />
        [@s.submit type="button" name="add"][@s.text name="reporting.activityDeliverablesList.addDeliverable" /][/@s.submit]
      </div>
    [/#if]
  [/@s.form]  
  </article>
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]