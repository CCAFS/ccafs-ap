[#ftl]
[#assign title = "Project Deliverable" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen", "star-rating"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/projects/projectDeliverables.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outputs" /]
[#assign currentSubStage = "deliverables" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projectOutputs", "nameSpace":"planning/projects", "action":"outputs", "param":"projectID=${project.id}"},
  {"label":"projectDeliverables", "nameSpace":"planning/projects", "action":"deliverablesList", "param":"projectID=${project.id}"}
  {"label":"projectDeliverable", "nameSpace":"planning/projects", "action":"deliverable", "param":"deliverableID=${deliverable.id}"}
]/]

[#assign params = {
  "deliverable":  {"id":"deliverableName", "name":"deliverable"},
  "responsiblePartner":  {"id":"responsiblePartnerName", "name":"deliverable.responsiblePartner"},
  "nextUsers":    {"id":"nextUsersName",   "name":"deliverable.nextUsers"},
  "partners":     {"id":"partnersName",    "name":"deliverable.otherPartners"} 
}
/] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
[#import "/WEB-INF/projects/macros/projectDeliverablesTemplate.ftl" as deliverableTemplate/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.deliverables.help" /] 
    <a href = [@s.url namespace="/" action='glossary'][/@s.url]>[@s.text name="planning.deliverables.help2" /]</a>
    </p>
  </div>
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  
  [@s.form action="deliverable" cssClass="pure-form"]
  <article class="halfContent" id="projectDeliverable">  
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    <br />
    [#-- Informing user that he-she does not have enough privileges to edit. See GrantProjectPlanningAccessInterceptor --]  
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title /][/@s.param][/@s.text]
      </p>
    [/#if]
    [#--  Deliverable Information --] 
    <div id="deliverable-information" class="borderBox clearfix"> 
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.information" /] </h1>  
      <div class="fullBlock">
        [#-- Title --] 
        [@customForm.input name="${params.deliverable.name}.title" className="deliverableTitle" i18nkey="planning.deliverables.title" required=true editable=editable /]
      </div>
      <div class="fullBlock">
        [#-- MOG  --]
        <div class="${editable?string('halfPartBlock','fullBlock')} chosen"> 
          [@customForm.select name="${params.deliverable.name}.output" label=""  disabled=false i18nkey="planning.deliverables.mog" listName="outputs" keyFieldName="id"  displayFieldName="description" required=true editable=editable /]
        </div> 
        [#-- Year  --]
        <div class="halfPartBlock chosen">
          [@customForm.select name="${params.deliverable.name}.year" value="${deliverable.year}" label=""  disabled=false i18nkey="planning.deliverables.year" listName="allYears" required=true editable=editable /]
          [#if !editable]${deliverable.year}[/#if]
        </div>
      </div> 
      <div class="fullBlock">
        [#assign deliverableType][#if deliverable.type??]${deliverable.type.category.id}[#else]-1[/#if][/#assign]
        [#assign deliverableSubType][#if deliverable.type??]${deliverable.type.id}[#else]-1[/#if][/#assign]
        [#-- Main Type --]
        <div class="halfPartBlock chosen"> 
          [@customForm.select name="mainType" value="${deliverableType}" i18nkey="planning.deliverables.mainType" listName="deliverableTypes" keyFieldName="id"  displayFieldName="name" required=true editable=editable /]
          [#if !editable]${(deliverable.type.category.name)!}[/#if]
        </div> 
        [#-- Sub Type --]
        <div class="halfPartBlock chosen">
          [#assign fieldEmpty] <div class="select"><p>[@s.text name="form.values.fieldEmpty" /]</p></div>[/#assign]
          [@customForm.select name="${params.deliverable.name}.type" value="${deliverableSubType}" i18nkey="planning.deliverables.subType" listName="" keyFieldName=""  displayFieldName="" required=true editable=editable /]
          [#if !editable][#if deliverable.typeOther??]${(deliverable.typeOther)!fieldEmpty}[#else]${(deliverable.type.name)!fieldEmpty}[/#if][/#if]
          <input type="hidden" id="subTypeSelected" value="${deliverableSubType}" />
          [#-- Specify other deliverable type--] 
          [@customForm.input name="${params.deliverable.name}.typeOther" value="${(deliverable.typeOther)!}" className="otherType" display=false showTitle=false i18nkey="planning.deliverables.specify" required=true disabled=true editable=editable /]          
        </div> 
      </div>
      
      [#-- Deliverables table dialog --] 
      [@deliverableTemplate.deliverablesTypesTable types=deliverableTypes show=(canEdit && !action.canDelete() && !reportingCycle) /]
      
      [#-- Deliverable type description and message--]
      [#if canEdit && editable && !action.canDelete() && !reportingCycle]
      <div class="fullBlock">
        <div class="note left">
          [#if deliverable.type??]<p><b>Deliverable type description:</b> [@s.text name="${(deliverable.type.description)!}" /]</p>[/#if]
          <p>[@s.text name="planning.deliverables.disclaimerMessage" /]</p>
        </div>
      </div>
      [/#if]
      
      [#-- -- -- REPORTING BLOCK -- -- --]
      [#if reportingCycle]
        [#-- Deliverable Status  --]
        <div class="halfPartBlock"> 
          [@customForm.select name="${params.deliverable.name}.status" label=""  disabled=false i18nkey="reporting.projectDeliverable.status" listName="statuses" keyFieldName="id"  displayFieldName="description" required=true editable=editable /]
        </div> 
        [#-- Status justification  --]
        <div class="fullBlock">
          [@customForm.textArea name="${params.deliverable.name}.statusJustification" i18nkey="reporting.projectDeliverable.statusJustification" editable=editable/]
        </div>
      [/#if]
    </div>
    
    [#-- Deliverable Next Users block  --]
    <div id="deliverable-nextUsers" class="borderBox clearfix">
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-nextUsers">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]#deliverable-nextUsers">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      <div class="fullBlock">
        <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.nextUsers" /] </h1> 
        [#if deliverable.nextUsers?has_content]
          [#list deliverable.nextUsers as nu] 
            [@deliverableTemplate.nextUserTemplate nu_name=params.nextUsers.name nu_index="${nu_index}" nextUserValue="${nu.id}" editable=editable canEdit=canEdit /]
          [/#list]
        [#else]
          [@deliverableTemplate.nextUserTemplate nu_name=params.nextUsers.name nu_index="0" nextUserValue="-1" editable=editable canEdit=canEdit /] 
        [/#if]
        [#if editable && canEdit]
          <div id="addNextUserBlock" class="addLink"><a href=""  class="addNextUser addButton">[@s.text name="planning.deliverables.addNewUser" /]</a></div>
        [/#if] 
      </div>
    </div>  
    
    [#-- Deliverable partnership  --]
    <div id="deliverable-partnership" class="borderBox clearfix">
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-partnership">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]#deliverable-partnership">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.partnership" /] </h1> 
      <div class="fullBlock">
        [#-- Partner who is responsible --]
        <div class="fullBlock">
          <p>[@customForm.text name="planning.projectDeliverable.indicateResponsablePartner" readText=!editable/]</p>
          [@deliverableTemplate.deliverablePartner dp=deliverable.responsiblePartner dp_name=params.responsiblePartner.name dp_index=dp_index isResponsable=true editable=editable /]
        </div>
        [#-- Other contact person that will contribute --]
        [#assign displayOtherPerson = (!deliverable.otherPartners?has_content && !editable)?string('none','block') /]
        <p style="display:${displayOtherPerson}">[@customForm.text name="planning.projectDeliverable.indicateOtherContact" readText=!editable/]</p>
        <div class="simpleBox" style="display:${displayOtherPerson}">
          [#if deliverable.otherPartners?has_content]
            [#list deliverable.otherPartners as dp]
              [@deliverableTemplate.deliverablePartner dp=dp dp_name=params.partners.name dp_index=dp_index editable=editable /]
            [/#list]
          [#else]
            <p class="emptyText center"> [@s.text name="planning.projectDeliverable.partnership.emptyText" /]</p>
          [/#if]
          [#if editable && canEdit]
            <div id="addPartnerBlock" class="addLink"><a href=""  class="addPartner addButton">[@s.text name="planning.deliverables.addPartner" /]</a></div>
          [/#if]
        </div>
      </div>
      [#if editable]
        <div class="partnerListMsj note">
          [@s.text name="preplanning.projectBudget.partnerNotList" /]
          <a href="[@s.url action='partners'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> 
            [@s.text name="preplanning.projectBudget.partnersLink" /] 
          </a>
        </div>
      [/#if]
    </div>
    
    [#-- -- -- REPORTING BLOCK -- -- --]
    [#if reportingCycle]
      [#-- Deliverable ranking --]
      <div id="deliverable-ranking" class="borderBox clearfix">
        [#if !editable && canEdit]
          <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-ranking">[@s.text name="form.buttons.edit" /]</a></div>
        [#else]
          [#if canEdit && !newProject]
            <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]#deliverable-ranking">[@s.text name="form.buttons.unedit" /]</a></div>
          [/#if]
        [/#if]
        <h1 class="contentTitle">[@s.text name="reporting.projectDeliverable.rankingTitle" /] </h1>
        [#-- Ranking --]
        <div class="fullBlock">
        <h6>Ranking</h6>
          <table id="rankingTable" class="default">
            <tbody>
              <tr>
                <td class="key">[@s.text name="reporting.projectDeliverable.ranking.addressGenderSocial" /]</td> 
                <td class="value">[@deliverableTemplate.rank name="${params.deliverable.name}.addressGenderSocial" disabled=!editable/]</td>
              </tr>
              <tr>
                <td class="key">[@s.text name="reporting.projectDeliverable.ranking.potencialActualContribution" /]</td>
                <td class="value">[@deliverableTemplate.rank name="${params.deliverable.name}.potencialActualContribution" disabled=!editable/]</td>
              </tr>
              <tr>
                <td class="key">[@s.text name="reporting.projectDeliverable.ranking.levelSharedOwnership" /] [@s.text name="reporting.projectDeliverable.ranking.levelSharedOwnership.complement" /]</td> 
                <td class="value">[@deliverableTemplate.rank name="${params.deliverable.name}.levelSharedOwnership" disabled=!editable/]</td>
              </tr>
              <tr>
                <td class="key">[@s.text name="reporting.projectDeliverable.ranking.productImportance" /]</td> 
                <td class="value">[@deliverableTemplate.rank name="${params.deliverable.name}.productImportance" disabled=!editable/]</td>
              </tr>
            </tbody>
          </table>
        </div>
        [#-- Compliance check (Data products only) --]
        <div class="fullBlock">
        <h6>[@s.text name="reporting.projectDeliverable.complianceTitle" /]</h6>
          <table id="dataCompliance" class="default">
            <tbody>
              <tr>
                <td class="key">[@s.text name="reporting.projectDeliverable.compliance.dataQualityAssurance" /]
                  <div id="aditional-dataQualityAssurance" class="aditional fileUpload" style="display:none">
                    [@customForm.input name="${params.deliverable.name}.dataQualityAssuranceUpload" type="file" className="upload" i18nkey="reporting.projectDeliverable.compliance.dataQualityAssurance.upload" editable=editable/]
                  </div>
                </td> 
                <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.dataQualityAssurance" disabled=!editable/]</td>
              </tr>
              <tr>
                <td class="key">[@s.text name="reporting.projectDeliverable.compliance.dataDictionary" /]</td> 
                <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.dataDictionary" disabled=!editable/]</td>
              </tr>
              <tr>
                <td class="key">[@s.text name="reporting.projectDeliverable.compliance.toolsUsedDataCollection" /]
                  <div id="aditional-toolsUsedDataCollection" class="aditional" style="display:none">
                    [@customForm.textArea name="${params.deliverable.name}.toolsUsedDataCollectionLinks" i18nkey="reporting.projectDeliverable.compliance.toolsUsedDataCollection.links" editable=editable/]
                  </div>
                </td> 
                <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.toolsUsedDataCollection" disabled=!editable/]</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      
      [#-- Deliverable dissemination --]
      <div id="deliverable-dissemination" class="borderBox clearfix">
        [#if !editable && canEdit]
          <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-dissemination">[@s.text name="form.buttons.edit" /]</a></div>
        [#else]
          [#if canEdit && !newProject]
            <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]#deliverable-dissemination">[@s.text name="form.buttons.unedit" /]</a></div>
          [/#if]
        [/#if]
        <h1 class="contentTitle">[@s.text name="reporting.projectDeliverable.disseminationTitle" /] </h1>
        [#-- Is this deliverable disseminated --]
        <div class="fullBlock">
          <table id="alreadyDisseminated" class="default">
            <tbody>
              <tr>
                <td class="key" title="[@s.text name="reporting.projectDeliverable.dissemination.alreadyDisseminated.help" /]">
                  <p>[@s.text name="reporting.projectDeliverable.dissemination.alreadyDisseminated" /]</p>
                  <div id="aditional-alreadyDisseminated"class="aditional" style="display:none">
                     
                    <p>[@s.text name="reporting.projectDeliverable.dissemination.alreadyDisseminated.description" /]</p>
                    <div class="fullBlock">
                      [@customForm.select name="${params.deliverable.name}.disseminationChannel" label=""  disabled=false i18nkey="reporting.projectDeliverable.disseminationChannel" listName="channels" keyFieldName="id"  displayFieldName="description" required=true editable=editable /]
                      [@customForm.input name="${params.deliverable.name}.disseminationUrl" className="deliverableTitle" i18nkey="reporting.projectDeliverable.disseminationUrl" required=true editable=editable /]
                    </div>
                  </div><!-- End aditional-alreadyDisseminated -->
                </td> 
                <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.alreadyDisseminated" disabled=!editable/]</td>
              </tr>
            </tbody>
          </table>
        </div>
        [#-- Is there an Open Access restriction --]
        <div class="fullBlock">
          <table id="openAccessRestriction" class="default">
            <tbody>
              <tr>
                <td class="key" title="[@s.text name="reporting.projectDeliverable.dissemination.openAccessRestriction.help" /]">
                  <p>[@s.text name="reporting.projectDeliverable.dissemination.openAccessRestriction" /]</p>
                  <div id="aditional-openAccessRestriction" class="aditional" style="display:none">
                    
                    <h6>[@s.text name="reporting.projectDeliverable.dissemination.openAccessRestriction.title" /]</h6>
                    <div class="fullBlock">
                      [#-- Intellectual Property Rights --]
                      <div class="openAccessRestrictionOption">
                        <input id="intellectualProperty" type="radio" name="${params.deliverable.name}.openAccessRestrictionOption" value="1" />
                        <label for="intellectualProperty">[@s.text name="reporting.projectDeliverable.dissemination.intellectualProperty" /]</label>
                      </div>
                      [#-- Limited Exclusivity Agreements --]
                      <div class="openAccessRestrictionOption">
                        <input id="limitedExclusivity" type="radio" name="${params.deliverable.name}.openAccessRestrictionOption" value="2" /> 
                        <label for="limitedExclusivity">[@s.text name="reporting.projectDeliverable.dissemination.limitedExclusivity" /]</label>
                      </div>
                      [#-- Restricted Use Agreement - Restricted access --]
                      <div class="openAccessRestrictionOption">
                        <input id="restrictedAccess" type="radio" name="${params.deliverable.name}.openAccessRestrictionOption" value="3" />
                        <label for="restrictedAccess">[@s.text name="reporting.projectDeliverable.dissemination.restrictedAccess" /]</label>
                      </div>
                      [#-- Effective Date Restriction - embargoed periods --]
                      <div class="openAccessRestrictionOption">
                        <input id="embargoedPeriods" type="radio" name="${params.deliverable.name}.openAccessRestrictionOption" value="4" />
                        <label for="embargoedPeriods">[@s.text name="reporting.projectDeliverable.dissemination.embargoedPeriod" /]</label>
                      </div>
                    </div>
                    [#-- Periods --]
                    <div class="fullBlock">
                      <div id="period-restrictedAccess" class="halfPartBlock" style="display:block">
                        [@customForm.input name="${params.deliverable.name}.restrictedAccessDate" className="period" type="text" i18nkey="reporting.projectDeliverable.dissemination.restrictedAccessDate" editable=editable/]
                      </div>
                      <div id="period-embargoedPeriods" class="halfPartBlock" style="display:block">
                        [@customForm.input name="${params.deliverable.name}.embargoedPeriodDate" className="period" type="text" i18nkey="reporting.projectDeliverable.dissemination.embargoedPeriodDate" editable=editable/]
                      </div>
                    </div>
                  </div><!-- End aditional-openAccessRestriction -->
                </td> 
                <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.openAccessRestriction" disabled=!editable/]</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    [/#if]
    
    [#if editable] 
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <input name="deliverableID"type="hidden" value="${deliverable.id}">
      <div class="[#if !newProject]borderBox[/#if]" >
        [#if !newProject] [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      </div>
    [#else]
        [#-- Display Log History --]
        [#if history??][@log.logList list=history /][/#if]   
    [/#if]
  </article>
  [/@s.form] 
   
</section> 

[#-- Internal parameters --]   
[#list params?keys as prop]
  <input id="${params[prop].id}" type="hidden" value="${params[prop].name}" /> 
[/#list]

[#-- Project deliverable Next user Template--]
[@deliverableTemplate.nextUserTemplate template=true /]

[#-- Deliverable Partner Template--]
[@deliverableTemplate.deliverablePartner dp={} dp_name=params.partners.name dp_index=dp_index template=true /]

[#include "/WEB-INF/global/pages/footer.ftl"]