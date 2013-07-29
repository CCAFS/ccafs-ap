 [#ftl]
      <footer>
        <div id="footerContainer">          
          <div id="centres-logos">
            <div id="rowOne">
              <img src="${baseUrl}/images/global/centers/logo_africarice.jpg" alt="Africa Rice Logo" height="60px" width="83" />
              <img src="${baseUrl}/images/global/centers/logo_bioversity.jpg" alt="Bioversity Logo" height="60px" width="68" />
              <img src="${baseUrl}/images/global/centers/logo_ciat.jpg" alt="CIAT Logo" height="60px" width="139" />
              <img src="${baseUrl}/images/global/centers/logo_cifor.jpg" alt="CIFOR Logo" height="60px" width="63" />
              <img src="${baseUrl}/images/global/centers/logo_cimmyt.jpg" alt="CIMMYT Logo" height="60px" width="80" />
              <img src="${baseUrl}/images/global/centers/logo_cip.jpg" alt="CIP Logo" height="60px" width="69" />
              <img src="${baseUrl}/images/global/centers/logo_icarda.gif" alt="ICARDA Logo" height="60px" width="64" />
              <img src="${baseUrl}/images/global/centers/logo_icraf.jpg" alt="ICRAF Logo" height="60px" width="110" />
              <img src="${baseUrl}/images/global/centers/logo_icrisat.jpg" alt="ICRISAT Logo" height="60px" width="43" />
            </div>
            <div id="rowTwo">
              <img src="${baseUrl}/images/global/centers/logo_ifpri.jpg" alt="IFPRI Logo" height="60px" width="38" />
              <img src="${baseUrl}/images/global/centers/logo_iita.gif" alt="IITA Logo" height="55px" width="116" style="vertical-align: super;"/>
              <img src="${baseUrl}/images/global/centers/logo_ilri.jpg" alt="ILRI Logo" height="60px" width="60" />
              <img src="${baseUrl}/images/global/centers/logo_irri.jpg" alt="IRRI Logo" height="55px" style="vertical-align: super;" width="135" />
              <img src="${baseUrl}/images/global/centers/logo_iwmi.png" alt="IWMI Logo" height="60px" width="80" />
              <img src="${baseUrl}/images/global/centers/logo_worldfish.jpg" alt="WORLDFISH Logo" height="60px" width="103" />
            </div>            
          </div> <!-- end centres-logos -->
          <div id="copyright">
            <p>&#64; Copyright 2013</p>
            <p>Current version 1.0</p>
          </div>
          <div id="reportIssue">
            <p><b>[@s.text name="footer.report.issue.first" /]<a target="_blank" href="https://docs.google.com/forms/d/1EouZJYHqERbDRB2DaT6Q1cY-_-Tqe0daf4fxXrBU-ts/viewform">[@s.text name="footer.report.issue.second" /]</a></b></p>
          </div>
        </div>
      </footer>
    </div> <!-- end container -->
    [#include "/WEB-INF/global/pages/analytics.ftl"]
    
    [#-- First, import external libraries and CSS. --]
    <!-- Support for lower versions of IE 9 -->
    <!--[if lt IE 9]>
      <script src="${baseUrl}/js/libs/html5shiv/html5shiv.js"></script>
    <![endif]-->
    
    [#if globalLibs??]
     [#list globalLibs as libraryName]
        [#if libraryName="chosen"]
          <script src="${baseUrl}/js/libs/chosen/chosen-0.13.0.jquery.min.js"></script>
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/chosen/chosen-0.13.0.css" />
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/reporting/customChosen.css" />
        [/#if]
        
        [#if libraryName="dataTable"]
          <script src="${baseUrl}/js/libs/dataTables/jquery.dataTables-1.9.4.min.js"></script>
        [/#if]
        
        [#if libraryName="googleAPI"]
          <script type="text/javascript" src="http://www.google.com/jsapi"></script>
        [/#if]
        
        [#if libraryName="jquery"]
          [#-- JQuery Core --]
          <script src="${baseUrl}/js/libs/jquery/jquery-1.8.2.min.js"></script>
          [#-- JQuery UI --]
          <script src="${baseUrl}/js/libs/jqueryUI/jquery-ui-1.10.0.custom.min.js"></script>
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jqueryUI/smoothness/jquery-ui-1.10.0.custom.min.css" />
        [/#if]
        
        [#if libraryName="jqueryUI"]
          <script src="${baseUrl}/js/libs/jqueryUI/jquery-ui-1.10.0.custom.min.js"></script>
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jqueryUI/smoothness/jquery-ui-1.10.0.custom.min.css" />
        [/#if]
        
        [#if libraryName="jqueryAndUI"]          
          <script src="${baseUrl}/js/libs/jqueryUI/jquery-ui-1.10.0.custom.min.js"></script>
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jqueryUI/smoothness/jquery-ui-1.10.0.custom.min.css" />
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
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jreject/jquery.reject.css" />
        [/#if]
        
     [/#list]
    [/#if]
    
    [#-- Second, import global javascripts and templates. --]
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/global.css" />
    <!--[if lte IE 7]>
      <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/ie7.css"/> 
    <![endif]-->
    
    <script type="text/javascript" src="${baseUrl}/js/global/global.js" ></script>
    
    [#-- import the custom JS and CSS --]
    [#if customJS??]
      [#list customJS as js]
        <script src="${js}"></script>
      [/#list]
    [/#if]
    [#if customCSS??]
      [#list customCSS as css]
        <link rel="stylesheet" type="text/css" href="${css}" />
      [/#list]
    [/#if]
  </body>
</html>