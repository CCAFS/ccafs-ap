[#ftl]
<div id="deliverable-nextUsers" class="clearfix">
  <div class="fullBlock">
    <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.nextUsers" /] </h1> 
    [#if deliverable.nextUsers?has_content]
      [#list deliverable.nextUsers as nu] 
        [@deliverableTemplate.nextUserTemplate nu_name=params.nextUsers.name nu_index="${nu_index}" nextUserValue="${nu.id}" editable=editable canEdit=canEdit /]
      [/#list]
    [#else]
      [@deliverableTemplate.nextUserTemplate nu_name=params.nextUsers.name nu_index="0" nextUserValue="-1" editable=editable canEdit=canEdit /] 
    [/#if]
    [#if editable ]
      <div id="addNextUserBlock" class="addLink"><a href=""  class="addNextUser addButton">[@s.text name="planning.deliverables.addNewUser" /]</a></div>
    [/#if] 
  </div>
</div>