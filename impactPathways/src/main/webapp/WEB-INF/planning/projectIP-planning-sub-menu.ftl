[#ftl]
<nav id="stageMenu" class="clearfix"> 
  <ul> 
    <li [#if currentStage == "projectOutcomes"] class="currentSection" [/#if]><a href="
        [@s.url action='projectOutcomes' includeParams='get'][/@s.url]
      ">[@s.text name="menu.planning.submenu.impactPathwayPrimary" /]</a></li>
    <li [#if currentStage == "projectIpOtherContributions" ] class="currentSection" [/#if]><a href="
        [@s.url action='projectIpOtherContributions' includeParams='get'][/@s.url]
      ">[@s.text name="menu.planning.submenu.impactPathwayOther" /]</a></li>  
  </ul>
  <div id="ipGraph-button" title="[@s.text name="menu.preplanning.submenu.ipGraph" /]"></div>
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
  