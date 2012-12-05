[#ftl]
[#assign title = "Activity Status Report" /]
[#assign globalLibs = ["jquery", "jqueryUI"] /]
[#assign customJS = ["${baseUrl}/js/reporting/statusReporting.js", "${baseUrl}/js/global/utils.js"] /]
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
  [@s.form action="status!save"]
  <article class="halfContent">
    <h1>
      ${activity.leader.name?substring(0, activity.leader.name?index_of(" ") )} - [@s.text name="reporting.activityList.activity" /] ${activity.id}      
    </h1>
    
    <h6>[@s.text name="reporting.activityStatus.title" /]</h6>
    <p> ${activity.title} </p>
    
    <h6>[@s.text name="reporting.activityStatus.description" /]</h6>
    <p> ${activity.description} </p>
    
    <table class="generalInformation">
      <tr>
        <td> <b> [@s.text name="reporting.activityStatus.startDate" /] </b> </td><td> ${activity.startDate?string("MM/dd/yyyy")} </td>
        <td> <b> [@s.text name="reporting.activityStatus.budget" /] </b> </td><td> ${activity.budget.usd} </td>
      </tr>
      <tr>
        <td> <b> [@s.text name="reporting.activityStatus.endDate" /] </b> </td><td>  ${activity.endDate?string("MM/dd/yyyy")} </td>
        <td> <b> [@s.text name="reporting.activityStatus.milestone" /] </b> </td><td>
          <a class="popup" href="[@s.url action='milestone' ]
                    [@s.param name='${milestoneRequestParameter}']${activity.milestone.id}[/@s.param]
                   [/@s.url]">${activity.milestone.code}
          </a> 
        </td>
      </tr>
      <tr>
        <td> <b> [@s.text name="reporting.activityStatus.contactPerson" /] </b> </td>
        <td colspan="3">
           ${activity.contactPersons[0].name} (<a href="mailto: ${activity.contactPersons[0].email} ">${activity.contactPersons[0].email}</a>)</span> 
           [#if activity.contactPersons?size > 1] <a id="viewMoreContacts" href="">, show others..</a> [/#if]
        </td>        
      </tr>
    </table>
    
    <br />
    
    <span class="infoBoxLarge"> 
      <!-- span class="title">Activity status</span --> 
      <section class="status">
        [@s.radio name="activity.status" list="statusList" listKey="id" listValue="name" value="${activity.status.id}" /]       
        [#-- @customForm.radioButtonGroup labelName="Activity Status" name="activity.status" listOfValues="statusList" keyField="id" displayField="name" defaultValue="${activity.status.id}" / --]        
      </section>
    </span>
    
    
    
    [@customForm.textArea name="activity.statusDescription" i18nkey="reporting.activityStatus.statusDescription" rows=5 cols=100 required=true /]
    
    <span class="infoBoxLarge"> 
      <span class="title">Gender integration: </span> 
      <section class="status">
        [!-- @customForm.radioButton name="activity.status" i18nkey="Yes" value="y" / --] 
        [!-- @customForm.radioButton name="activity.status" i18nkey="No" value="n" / --]        
      </section>      
    </span>
    
    <span class="infoBox"><span class="title">Gender integration description:</span> </span>
    <p> Esta es la descripcion del gender integration </p>
    
    
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
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activity.id}" />
    [@s.submit type="button" name="save"]SAVE[/@s.submit]
      
    [#include "/WEB-INF/reporting/reportingStepSubMenu.ftl" /]  
  </article>
  
  [/@s.form]
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]