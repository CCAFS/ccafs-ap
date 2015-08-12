[#ftl]
<div id="top-quote">
  [#if project.type?string?matches("CCAFS_CORE")]
    <div id="projectType-quote" class="aux-quote-id" title="CCAFS Core project">
      <p><b>CCAFS Core</b></p>
    </div>
  [/#if]
  [#if project.type?string?matches("BILATERAL")]
    <div id="projectType-quote" class="aux-quote-id" title="Bilateral project">
      <p><b>Bilateral</b></p>
    </div>
  [/#if]
  [#if project.type?string?matches("CCAFS_COFUNDED")]
    <div id="projectType-quote" class="aux-quote-id" title="CCAFS Co-Funded project">
      <p><b>CCAFS Co-Funded</b></p>
    </div>
  [/#if]
      <div id="organizationID-quote" class="org-quote-id" title="CCAFS organization identifier">
      <p>${organizationIdentifier}</p>
    </div>
  [#if project?has_content]
    <div id="projectID-quote" class="quote-id" title="[#if project.title?has_content]${project.title}[/#if]">
      <a href="[@s.url namespace="/planning/projects" action='description'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">
        <p><span>${project.id}</span></p>
      </a>
    </div>
  [/#if]
  [#if deliverable?has_content]
    <div id="deliverableID-quote" class="quote-id" title="[#if deliverable.title?has_content]${deliverable.title}[/#if]">
      <p><span>${deliverable.id}</span></p>
    </div>
  [/#if]
  [#if activity?has_content]
    <div id="activityID-quote" class="quote-id" title="[#if activity.title?has_content][@s.text name="planning.activity" /]: ${activity.title}[/#if]">
     <img class="icon" src="${baseUrl}/images/global/activity-icon.png"> ID: <span>${activity.composedId}</span>
    </div>
  [/#if]
</div>
