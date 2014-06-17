[#ftl]
[#--
 
 * This file is part of CCAFS Planning and Reporting Platform.
 *
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see 
 * <http://www.gnu.org/licenses/>
  
--]

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
    
    [#-- Google webmaster tools tag --]
    <meta name="google-site-verification" content="uDt49ijI7uKxK60GeIDi2N1DedHr3hsomqFzE7ngwqw" />
    
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
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jqueryUI/jquery-ui-1.10.css" />
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/jquery-ui.custom.css" />
        [/#if]
        
        [#if libraryName="jqueryUI"]
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jqueryUI/jquery-ui-1.10.css" />
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/jquery-ui.custom.css" />
        [/#if]
        
        [#if libraryName="jqueryAndUI"]          
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/libs/jqueryUI/jquery-ui-1.10.css" />
          <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/jquery-ui.custom.css" />
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