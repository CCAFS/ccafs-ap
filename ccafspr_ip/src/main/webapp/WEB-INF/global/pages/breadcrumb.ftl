[#ftl]
<ul id="breadcrumb">
 [#if breadCrumb?has_content] 
   [#list breadCrumb as item]
    <li>
       [#if item.action?has_content] 
      <a href="${baseUrl}/${item.nameSpace}/${item.action}.do">  
      [#else]
      <a href="#"> 
      [/#if]
      [@s.text name="breadCrumb.menu.${item.label}" /]
      </a>
    </li> 
   [/#list]
 [/#if]
</ul>
