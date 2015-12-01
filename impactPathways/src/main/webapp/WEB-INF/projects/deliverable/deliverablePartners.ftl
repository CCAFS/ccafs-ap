[#ftl]
<div id="deliverable-partnership" class="clearfix">
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