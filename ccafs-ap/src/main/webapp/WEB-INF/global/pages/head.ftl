[#ftl]

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    [#-- Keywords --]
    [#if pageKeywords??]
      <meta name="keywords" content="${pageKeywords}" />
    [/#if]
    [#-- Description --]
    [#if pageDescription??]
      <meta name="description" content="${pageDescription}" />
    [/#if]
    
    [#-- Google SEO - Refer to http://support.google.com/webmasters/bin/answer.py?hl=en&answer=79812 --]
    [#if robotAccess??]
      <meta name="robots" content="${robotAccess}" />
      <meta name="googlebot" content="${robotAccess}" />
    [/#if]
    
    [#-- TODO - Still need to integrate google verification for google webmaster tools --]
    
    <title>${title!"CCAFS Activity Planning"}</title>
    
    [#-- First, import external libraries and CSS. --]
    <!-- Support for lower versions of IE 9 -->
    <!--[if lt IE 9]>
      <script src="${baseUrl}/js/libs/html5shiv/html5shiv.js"></script>
    <![endif]-->
    
    [#if globalLibs??]
     [#list globalLibs as libraryName]
        [#if libraryName="jquery"]
          <script src="${baseUrl}/js/libs/jquery/jquery-1.8.2.min.js"></script>          
        [/#if]
        
        [#if libraryName="dataTable"]
          <script src="${baseUrl}/js/libs/dataTables/jquery.dataTables-1.9.4.min.js"></script>
        [/#if]
        
        [#if libraryName="jqueryUI"]
          <script src="${baseUrl}/js/libs/jqueryUI/jquery-ui-1.9.2.custom.js"></script>
        [/#if]
  	 [/#list]
  	[/#if]
    [#-- Second, import global template. --]
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/global.css" />
     <!--[if lte IE 7]> 
     <style type="text/css" href="${baseUrl}/css/global/ie7.css"/> 
     <![endif]-->
    
    [#-- Last, import the custom JS and CSS --]
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
  </head>