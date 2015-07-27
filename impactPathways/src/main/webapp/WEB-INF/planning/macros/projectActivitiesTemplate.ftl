[#ftl]
[#macro activityMacro activity activity_name="" activity_index="0" template=false editable=true canEdit=true]
  [#assign activitiesName]${activity_name}[${activity_index}][/#assign]
  [#assign activityId]${template?string('template',activity_index)}[/#assign]
  <div id="activity-${activityId}" class="activity borderBox" style="display:${template?string('none','block')}"> 
    [#if (!editable && canEdit)]
      <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]#activity-${activity_index}">[@s.text name="form.buttons.edit" /]</a></div>
    [/#if]
    [#if (editable && canEdit) && (activity?? || action.canBeDeleted(activity.id))]
      <div class="removeElement" title="[@s.text name="planning.activities.removeActivity" /]"></div> 
    [/#if]
    <span class="index">${activity_index+1}</span> 
      <input class="id" type="hidden" name="${activitiesName}.id" value="[#if activity.id??]${activity.id}[#else]-1[/#if]"> 
    [#-- Title --]
    <div class="fullPartBlock clearfix">
      [@customForm.input name="${activitiesName}.title" className="title" type="text" required=true i18nkey="planning.activityDescription.title" editable=editable/]
    </div>
    [#-- Description --]
    <div class="fullPartBlock clearfix">
      [@customForm.textArea name="${activitiesName}.description" className="description" required=true i18nkey="planning.activityDescription.description" editable=editable/]
    </div>  
    [#-- Start and End Date --]
    <div class="fullPartBlock clearfix"> 
      <div class="halfPartBlock">
        [@customForm.input name="${activitiesName}.startDate" className="startDate" type="text" i18nkey="planning.activityDescription.startDate" required=true editable=editable/]
      </div>   
      <div class="halfPartBlock">
        [@customForm.input name="${activitiesName}.endDate" className="endDate"  type="text" i18nkey="planning.activityDescription.endDate" required=true editable=editable/]
      </div>
    </div>
    [#-- Project Partner --]
    <div class="fullPartBlock">
      
      [#if activity.projectPartners??]
        [@customForm.select name="${activitiesName}.leader" label="" i18nkey="planning.activityDescription.leaderName" listName="projectPartners" keyFieldName="id" displayFieldName="composedName" editable=editable/]
      [#else]
        [@customForm.select name="${activitiesName}.leader" label="" i18nkey="planning.activityDescription.leaderName" listName="projectPartners" keyFieldName="id" displayFieldName="composedName" editable=editable/]
      [/#if]
    </div>  
  </div><!-- End ${activityId} -->
[/#macro]