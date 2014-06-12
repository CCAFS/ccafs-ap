[#ftl]
[#assign title = "Activity Objectives Planning" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/planning/objectivesPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "objectives" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.objectives.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  
  
  [@s.form action="objectives" cssClass="pure-form"]  
  <article class="halfContent">
    <h1 class="contentTitle">
      ${activity.leader.acronym}: [@s.text name="planning.mainInformation.activity" /] ${activity.activityId}
    </h1>
    
    [#-- Activity identifier --]
    <input name="activityID" value="${activity.id?c}" type="hidden"/>
    
    <fieldset id="objectivesBlock">
      <legend><h6> [@s.text name="planning.objectives" /]  </h6></legend>
    
    [#if activity.objectives?has_content]
      [#list activity.objectives as objective]
        <div id="objective-${objective_index}" class="objective">
          [#-- Objective identifier --]
          <input type="hidden" name="activity.objectives[${objective_index}].id" value="${objective.id?c}" />
          
          [#-- remove link --]      
          <div class="removeLink">            
              <img src="${baseUrl}/images/global/icon-remove.png" />
              <a id="removeObjective-0" href="" class="removeObjective">
                [@s.text name="planning.objectives.removeObjective" /]
              </a>
          </div>      
          [@customForm.textArea name="activity.objectives[${objective_index}].description" i18nkey="planning.objectives.objective"  required=true/]
          <hr />
        </div>
      [/#list]
    [/#if]

    <div id="addDeliverableBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addObjective" >[@s.text name="planning.objectives.addObjective" /]</a>
    </div>
    </fieldset>
    
    
    [#-- Objective template --]
    <div id="objectiveTemplate" style="display:none">
      [#-- Objective identifier --]
      <input type="hidden" name="id" value="-1" />
      
      [#-- remove link --]      
      <div class="removeLink">            
        <img src="${baseUrl}/images/global/icon-remove.png" />
        <a id="removeObjective" href="" class="removeObjective">
          [@s.text name="planning.objectives.removeObjective" /]
        </a>
      </div>      
      [#-- Description --]
      [@customForm.textArea name="description" i18nkey="planning.objectives.objective" required=true /]
      <hr />
    </div>
      
    [#-- Only the owner of the activity can see the action buttons --]
    [#if activity.leader.id == currentUser.leader.id && canSubmit]
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