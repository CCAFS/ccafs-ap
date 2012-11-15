[#ftl]
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>${title!"CCAFS Activity Planning"}</title>
    
    [#-- Import external libraries and CSS at first step. --]    
    [#-- Second, use our global template. --]
    [#-- Last, import the custom CSS --]    
    [#list jsIncludes as library]
      [#if library="jquery"]
        <script src="jquery.js"></script>
      [/#if]
  	[/#list]  
    
  </head>