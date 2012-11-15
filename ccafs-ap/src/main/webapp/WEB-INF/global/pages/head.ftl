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
    [#list jsIncludes as libraryName]
      [#if libraryName="jquery"]
        <script src="${baseUrl}/js/libs/jquery/jquery-1.8.2.min.js"></script>
      [/#if]
  	[/#list]
    [#-- Second, import global template. --]
    <link rel="stylesheet" type="text/css" href="${baseUrl}/css/global/global.css" />
    
    [#-- Last, import the custom CSS --]
    
    
  </head>