[#ftl]
[#assign title = "Activity Status Report" /]
[#assign globalLibs = ["jquery", "jqueryUI"] /]
[#assign customJS = ["${baseUrl}/js/reporting/statusReporting.js"] /]
[#assign customCSS = ["${baseUrl}/css/reporting/statusReporting.css", "${baseUrl}/css/libs/jqueryUI/jquery-ui-1.9.2.custom.css", ""] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "status" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
    
  <section>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  <article class="halfContent">
    <h1>
      ${activity.id} - [#if activity.title?length < 60] ${activity.title}</a> [#else] [@utilities.wordCutter string=activity.title maxPos=60 /]...</a> [/#if]
    </h1>
    
    <span class="infoBox"><span class="title">Description:</span> </span>
    <p> ${activity.description} </p>
    
    <span class="infoBox"> <span class="title">[@s.text name="reporting.activityStatus.startDate" /]</span> <span class="value">${activity.startDate?string("MM/dd/yyyy")}</span> </span>
    <span class="infoBox"> <span class="title">[@s.text name="reporting.activityStatus.budget" /]</span> <span class="value"> Falta budget</span> </span>
    
    <span class="infoBox"> <span class="title">[@s.text name="reporting.activityStatus.endDate" /]</span> <span class="value"> ${activity.endDate?string("MM/dd/yyyy")}</span> </span>
    <span class="infoBox"> <span class="title">[@s.text name="reporting.activityStatus.milestone" /]</span> <span class="value"> milestone</span> </span>
    
    <span class="infoBoxLarge"> 
      <span class="title">[@s.text name="reporting.activityStatus.contactPerson" /]</span> 
      <span class="value"> ${activity.contactPersons[0].name} (<a href="mailto: ${activity.contactPersons[0].email} ">${activity.contactPersons[0].email}</a>)</span> 
        [#if activity.contactPersons?size > 1] <a id="viewMoreContacts" href="">, show others..</a> [/#if]
    </span>
         
    <span class="infoBoxLarge"> 
      <span class="title">Activity status</span> 
      <section class="status">
        [#if activity.status == "Completed"]
          [@customForm.radioButton name="activity.status" i18nkey="reporting.activityStatus.statusComplete" value="Completed" checked=true /]
        [#else]
          [@customForm.radioButton name="activity.status" i18nkey="reporting.activityStatus.statusComplete" value="Completed" /] 
        [/#if]
        [#if activity.status == "Partially completed"][@customForm.radioButton name="activity.status" i18nkey="reporting.activityStatus.statusPartiallyComplete" value="PartiallyCompleted" checked=true /] [#else]
          [@customForm.radioButton name="activity.status" i18nkey="reporting.activityStatus.statusPartiallyComplete" value="PartiallyCompleted" checked=true /][/#if]
        [#if activity.status == "Uncompleted"][@customForm.radioButton name="activity.status" i18nkey="reporting.activityStatus.statusUncompleted" value="3" checked=true /] [#else]
          [@customForm.radioButton name="activity.status" i18nkey="reporting.activityStatus.statusUncompleted" value="3" checked=true /][/#if]
      </section>
    </span>
    
    [@customForm.textArea name="activity.statusDescription" i18nkey="reporting.activityStatus.statusDescription" rows=5 cols=100 required=true /]
    
    <span class="infoBoxLarge"> 
      <span class="title">Gender integration: </span> 
      <section class="status">
        [@customForm.radioButton name="activity.status" i18nkey="Yes" value="y" /] 
        [@customForm.radioButton name="activity.status" i18nkey="No" value="n" /]        
      </section>      
    </span>
    
    <span class="infoBox"><span class="title">Gender integration description:</span> </span>
    <p> Esta es la descripcion del gender integration </p>
    
    <span class="infoBox"><span class="title">Keywords:</span> </span>
    <p> Keywords used </p>
  
     <div id="contactPersons" title="Contact persons">
      <table id="contactPersonsTable">
        <thead>
          <tr> <th>Name</th> <th>Email</th></tr>
        </thead>
        <tbody>
          [#list activity.contactPersons as contactPerson]
            <tr> <td> ${contactPerson.name} </td> <td> ${contactPerson.email} </td></tr>
          [/#list]
        </tbody>
      </table>
    </div>
    
      
    [#include "/WEB-INF/reporting/reportingStepSubMenu.ftl" /]  
  </article>
  
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]