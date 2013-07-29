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
    
    
    
  </head>