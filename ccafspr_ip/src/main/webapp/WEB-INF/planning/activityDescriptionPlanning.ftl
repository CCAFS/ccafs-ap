[#ftl]
[#assign title = "Activity Description" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/activityDescriptionPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activity" /]
[#assign currentStage = "activityDescription" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projects"},
  {"label":"projects", "nameSpace":"planning", "action":"projects"},
  {"label":"activities", "nameSpace":"planning/projects", "action":"activities", "param":"projectID=${project.id}" },
  {"label":"activityDescription", "nameSpace":"planning/projects/activities", "action":"" }
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
      [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor --]
      [#if !saveable]
        <p class="readPrivileges">
          [@s.text name="saving.read.privileges"]
            [@s.param][@s.text name="planning.activityDescription"/][/@s.param]
          [/@s.text]
        </p>
      [/#if]
      [#-- Activity Leader --]
      <h1 class="contentTitle">
        [@s.text name="planning.activityDescription.leader" /] 
      </h1>
      <div id="leader" class="borderBox clearfix">
        [#if activity.leader?has_content]
          [#-- Loading activity leader  --]
          
          [#-- Institution --]
          <div class="fullBlock">
            <b>[@s.text name="planning.activityDescription.institution" /]</b> ${activity.leader.currentInstitution.name}
          </div>
          [#-- Contact Name--]
          <div class="halfPartBlock">  
            <b>[@s.text name="planning.activityDescription.contactName" /]</b> ${activity.leader.firstName} ${activity.leader.lastName}
          </div>
          [#--  Contact Email --]
          <div class="halfPartBlock">
            <b>[@s.text name="planning.activityDescription.contactEmail" /]</b>${activity.leader.email}
          </div>
        [#else]
          [#-- Loading expected Leader --]
          
          [#-- Institution --]
          <div class="fullPartBlock chosen">
            [@customForm.select name="activity.expectedLeader.currentInstitution" label="" disabled=false i18nkey="planning.activityDescription.institution" listName="allPartners" keyFieldName="id"  displayFieldName="composedName" /]
          </div>
          [#-- Contact Name--]
          <div class="halfPartBlock">
            [@customForm.input name="activity.expectedLeader.firstName" type="text" i18nkey="planning.activityDescription.contactName" required=true /]
          </div>
          [#--  Contact Email --]
          <div class="halfPartBlock">
            [@customForm.input name="activity.expectedLeader.email" type="text"  i18nkey="planning.activityDescription.contactEmail" required=true  /]
          </div>
        [/#if]
      </div> <!-- End leader .borderBox -->
      
      [#-- Activity Main information --]
      <h1 class="contentTitle">
        [@s.text name="planning.activityDescription.mainInformation" /] 
      </h1>
      <div id="description" class="borderBox">
        [#-- Activity Title --]
        <div class="fullBlock">
          [@customForm.textArea name="activity.title" i18nkey="planning.activityDescription.title" required=true /]
        </div>
        [#-- Activity Description --]
        <div class="fullBlock">
          [@customForm.textArea name="activity.description" i18nkey="planning.activityDescription.description" required=true  /]
        </div>
        [#-- Start Date --]
        <div class="halfPartBlock">
          [@customForm.input name="activity.startDate" type="text" i18nkey="planning.activityDescription.startDate" required=true /]
        </div> 
        [#-- End Date --]
        <div class="halfPartBlock">
          [@customForm.input name="activity.endDate" type="text" i18nkey="planning.activityDescription.endDate" required=true /]
        </div>
        [#-- Cross Cutting --]
        <div id="activityCrossCutting" class="thirdPartBlock">
          <h6>[@s.text name="planning.activityDescription.crossCutting" /]</h6>
          <div class="checkboxGroup">
            [@s.checkboxlist name="activity.crossCuttings" list="ipCrossCuttings" listKey="id" listValue="name" cssClass="checkbox" value="crossCuttingIds" /]
          </div>
        </div>
      </div> <!-- End description .borderBox-->
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
  [#-- Hidden values used by js --]
  <input id="minDateValue" value="${startYear?c}-01-01" type="hidden"/>
  <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/>   
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]