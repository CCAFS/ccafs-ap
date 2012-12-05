[#ftl]
<nav id="stageMenu">
  <ul>
         
  
    <a [#if currentReportingSection == "activities"] class="currentReportingSection" [/#if] href="
        [@s.url action='status' includeParams='get'][/@s.url]
      "><li>Status</li>
    </a>
    <a [#if currentReportingSection == "outputs"] class="currentReportingSection" [/#if] href="
        [@s.url action='deliverables' includeParams='get'][/@s.url]
      "><li>Deliverables</li>
    </a>
    <a [#if currentReportingSection == "publications"] class="currentReportingSection" [/#if] href="
        [@s.url action='status' includeParams='get'][/@s.url]
      "><li>Partners</li>
    </a>            
  </ul>
</nav>