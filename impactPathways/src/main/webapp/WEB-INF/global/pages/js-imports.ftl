 [#ftl]

  [#if globalLibs??]
   [#list globalLibs as libraryName]
      [#if libraryName="chosen"]
        <script src="${baseUrl}/js/libs/chosen/chosen-0.13.0.jquery.min.js"></script>
      [/#if]
      
      [#if libraryName="dataTable"]
        <script src="${baseUrl}/js/libs/dataTables/jquery.dataTables-1.9.4.min.js"></script>
        <script src="${baseUrl}/js/libs/dataTables/js-naturalSort.dataTables-1.9.0.js"></script>
      [/#if]
      
      [#if libraryName="googleAPI"]
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
      [/#if]
      
      [#if libraryName="googleMaps"]
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBTC15VqenY93jEFMYO8F-mcWc-cXUZ_Mw&sensor=false"></script>
      [/#if]
      
      [#if libraryName="jquery"]
        [#-- JQuery Core --]
        <script src="${baseUrl}/js/libs/jquery/jquery-1.8.2.min.js"></script>
        [#-- JQuery UI --]
        <script src="${baseUrl}/js/libs/jqueryUI/jquery-ui-1.10.0.custom.min.js"></script>
      [/#if]
      
      [#if libraryName="jqueryUI"]
        <script src="${baseUrl}/js/libs/jqueryUI/jquery-ui-1.10.0.custom.min.js"></script>
      [/#if]
      
      [#if libraryName="jqueryAndUI"]
        <script src="${baseUrl}/js/libs/jqueryUI/jquery-ui-1.10.0.custom.min.js"></script>
      [/#if]
      
      [#if libraryName="highcharts"]
        <script src="${baseUrl}/js/libs/highcharts-4.1.8/highcharts.js"></script>
      [/#if]
      
      [#if libraryName="highmaps"]
        <script src="${baseUrl}/js/libs/highmaps-1.1.8/highmaps.js"></script>
      [/#if]
      
      [#if libraryName="noty"]
        [#-- Additional information visit: http://needim.github.com/noty/ --]
        [#-- Style can be found on /noty/layouts/inline.js and /noty/themes/default.js --] 
        <script type="text/javascript" src="${baseUrl}/js/libs/noty/packaged/jquery.noty.packaged.min.js"></script>
        <script type="text/javascript" src="${baseUrl}/js/libs/noty/themes/relax.js"></script>
      [/#if]

      [#if libraryName="jreject"]
        <script src="${baseUrl}/js/libs/jreject/jquery.reject-1.0.2.js"></script>
      [/#if]
      
      [#if libraryName="tinyEditor"]
        <script src="${baseUrl}/js/libs/tinymce/tinymce-4.0.22.min.js"></script>
        <script src="${baseUrl}/js/libs/tinymce/tinymce-configuration.js"></script>
      [/#if]
      
      [#if libraryName="autoSave"]
        <script src="${baseUrl}/js/global/autoSave.js"></script>
      [/#if]
      
      [#if libraryName="cytoscape"] 
        <script src="${baseUrl}/js/libs/cytoscape-2.2.12/cytoscape.min.js"></script>
      [/#if]
      
      [#if libraryName="qtip"]
      <script src="${baseUrl}/js/libs/cytoscape-2.2.12/cytoscape.js-qtip.js"></script>
        <script src="${baseUrl}/js/libs/qtip/jquery.qtip.js"></script>
      [/#if]
      [#if libraryName="cytoscapePanzoom"] 
        <script src="${baseUrl}/js/libs/cytoscapePanzoom/cytoscape.js-panzoom.js"></script>
      [/#if]
      [#if libraryName="slidr"] 
        <script src="${baseUrl}/js/libs/slidr/slidr.min.js"></script>
      [/#if]
   [/#list]
  [/#if]

  [#-- Second, import global javascripts and templates. --]
  <input type="hidden" id="baseURL" value="${baseUrl}" />
  <input type="hidden" id="editable" value="${editable?string}" />
  <input type="hidden" id="production" value="${config.production?string}" />
  [#-- Library for textareas --]
  <script src="${baseUrl}/js/libs/autogrow-3.0/jquery.autogrowtextarea.min.js"></script>
  [#-- Global Javascript --]
  <script type="text/javascript" src="${baseUrl}/js/global/global.js" ></script>

  [#-- import the custom JS and CSS --]
  [#if customJS??]
    [#list customJS as js]
      <script src="${js}"></script>
    [/#list]
  [/#if]

  [#-- Last, import the google analytics code --]
  [#include "/WEB-INF/global/pages/analytics.ftl" /]