[#ftl]
<nav id="stageMenu" class="clearfix">
  <ul> 
    <li [#if currentStage == "outcomes"] class="currentSection" [/#if]>
      <a href="[@s.url action='outcomes' includeParams='get'][/@s.url]">
        [#if currentUser.FPL]
          [@s.text name="menu.preplanning.submenu.outcomes" /]
        [#elseif currentUser.RPL]
          [@s.text name="menu.preplanning.submenu.outcomesRPL" /]
        [/#if]
      </a>
    </li>
    
    [#if currentUser.RPL ]
      <li [#if currentStage == "midOutcomes"] class="currentSection" [/#if]>
        <a href="[@s.url action='midOutcomesRPL' includeParams='get'][/@s.url]">
          [@s.text name="menu.preplanning.submenu.midOutcomes" /]
        </a>
      </li>
      
      <li [#if currentStage == "outputs"] class="currentSection" [/#if]>
        <a href="[@s.url action='outputsRPL' includeParams='get'][/@s.url]">
          [@s.text name="menu.preplanning.submenu.outputs" /]
        </a>
      </li>
    
    [#elseif currentUser.FPL ] 
      <li [#if currentStage == "midOutcomes"] class="currentSection" [/#if]>
        <a href="[@s.url action='midOutcomes' includeParams='get'][/@s.url]">
          [@s.text name="menu.preplanning.submenu.midOutcomes" /]
        </a>
      </li>
      
      <li [#if currentStage == "outputs"] class="currentSection" [/#if]>
        <a href="[@s.url action='outputs' includeParams='get'][/@s.url]">
          [@s.text name="menu.preplanning.submenu.outputs" /]
        </a>
      </li> 
    [/#if]
  </ul> 
  <div id="ipGraph-button" title="[@s.text name="menu.preplanning.submenu.ipGraph" /]"></div>
</nav> 
<div id="gran-ip">
	<div id="content-ip" style="display:none;height: 270px;width: 100%;margin: 21px 0px;position:relative;" class="ui-widget-content">
	  <div id="ipGraph-content"></div>
		<div id="ipGraph-buttonsGroup">
		  <button id="ipGraph-btnPrint" title="Print"></button>
		  [#--<button id="ipGraph-btnMax">Max</button>--]
		  [#--<button id="ipGraph-btnMin" style="display:none">Min</button>--]
		</div>
	</div>
</div>
  