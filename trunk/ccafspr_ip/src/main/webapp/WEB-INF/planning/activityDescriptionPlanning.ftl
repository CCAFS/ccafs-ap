[#ftl]
[#assign title = "Activity Description" /]
[#assign globalLibs = ["jquery", "noty", "autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityDescription" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"projects", "nameSpace":"planning/projects", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/activities", "action":"" },
  {"label":"activityDescription", "nameSpace":"planning/activities", "action":"activityDescription" }
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>

  [#include "/WEB-INF/planning/activityPlanningSubMenu.ftl" /]


  [@s.form action="activityDescription" cssClass="pure-form"]  
    <article class="halfContent" id="activityDescription">
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
      [#if !saveable]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="planning.activityDescription"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
      <h1 class="contentTitle">
      [@s.text name="planning.activityDescription" /] 
      </h1>
      <div id="activityDescription" >
      <fieldset class="fullBlock">  
        <div class="borderBox">
          [#if activity.leader?has_content]
            <div class="borderBox">
            [#-- Activity Leader --]
            <div id="activityLeader" class="">
              <div class="fullBlock">
                <b>[@s.text name="planning.activityDescription.leader" /]</b> ${activity.leader.currentInstitution.name}
              </div>
              [#-- Contact Name--]
              <div class="halfPartBlock">  
                <b>[@s.text name="planning.activityDescription.contactName" /]</b> ${activity.leader.firstName} ${activity.leader.lastName}
              </div>
              [#--  Contact Email --]
              <div class="halfPartBlock">
                <b>[@s.text name="planning.activityDescription.contactEmail" /]</b>${activity.leader.email}
              </div>
            </div>
          [#else]
            [#-- Activity Leader --]
            [@customForm.select name="activity.expectedLeader.institution.id" label=""  disabled=false i18nkey="planning.activityDescription.leader" listName="allPartners" keyFieldName="id"  displayFieldName="name" /]
            <div id="projectDescription" class="">
              [#-- Contact Name--]
            <div class="halfPartBlock">  
              [@customForm.input name="activity.expectedLeader.name" type="text" i18nkey="planning.activityDescription.contactName" required=true   /]
            </div>
            [#--  Contact Email --]
            <div class="halfPartBlock">          
              [@customForm.input name="activity.expectedLeader.email" type="text"  i18nkey="planning.activityDescription.contactEmail" required=true  /]
            </div>
          [/#if]
      </div><br/>
      [#-- Activity Title --]
      [@customForm.textArea name="activity.title" i18nkey="planning.activityDescription.title" required=true /]
      [#-- Activity Description --]
      [@customForm.textArea name="activity.description" i18nkey="planning.activityDescription.description" required=true  /]
      [#-- Start Date --]
      <div class="halfPartBlock">
        [@customForm.input name="activity.start" type="text" i18nkey="planning.activityDescription.startDate" required=true /]
      </div> 
      [#-- End Date --]
      <div class="halfPartBlock">
        [@customForm.input name="activity.end" type="text" i18nkey="planning.activityDescription.endDate" required=true /]
      </div>
      [#-- Cross Cutting --] 
      <div id="activityCrossCutting" class="thirdPartBlock">
        <h6>[@s.text name="planning.activityDescription.crossCutting" /]</h6>
        <div class="checkboxGroup">
          [@s.checkboxlist name="activity.crossCuttings" list="ipCrossCuttings" listKey="id" listValue="name" cssClass="checkbox" value="crossCuttingIds" /]
        </div>
      </div>
    </div> 

    </fieldset><br/>
    </div> 
    [#if saveable]
      <input type="hidden" name="activityID" value="${activity.id?c}">
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