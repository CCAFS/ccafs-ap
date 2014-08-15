[#ftl]
<nav id="mainMenu">  
	<div class="container">
	  <ul>
	    [#if logged]
	      [#-- Home element --]
	      <a href="${baseUrl}/">
	        <li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>
	          <span class="icon">
	            [#if currentSection?? && currentSection == "home"]
	               <img class="icon-15" src="${baseUrl}/images/global/icon-home-menu-selected.png" />
	            [#else]
	               <img class="icon-15" src="${baseUrl}/images/global/icon-home-menu-selected.png" />
	            [/#if]
	          </span>
	          <span class="text">
	            [@s.text name="menu.home" /]
	          </span>
	        </li>
	      </a>
	      
	      [#-- PRE-Planning section --]
	      [#if currentUser.FPL || currentUser.RPL || currentUser.CU || currentUser.admin ]
	        [#if preplanningActive ]
	          [#if currentUser.CU ]
  	          <a  href="[@s.url namespace="/pre-planning/projects" action='projects'/]">
  	        [#else]
  	          <a  href="[@s.url namespace="/pre-planning/projects" action='outcomes'/]">
  	        [/#if]
	        [#else]
	          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
	        [/#if]
	          <li [#if currentSection?? && currentSection == "preplanning"] class="currentSection" [/#if]>[@s.text name="menu.preplanning" /]</li>
	        </a>
	      [/#if]
	      
	      [#-- Planning section --]
	      [#if !currentUser.guest ]
	        [#if planningActive ]               
	          <a  href="[@s.url namespace="/planning/projects" action='projects'/]">
	        [#else]
	          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
	        [/#if]
	          <li [#if currentSection?? && currentSection == "planning"] class="currentSection" [/#if]>[@s.text name="menu.planning" /]</li>
	        </a>
	      [/#if]
	      
	      [#-- Reporting section --]
	      [#-- 
	      [#if !currentUser.guest ] 
	        [#if reportingActive ]               
	          <a href="${baseUrl}/reporting/introduction.do" >
	        [#else]
	          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
	        [/#if]
	          <li [#if currentSection?? && currentSection == "reporting"] class="currentSection" [/#if]>[@s.text name="menu.reporting" /]</li>
	        </a>
	      [/#if]	      
	      --]
	      [#-- Summaries section --]
	      [#--
	      [#if currentUser.CU || currentUser.FPL || currentUser.RPL || currentUser.admin ]
	        [#if summariesActive ]
	          <a href="${baseUrl}/summaries/activities.do" /]" >
	              <li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>[@s.text name="menu.summaries" /]</li>
	          </a>
	        [#else]
	          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
	              <li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>[@s.text name="menu.summaries" /]</li>
	          </a>
	        [/#if]
	      [/#if]
	      --]
	      [#-- Admin section --]
	      [#--
	      [#if currentUser.admin ]
	        <a href="javascript:void(0);">
	          <li [#if currentSection?? && currentSection == "admin"] class="currentSection" [/#if]>[@s.text name="menu.admin" /]</li>
	        </a>
	      [/#if]
	      --]
	    [#else]
	      [#-- If the user is not logged show the login element in menu --]
	      <a href="${baseUrl}/"><li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>[@s.text name="menu.login" /]</li></a>
	    [/#if]
	  </ul>
	  
	  [#if logged]
        <div id="userInfo">
          <a id="userLogOut" href="[@s.url action="logout" namespace="/" /]">[@s.text name="header.logout" /]</a>
          <p class="email">${currentUser.firstName} ${currentUser.lastName}</p>  
          <p class="institution">${currentUser.currentInstitution.name}</p>
          [#if currentUser.institutions?size != 1]
          <ul id="userInfo-drop" class="drop-down"> 
            [#list currentUser.institutions as institution]
              [@s.url action="selectInstitution" namespace="/" var="url"]
                [@s.param name="institutionID"]${institution.id?c}[/@s.param]
              [/@s.url]
              [#if currentUser.currentInstitution.id != institution.id]
              <li> <a href="${url}" class="institution"> ${institution.name} </a> </li>
              [/#if]
            [/#list]
          </ul>
          [/#if]
        </div>
  	[/#if]
  </div>
</nav> 

<div class="container">
 [#include "/WEB-INF/global/pages/breadcrumb.ftl"]
</div>
<div class="container container_9">
	<section id="generalMessages">
	  [#-- Messages are going to show using notify plugin (see global.js) --]
	  <ul id="messages" style="display: none;">
	  [@s.iterator value="actionMessages"]    
	    <li class="success">[@s.property escape="false" /]</li>    
	  [/@s.iterator]
	  [@s.iterator value="errorMessages"]    
	    <li class="error">[@s.property escape="false" /]</li>    
	  [/@s.iterator]
	  </ul>
	</section>