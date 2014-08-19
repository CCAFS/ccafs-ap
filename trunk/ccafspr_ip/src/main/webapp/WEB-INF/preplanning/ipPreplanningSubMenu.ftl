[#ftl]
<nav id="stageMenu" class="clearfix">
  <ul> 
    <li [#if currentStage == "outcomes"] class="currentSection" [/#if]><a href="
        [@s.url action='outcomes' includeParams='get'][/@s.url]
      ">[@s.text name="menu.preplanning.submenu.outcomes" /]</a></li>
    
    [#if currentUser.RPL ]
    <li [#if currentStage == "midOutcomes"] class="currentSection" [/#if]><a href="
        [@s.url action='midOutcomesRPL' includeParams='get'][/@s.url]
      ">[@s.text name="menu.preplanning.submenu.midOutcomes" /]</a></li>
    
    <li [#if currentStage == "outputs"] class="currentSection" [/#if]><a href="
        [@s.url action='outputsRPL' includeParams='get'][/@s.url]
      ">[@s.text name="menu.preplanning.submenu.outputs" /]</a></li>
    
    [#elseif currentUser.FPL ] 
    <li [#if currentStage == "midOutcomes"] class="currentSection" [/#if]><a href="
        [@s.url action='midOutcomes' includeParams='get'][/@s.url]
      ">[@s.text name="menu.preplanning.submenu.midOutcomes" /]</a></li>
    
    <li [#if currentStage == "outputs"] class="currentSection" [/#if]><a href="
        [@s.url action='outputs' includeParams='get'][/@s.url]
      ">[@s.text name="menu.preplanning.submenu.outputs" /]</a></li> 
    [/#if]
  </ul> 
  <div id="ipGraph-button" title="[@s.text name="menu.preplanning.submenu.ipGraph" /]"></div>
</nav> 
<div id="content-ip" style="display:none;height: 100%;width: 100%;">
  <div id="ipGraph-content" class="ui-widget-content" ></div>
</div>
  