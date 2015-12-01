[#ftl]
<div id="deliverable-nextUsers" class="clearfix">
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