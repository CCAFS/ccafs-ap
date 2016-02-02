[#ftl]
[#macro activityMacro activity activity_name="" activity_index="0" template=false editable=true canEdit=true]
  [#assign activitiesName]${activity_name}[${activity_index}][/#assign]
  [#assign activityId]${template?string('template',(activity.id)!)}[/#assign]
  <div id="activity-${(activityId)}" class="activity borderBox" style="display:${template?string('none','block')}"> 
    [#if (!editable && canEdit)]
      <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#activity-${(activity.id)!}">[@s.text name="form.buttons.edit" /]</a></div>
    [#else]
      [#if canEdit && !newProject]
        <div class="viewButton removeOption"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#activity-${(activity.id)!}">[@s.text name="form.buttons.unedit" /]</a></div>
      [/#if]
    [/#if]
    [#if template]
      <div class="leftHead">
        <span class="index"></span>
      </div>
    [#else]
      <div class="leftHead">
        <span class="index">${activity_index+1}</span>
        <span class="elementId">A${(activity.id)!}</span>
      </div>
    [/#if]
    [#if template]
      <div class="removeElement" title="[@s.text name="planning.activities.removeActivity" /]"></div>
    [#else]
      [#if editable && canEdit && action.canDelete((activity.id)!-1)]
        <div class="removeElement" title="[@s.text name="planning.activities.removeActivity" /]"></div>
      [/#if]
    [/#if]
    <input class="id" type="hidden" name="${activitiesName}.id" value="[#if activity.id??]${activity.id}[#else]-1[/#if]"> 
    [#-- Title --]
    <div class="fullPartBlock clearfix">
      [@customForm.input name="${activitiesName}.title" className="title" type="text" required=true i18nkey="planning.activityDescription.title" editable=editable && !reportingCycle/]
    </div>
    [#-- Description --]
    <div class="fullPartBlock clearfix">
      [@customForm.textArea name="${activitiesName}.description" className="description" required=true i18nkey="planning.activityDescription.description" editable=editable && !reportingCycle/]
    </div>  
    [#-- Start and End Date --]
    <div class="fullPartBlock clearfix"> 
      <div class="halfPartBlock">
        [@customForm.input name="${activitiesName}.startDate" className="startDate" type="text" i18nkey="planning.activityDescription.startDate" required=true editable=editable && !reportingCycle/]
      </div>   
      <div class="halfPartBlock">
        [@customForm.input name="${activitiesName}.endDate" className="endDate"  type="text" i18nkey="planning.activityDescription.endDate" required=true editable=editable && !reportingCycle/]
      </div>
    </div>
    [#-- -- -- REPORTING BLOCK -- -- --]
    [#if reportingCycle]
      [#-- Activity status --]
      <div class="fullPartBlock clearfix"> 
        <div class="halfPartBlock">
          [@customForm.select name="${activitiesName}.activityStatus" className="activityStatus" label="" required=true i18nkey="reporting.activityDescription.activityStatus" listName="projectStauses" editable=editable/]
        </div>   
      </div>
      [#-- Overall Activity progress --]
      <div class="fullPartBlock clearfix">
        [@customForm.textArea name="${activitiesName}.activityProgress" className="activityProgress" required=true i18nkey="reporting.activityDescription.activityProgress" editable=editable /]
      </div>
    [/#if]
    [#-- Project Leader --]
    <div class="fullPartBlock">
      [@customForm.select name="${activitiesName}.leader" className="leader" label="" required=true i18nkey="planning.activityDescription.leaderName" listName="projectPartnerPersons" editable=editable && !reportingCycle/]
    </div>
  </div><!-- End ${activityId} -->
[/#macro]
