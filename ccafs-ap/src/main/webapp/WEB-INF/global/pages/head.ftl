[#ftl]
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    [#-- Favicon --]
    <link rel="shortcut icon" href="${baseUrl}/images/global/favicon.ico" />
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
    
      [#-- This file must be called before close the body tag in order to allow first the page load --]
  [#-- import js files of external libraries--]
  <!-- Support for lower versions of IE 9 -->
  <!--[if lt IE 9]>
    <script src="${baseUrl}/js/libs/html5shiv/html5shiv.js"></script>
  <![endif]-->
    
    
    [#-- First, import CSS files of external libraries. --]
    [#-- All js imports are made in footer --]
    
    
    [#if globalLibs??]
     [#list globalLibs as libraryName]
        [#if libraryName="chosen"]
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/chosen/chosen-0.13.0.css" />
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/reporting/customChosen.css" />
        [/#if]
        
        [#if libraryName="jquery"]
          [#-- JQuery UI --]
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jqueryUI/smoothness/jquery-ui-1.10.0.custom.min.css" />
        [/#if]
        
        [#if libraryName="jqueryUI"]
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jqueryUI/smoothness/jquery-ui-1.10.0.custom.min.css" />
        [/#if]
        
        [#if libraryName="jqueryAndUI"]          
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jqueryUI/smoothness/jquery-ui-1.10.0.custom.min.css" />
        [/#if]
        
        [#if libraryName="jreject"]          
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jreject/jquery.reject.css" />
        [/#if]
        
  	 [/#list]
  	[/#if]
  	
    [#-- Second, import global javascripts and templates. --]
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/reset.css" />
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/global.css" />
    <!--[if lte IE 7]>
      <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/ie7.css"/> 
    <![endif]-->
    
    [#-- import the custom CSS --]
    [#if customCSS??]
      [#list customCSS as css]
        <link rel="stylesheet" type="text/css" href="${css}" />
      [/#list]
    [/#if]
  </head>