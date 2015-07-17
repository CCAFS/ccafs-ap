[#ftl]

<nav id="stageMenu">
  <ul>
    <li class="goBack"><a href="
        [@s.url action='deliverablesList' includeParams='get'][/@s.url]
      ">[@s.text name="menu.activitiesReporting.submenu.deliverable.activityeliverables" /]
    </a> </li>
    <li [#if currentStage == "metadata"] class="currentReportingSection" [/#if]><a href="
        [@s.url action='deliverables' includeParams='get'][/@s.url]
      ">[@s.text name="menu.activitiesReporting.submenu.deliverable.metadata" /]
    </a> </li>
    <li [#if currentStage == "dataSharing"] class="currentReportingSection" [/#if]><a href="
        [@s.url action='deliverablesData' includeParams='get'][/@s.url]
      ">[@s.text name="menu.activitiesReporting.submenu.deliverable.dataSharing" /]
    </a></li>
    
    [#assign enabled = (deliverable.data || deliverable.tool) && deliverable.complete]
    [#if enabled]
      <li [#if currentStage == "ranking"] class="currentReportingSection" [/#if]>
        <a href=" [@s.url action='deliverablesRank' includeParams='get'][/@s.url]">
          [@s.text name="menu.activitiesReporting.submenu.deliverable.ranking" /]
        </a>
      </li>
    [#else]
      <li class="menu-disabled">
        <a> 
          [@s.text name="menu.activitiesReporting.submenu.deliverable.ranking" /] 
          <br />
          [#if !(deliverable.data || deliverable.tool)]
            <span style="font-size: 10px;">Data and tools only</span>
          [#else]
            <span style="font-size: 10px;">Complete deliverables only</span>
          [/#if]
        </a>
      </li>
    [/#if]
  </ul>
  <div class="clearfix"></div>
</nav>
