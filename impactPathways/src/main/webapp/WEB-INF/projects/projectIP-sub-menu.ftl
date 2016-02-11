[#ftl]
<nav id="stageMenu" class="clearfix"> 
  <div id="ipGraph-button" title="[@s.text name="menu.preplanning.submenu.ipGraph" /]"></div>
  [#--
  <ul> 
    <li [#if currentSubStage == "outcomes"] class="currentSection" [/#if]>
      <a href="[@s.url action='outcomes' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.projectOutcomes" /]</a>
    </li>
    <li [#if currentSubStage == "ccafsOutcomes" ] class="currentSection" [/#if]>
      <a href="[@s.url action='ccafsOutcomes' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.ccafsOutcomes" /]</a>
    </li> 
    <li [#if currentSubStage == "otherContributions" ] class="currentSection" [/#if]>
      <a href="[@s.url action='otherContributions' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]">[@s.text name="menu.planning.submenu.otherContributions" /]</a>
    </li> 
  </ul>
  --]
</nav> 

<div id="content-ip" style="display:none;height: 270px;width: 100%;margin: 21px 0px;position:relative;" class="ui-widget-content">
  <div id="loading-ipGraph-content" style="display:none;position:absolute; width:100%; height:100%;top: 45%;">
      <img style="display: block; margin: 0 auto;" src="${baseUrl}/images/global/loading.gif" alt="Loader" />
  </div>
  <div id="ipGraph-content"></div> 
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
  