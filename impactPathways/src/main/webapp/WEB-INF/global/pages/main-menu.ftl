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
	          <span class="text">[@s.text name="menu.home" /]</span>
	        </li>
	      </a>
	      
	      [#-- P&R Overview
        <a href="${baseUrl}/overview.do">
          <li [#if currentSection?? && currentSection == "overview"] class="currentSection" [/#if]>
            <span class="text">[@s.text name="menu.overview" /]</span>
          </li>
        </a>
	      --]
	      [#-- PRE-Planning section --]
	      [#if securityContext.FPL || securityContext.RPL || securityContext.ML || securityContext.CU || securityContext.admin ]
	        [#if preplanningActive ]
  	          <a  href="[@s.url namespace="/pre-planning" action='intro'/]">
	        [#else]
	          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
	        [/#if]
	          <li [#if currentSection?? && currentSection == "preplanning"] class="currentSection" [/#if]>[@s.text name="menu.preplanning" /]</li>
	        </a>
	      [/#if]
	      
	      [#-- Planning section --]
	      [#if !securityContext.guest ]
	        [#if planningActive ]               
	          <a  href="[@s.url namespace="/planning" action='projectsList'/]">
	        [#else]
	          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
	        [/#if]
	          <li [#if currentSection?? && currentSection == "planning"] class="currentSection" [/#if]>[@s.text name="menu.planning" /]</li>
	        </a>
	      [/#if]
	      
	      [#-- Reporting section --]
	      [#if !securityContext.guest ] 
	        [#if reportingActive ]               
	          <a href="${baseUrl}/reporting/introduction.do" >
	        [#else]
	          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
	        [/#if]
	          <li [#if currentSection?? && currentSection == "reporting"] class="currentSection" [/#if]>[@s.text name="menu.reporting" /]</li>
	        </a>
	      [/#if]	      
	     
	      [#-- Summaries section --]
	      [#if !securityContext.guest ] 
	        [#if summariesActive ]
	          <a href="${baseUrl}/summaries/board.do" /]" >
	              <li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>[@s.text name="menu.summaries" /]</li>
	          </a>
	        [#else]
	          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
	              <li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>[@s.text name="menu.summaries" /]</li>
	          </a>
	        [/#if]
	      [/#if]
	      
	      [#-- Admin section --]
	      [#--
	      [#if securityContext.admin ]
	        <a href="javascript:void(0);">
	          <li [#if currentSection?? && currentSection == "admin"] class="currentSection" [/#if]>[@s.text name="menu.admin" /]</li>
	        </a>
	      [/#if]
	      --]
	    [#else]
	      [#-- If the user is not logged show the login element in menu --]
	      <a href="${baseUrl}/"><li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>[@s.text name="menu.login" /]</li></a>

	      [#-- P&R Overview
        <a href="${baseUrl}/overview.do">
          <li [#if currentSection?? && currentSection == "overview"] class="currentSection" [/#if]>
            <span class="text">[@s.text name="menu.overview" /]</span>
          </li>
        </a>
	      --]
	    [/#if]
	  </ul>
	  
	  [#if logged]
        <div id="userInfo">
          <a id="userLogOut" href="[@s.url action="logout" namespace="/" /]">[@s.text name="header.logout" /]</a>
          <p class="userId" style="display:none">${currentUser.id}</p> 
          <p class="name">${currentUser.firstName} ${currentUser.lastName}</p>  
          <p class="institution">${currentUser.email}</p>
          <p class="roles">${securityContext.roles}</p>
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