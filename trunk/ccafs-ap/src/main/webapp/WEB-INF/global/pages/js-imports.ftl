 [#ftl]
  
  
  [#if globalLibs??]
   [#list globalLibs as libraryName]
      [#if libraryName="chosen"]
        <script src="${baseUrl}/js/libs/chosen/chosen-0.13.0.jquery.min.js"></script>
      [/#if]
      
      [#if libraryName="dataTable"]
        <script src="${baseUrl}/js/libs/dataTables/jquery.dataTables-1.9.4.min.js"></script>
      [/#if]
      
      [#if libraryName="googleAPI"]
        <script type="text/javascript" src="http://www.google.com/jsapi"></script>
      [/#if]
      
      [#if libraryName="googleMaps"]
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBTC15VqenY93jEFMYO8F-mcWc-cXUZ_Mw&sensor=false"></script>
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
      
      [#if libraryName="noty"]
        [#-- Additional information visit: http://needim.github.com/noty/ --]
        [#-- Style can be found on /noty/layouts/inline.js and /noty/themes/default.js --]
        <script type="text/javascript" src="${baseUrl}/js/libs/noty/jquery.noty.js"></script>
        <script type="text/javascript" src="${baseUrl}/js/libs/noty/layouts/inline.js"></script>
        <script type="text/javascript" src="${baseUrl}/js/libs/noty/themes/default.js"></script>
      [/#if]
      
      [#if libraryName="jreject"]          
        <script src="${baseUrl}/js/libs/jreject/jquery.reject-1.0.2.js"></script>
      [/#if]
      
   [/#list]
  [/#if]
  
  [#-- Second, import global javascripts and templates. --]
  <script type="text/javascript" src="${baseUrl}/js/global/global.js" ></script>
  
  [#-- import the custom JS and CSS --]
  [#if customJS??]
    [#list customJS as js]
      <script src="${js}"></script>
    [/#list]
  [/#if]
  

  
  [#-- Last, import the google analytics code --]
  [#include "/WEB-INF/global/pages/analytics.ftl" /]