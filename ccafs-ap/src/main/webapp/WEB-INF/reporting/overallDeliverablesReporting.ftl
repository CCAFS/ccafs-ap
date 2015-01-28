[#ftl]
[#assign title = "Activity Deliverables List" /]
[#assign globalLibs = ["jquery", "dataTable", "noty", "star-rating"] /]
[#assign customJS = ["${baseUrl}/js/reporting/overallDeliverablesReporting.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "tlRpl" /]
[#assign currentStage = "tlOverallDeliverables" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]

<section class="content">
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]

  [@s.form action="overallDeliverables"]  
  <article class="halfContent">
    [#include "/WEB-INF/reporting/tlRplSubMenu.ftl" /]
    <h1>[@s.text name="reporting.overallDeliverables" /]: [#if currentUser.leader??]${currentUser.leader.name}[/#if]</h1>    

    <div id="filterBy">
      <div id="filter-title" class="filter" style="display:none">
      <h4>Filter by [@s.text name="reporting.activityDeliverablesList.description" /]:<h4>
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
      [#if currentUser.TL]
        <div id="filter-leader" class="filter">
         <h4>Filter by [@s.text name="reporting.overallDeliverables.leader" /]:<h4>
        </div>
      [/#if]
    </div>

    <table id="deliverableList">  
      <thead>
        <tr>
          <th id="title">[@s.text name="reporting.activityDeliverablesList.description" /]</th>
          <th id="type">[@s.text name="reporting.activityDeliverablesList.type" /]</th>
          <th id="year">[@s.text name="reporting.activityDeliverablesList.year" /]</th>
          <th id="status">[@s.text name="reporting.activityDeliverablesList.status" /]</th>
          [#if currentUser.TL]
            <th id="leader">[@s.text name="reporting.overallDeliverables.leader" /]</th>
          [/#if]
          <th id="cp-rank">[@s.text name="reporting.overallDeliverables.cpRank" /]</th>
          <th id="tl-rank">[@s.text name="reporting.overallDeliverables.tlRank" /]</th>
        </tr>
      </thead> 
      
      <tbody>
        [#list deliverables as product]
          [#assign isNewDeliverable = !product.expected && product.year == currentReportingLogframe.year]
          <tr>
            <td class="left">
              [#assign title]
                [#if product.description?has_content]${product.description}[#else][@s.text name="reporting.activityDeliverablesList.notDefined" /][/#if]
              [/#assign]
              <a href="
              [@s.url action='deliverables' includeParams='get']
                [@s.param name='${activityRequestParameter}']0[/@s.param]
                [@s.param name='${deliverableRequestParameter}']${product.id?c}[/@s.param]
              [/@s.url]
              " title="${title}">
                [#if title?length < 50] ${title}</a> [#else] [@utilities.wordCutter string=title maxPos=50 /]...</a> [/#if]
            </td> 
            <td> ${product.type.name}</td>
            <td> ${product.year} </td>
            <td> ${product.status.name} </td>
            [#if currentUser.TL]
              <td>${action.getDeliverableLeader(product.id)}</td>
            [/#if]
            <td> 
              <div class="rankBlock"> 
                [#assign score = product.getScoreByLeader(activityLeaderID) /]
                <input class="hover-star required" type="radio" [#if currentUser.TL]disabled name="deliverables[-${product_index}]" [#else] name="deliverables[${product_index}].scores[${activityLeaderID}]" [/#if] value="3" [#if score == 3] checked [/#if] title="[@s.text name='reporting.activityDeliverables.ranking.level.notImportant' /]"/>
                <input class="hover-star" type="radio" [#if currentUser.TL]disabled name="deliverables[-${product_index}]" [#else] name="deliverables[${product_index}].scores[${activityLeaderID}]" [/#if] value="2" [#if score == 2] checked [/#if] title="[@s.text name='reporting.activityDeliverables.ranking.level.important' /]"/>
                <input class="hover-star" type="radio" [#if currentUser.TL]disabled name="deliverables[-${product_index}]" [#else] name="deliverables[${product_index}].scores[${activityLeaderID}]" [/#if] value="1" [#if score == 1] checked [/#if] title="[@s.text name='reporting.activityDeliverables.ranking.level.veryImportant' /]"/>
                <div class="clearfix"></div>
              </div>
            </td>
            <td> 
              <div class="rankBlock"> 
                [#assign themeLeaderID = action.getDeliverableThemeLeader(product.id) /]
                [#assign score = product.getScoreByLeader(themeLeaderID) /]
                <input class="hover-star required" type="radio" [#if !currentUser.TL]disabled name="deliverables[-${product_index}]" [#else] name="deliverables[${product_index}].scores[${themeLeaderID}]" [/#if] value="3" [#if score == 3] checked [/#if] title="[@s.text name='reporting.activityDeliverables.ranking.level.notImportant' /]"/>
                <input class="hover-star" type="radio" [#if !currentUser.TL]disabled name="deliverables[-${product_index}]" [#else] name="deliverables[${product_index}].scores[${themeLeaderID}]" [/#if] value="2" [#if score == 2] checked [/#if] title="[@s.text name='reporting.activityDeliverables.ranking.level.important' /]"/>
                <input class="hover-star" type="radio" [#if !currentUser.TL]disabled name="deliverables[-${product_index}]"[#else] name="deliverables[${product_index}].scores[${themeLeaderID}]" [/#if] value="1" [#if score == 1] checked [/#if] title="[@s.text name='reporting.activityDeliverables.ranking.level.veryImportant' /]"/>
                <div class="clearfix"></div>
              </div>
            </td>
          </tr>
        [/#list]  
      </tbody> 
    </table>
    [#if canSubmit]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]

    </article>
  [/@s.form]
  </section>
[#include "/WEB-INF/global/pages/footer.ftl"]