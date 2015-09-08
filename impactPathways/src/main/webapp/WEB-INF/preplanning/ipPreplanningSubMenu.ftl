[#ftl]
<nav id="stageMenu" class="clearfix">
  <ul> 
    <li [#if currentStage == "outcomes"] class="currentSection" [/#if]>
      <a href="[@s.url action='outcomes' includeParams='get'][/@s.url]">
        [#if program.type.id == flagshipProgramTypeID]
          [@s.text name="menu.preplanning.submenu.outcomes" /]
        [#elseif program.type.id == regionProgramTypeID]
          [@s.text name="menu.preplanning.submenu.outcomesRPL" /]
        [/#if]
      </a>
    </li>
    
    [#if program.type.id == regionProgramTypeID ]
      <li [#if currentStage == "midOutcomes"] class="currentSection" [/#if]>
        <a href="[@s.url action='midOutcomesRPL' includeParams='get'][/@s.url]">
          [@s.text name="menu.preplanning.submenu.midOutcomes" /]
        </a>
      </li>
      
    [#elseif program.type.id == flagshipProgramTypeID ] 
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

<div id="content-ip" style="display:none;height: 270px;width: 100%;margin: 21px 0px;position:relative;" class="ui-widget-content">
	<div id="loading-ipGraph-content" style="display:none;position:absolute; width:100%; height:100%;top: 45%;">
        <img style="display: block; margin: 0 auto;" src="${baseUrl}/images/global/loading.gif" alt="Loader" />
    </div>
    <div id="ipGraph-content"></div>
      [#if program.type.id == flagshipProgramTypeID]
    	<button id="ipGraph-btnFullimpact" class="ipGraph-btn">[@s.text name="preplanning.ipGraph.all" /]</button>
    	<button style='display:none' id="ipGraph-btnSingleimpact" class="ipGraph-btn">[@s.text name="preplanning.ipGraph.myFP" /]</button>
	  [/#if]
	<div id="ipGraph-buttonsGroup">
	  <button id="ipGraph-btnPrint" class="ipGraph-btn" title="Print" disabled></button>
	  <button id="ipGraph-btnMax" class="ipGraph-btn" title="Fullscreen" disabled></button>
	  <a id="download" style="display:none">Download as image</a>
	  [#--<button id="ipGraph-btnMin" style="display:none">Min</button>--]
	</div>
</div>

<div id="dialog-message" title="Full View">    
    <div id="loading-dialog-message" style="display:none;position:absolute; width:100%; height:100%;top: 45%;">
        <img style="display: block; margin: 0 auto;" src="${baseUrl}/images/global/loading.gif" alt="Loader" />
    </div>
</div>