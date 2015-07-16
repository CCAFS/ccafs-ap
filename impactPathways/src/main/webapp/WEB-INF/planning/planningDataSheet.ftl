[#ftl]
<div id="top-quote">
  [#if project?has_content]
    <div id="projectID-quote" class="quote-id" title="[#if project.title?has_content][@s.text name="planning.project" /]: ${project.title}[/#if]">
      <a href="[@s.url namespace="/planning/projects" action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> 
        <p>ID: <span>${project.composedId}</span></p>
        <p>${project.type}</p>
      </a>
    </div>
  [/#if]
  [#if deliverable?has_content]
    <div id="deliverableID-quote" class="quote-id" title="[#if project.title?has_content][@s.text name="planning.deliverable" /]: ${deliverable.title}[/#if]">
      <a href="[@s.url namespace="/planning/projects" action='deliverable' includeParams='get'][@s.param name='deliverableID']${deliverable.id?c}[/@s.param][/@s.url]"> 
        <p>ID: <span>${deliverable.id}</span></p> 
      </a>
    </div>
  [/#if]
  [#if activity?has_content]
    <div id="activityID-quote" class="quote-id" title="[#if activity.title?has_content][@s.text name="planning.activity" /]: ${activity.title}[/#if]">
     <img class="icon" src="${baseUrl}/images/global/activity-icon.png"> ID: <span>${activity.composedId}</span>
    </div>
  [/#if]
</div>
