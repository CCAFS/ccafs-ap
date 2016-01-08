[#ftl]
[#assign title = "Activity Deliverables Planning" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/planning/deliverablesPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentCycleSection = "deliverables" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.activityDeliverables.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  
  [@s.form action="deliverables"]  
  <article class="halfContent">
    <h1 class="contentTitle">
      ${activity.leader.acronym}: [@s.text name="planning.mainInformation.activity" /] ${activity.activityId}
    </h1>
    
    [#-- Activity identifier --]
    <input name="activityID" value="${activity.id?c}" type="hidden"/>
    
    <fieldset id="deliverablesBlock">
      <legend> <h6> [@s.text name="planning.activityDeliverables" /] </h6> </legend>
      [#if activity.deliverables?has_content]
        [#list activity.deliverables as deliverable]
          <div id="deliverable-${deliverable_index}" class="deliverable">
            [#-- identifier --]
            <input name="activity.deliverables[${deliverable_index}].id" type="hidden" value="${deliverable.id?c}" />
            
            [#-- Adding remove link only for new deliverables --]
            <div class="removeLink">
              <img src="${baseUrl}/images/global/icon-remove.png" />
              <a id="removeDeliverable-${deliverable_index}" href="" class="removeDeliverable">
                [@s.text name="planning.activityDeliverables.removeDeliverable" /]
              </a>
            </div>
            
            [#-- Item index --]
            <div class="itemIndex">
              [@s.text name="planning.activityDeliverables.deliverable" /] ${deliverable_index +1}
            </div>
            
            [#-- Description --]
            <div class="fullBlock">
              [@customForm.textArea name="activity.deliverables[${deliverable_index}].description" i18nkey="planning.activityDeliverables.description" required=true /]
            </div>
            
            [#-- Type --]
            <div class="halfPartBlock">
              [@customForm.select name="activity.deliverables[${deliverable_index}].type" label="" i18nkey="planning.activityDeliverables.type" listName="deliverableTypesList" keyFieldName="id"  displayFieldName="name" className="deliverableType" /]
            </div>
            
            [#-- Year --]
            <div class="halfPartBlock">
              [@customForm.select name="activity.deliverables[${deliverable_index}].year" label="" i18nkey="planning.activityDeliverables.year" listName="yearList" value="${deliverable.year?c}" /]            
            </div>
            
          </div> <!-- End deliverable-${deliverable_index} -->
          <hr />
        [/#list]
      [/#if]
      
      <div id="addDeliverableBlock" class="addLink">
        <img src="${baseUrl}/images/global/icon-add.png" />
        <a href="" class="addDeliverable" >[@s.text name="planning.activityDeliverables.addDeliverable" /]</a>
      </div>
    
    </fieldset>
    
    [#-- Delvierable template --]
    <div id="delvierableTemplate" style="display:none">
      <div id="deliverable-9999" class="deliverable" style="display: none;">      
        [#-- remove link --]      
        <div class="removeLink">            
            <img src="${baseUrl}/images/global/icon-remove.png" />
            <a id="removeDeliverable-9999" href="" class="removeDeliverable">
              [@s.text name="planning.activityDeliverables.removeDeliverable" /]
            </a>
        </div>
        
        [#-- identifier --]
        <input name="id" type="hidden" value="-1" />
        
        [#-- Item index --]
        <div class="itemIndex">
          [@s.text name="planning.activityDeliverables.deliverable" /]
        </div>
        
        [#-- Description --]
        <div class="fullBlock">                      
          [@customForm.textArea name="description" i18nkey="planning.activityDeliverables.description" required=true /]
        </div>
        
        [#-- Type --]
        <div class="halfPartBlock">        
          [@customForm.select name="type" i18nkey="planning.activityDeliverables.type" listName="deliverableTypesList" keyFieldName="id"  displayFieldName="name" className="deliverableType" /]
        </div>
        
        [#-- Year --]
        <div class="halfPartBlock">
          [@customForm.select name="year" label="" i18nkey="planning.activityDeliverables.year" listName="yearList"/]            
        </div>
        
        <hr />
      </div> <!-- End deliverable template -->
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