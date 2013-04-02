[#ftl]
[#assign title = "Activity Deliverables Planning" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign customJS = ["${baseUrl}/js/planning/deliverablesPlanning.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "deliverables" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  
  [@s.form action="deliverables"]  
  <article class="halfContent">
    <h1 class="contentTitle">
      [@s.text name="planning.mainInformation.activity" /] ${activity.id} - [@s.text name="planning.activityDeliverables" /] 
    </h1>
    
    [#-- Activity identifier --]
    <input name="activityID" value="${activity.id}" type="hidden"/>
    
    <div id="deliverablesBlock">
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
              [@customForm.textArea name="activity.deliverables[${deliverable_index}].description" i18nkey="planning.activityDeliverables.description" /]
            </div>
            
            [#-- Type --]
            <div class="halfPartBlock">
              [@customForm.select name="activity.deliverables[${deliverable_index}].type" label="" i18nkey="planning.activityDeliverables.type" listName="deliverableTypesList" keyFieldName="id"  displayFieldName="name" className="deliverableType" /]
            </div>
            
            [#-- Year --]
            <div class="halfPartBlock">
              [@customForm.select name="activity.deliverables[${deliverable_index}].year" label="" i18nkey="planning.activityDeliverables.year" listName="yearList" value="${activity.deliverables[deliverable_index].year}" /]            
            </div>
            
            [#-- Formats --]
            [#if deliverableTypeIdsNeeded?seq_contains(activity.deliverables[deliverable_index].type.id)]              
              <div class="fullBlock">
            [#else]
              <div class="fullBlock" style="display: none;">
            [/#if]
                <h6>[@s.text name="planning.activityDeliverables.formatFiles" /]</h6>
                <div class="checkboxGroup">
                  [@s.fielderror cssClass="fieldError" fieldName="activity.deliverables[${deliverable_index}].fileFormats"/]
                  [@s.checkboxlist name="activity.deliverables[${deliverable_index}].fileFormats" list="fileFormatsList" listKey="id" listValue="name" value="activity.deliverables[${deliverable_index}].fileFormatsIds" cssClass="checkbox" /]
                </div>
              </div>
            
          </div> <!-- End deliverable-${deliverable_index} -->
          <hr />
        [/#list]
      [/#if]
    </div>
    
    <div id="addDeliverableBlock" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addDeliverable" >[@s.text name="planning.activityDeliverables.addDeliverable" /]</a>
    </div>
    
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
          [@customForm.textArea name="description" i18nkey="planning.activityDeliverables.description" /]
        </div>
        
        [#-- Type --]
        <div class="halfPartBlock">        
          [@customForm.select name="type" i18nkey="planning.activityDeliverables.type" listName="deliverableTypesList" keyFieldName="id"  displayFieldName="name" className="deliverableType" /]
        </div>
        
        [#-- Year --]
        <div class="halfPartBlock">
          [@customForm.select name="year" label="" i18nkey="planning.activityDeliverables.year" listName="yearList"/]            
        </div>
        
        [#-- Formats --]                    
        <div class="fullBlock">
          <h6>[@s.text name="planning.activityDeliverables.formatFiles" /]</h6>          
          <div class="checkboxGroup">                        
            [@s.checkboxlist name="fileFormats" list="fileFormatsList" listKey="id" listValue="name" cssClass="checkbox" /]
          </div>
        </div>
        
      </div> <!-- End deliverable template -->
    </div>
      
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]