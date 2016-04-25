[#ftl]
<nav id="mainMenu">  
	<div class="container">
	  <ul>
	    [#if logged]
	      [#-- Home element --]
        <li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>
  	      <a href="${baseUrl}/">
	         <span class="icon"><img class="icon-15" src="${baseUrl}/images/global/icon-home-menu-selected.png" /></span>
	         <span class="text">[@s.text name="menu.home" /]</span>
  	      </a>
        </li>
	      
        [#-- P&R Overview
        <li [#if currentSection?? && currentSection == "overview"] class="currentSection" [/#if]>
          <a href="${baseUrl}/overview.do">
            <span class="text">[@s.text name="menu.overview" /]</span>
          </a>
        </li>
        --]
        [#-- PRE-Planning section --]
        [#if securityContext.FPL || securityContext.RPL || securityContext.ML || securityContext.CU || securityContext.admin ]
        <li [#if currentSection?? && currentSection == "preplanning"] class="currentSection" [/#if]>
          <a [#if preplanningActive ]href="[@s.url namespace="/pre-planning" action='intro'/]">[#else]href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">[/#if]
            [@s.text name="menu.preplanning" /]
          </a>
        </li>
        [/#if]
       
        [#-- Planning section --]
        <li [#if currentSection?? && currentSection == "planning"] class="currentSection" [/#if]>
          <a [#if planningActive ]href="[@s.url namespace="/planning" action='projectsList'/]">[#else]href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">[/#if]
          [@s.text name="menu.planning" /]</a>
        </li>
	      
	      [#-- Reporting section --]
	      <li [#if currentSection?? && currentSection == "reporting"] class="currentSection" [/#if]>
          <a [#if reportingActive ]href="${baseUrl}/reporting/projectsList.do"[#else]]href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled"[/#if]>
            [@s.text name="menu.reporting" /]
          </a>
          [#assign reportingCycleValid = currentCycleSection?? && reportingCycle/]
          <ul class="subMenu">
            <li [#if reportingCycleValid && (currentCycleSection  == "projects")] class="currentSection" [/#if] >
              <a href="[#if reportingActive]${baseUrl}/reporting/projectsList.do[/#if]" class="[#if !reportingActive]disabled[/#if]">Projects</a>
            </li>
            [#-- Contact points and Flagships Leaders : securityContext.FPL || securityContext.CP || --]
            <li [#if reportingCycleValid && (currentCycleSection  == "crpIndicators")] class="currentSection" [/#if] >
              <a href="${baseUrl}/reporting/synthesis/crpIndicators.do?liaisonInstitutionID&edit=true"  class="[#if !reportingActive]disabled[/#if]">CRP Indicators</a>
            </li>
          
            [#-- Flagships Leaders and Regional Leaders : securityContext.FPL || securityContext.RPL || --]
            <li [#if reportingCycleValid && (currentCycleSection  == "outcomeSynthesis")] class="currentSection" [/#if] >
              <a href="${baseUrl}/reporting/synthesis/outcomeSynthesis.do?liaisonInstitutionID&edit=true" class="[#if !reportingActive]disabled[/#if]" >Outcome Synthesis</a>
            </li>
         
            [#-- Flagships Leaders and Regional Leaders : securityContext.FPL || securityContext.RPL || --]
            <li [#if reportingCycleValid && (currentCycleSection  == "synthesisByMog")] class="currentSection" [/#if] >
              <a href="${baseUrl}/reporting/synthesis/synthesisByMog.do?liaisonInstitutionID&edit=true" class="[#if !reportingActive]disabled[/#if]" >Synthesis by MOG</a>
            </li> 
            
            [#-- Project Evaluation --]
            <li [#if reportingCycleValid && (currentCycleSection  == "projectsEvaluation")] class="currentSection" [/#if] >
              <a href="${baseUrl}/reporting/projectsEvaluation.do" class="[#if !reportingActive]disabled[/#if]" >Project Evaluation</a>
            </li> 
          
            
          </ul>
        </li>
              
	      [#-- Summaries section --]
        <li [#if currentSection?? && currentSection == "summaries"]class="currentSection"[/#if]>
          <a [#if summariesActive ]href="${baseUrl}/summaries/board.do" /]"[#else]href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled"[/#if]>
            [@s.text name="menu.summaries" /]
          </a>
        </li>
	      
	      [#-- P&R Help --]
        <li [#if currentSection?? && currentSection == "help"] class="currentSection" [/#if]>
          <a href="${baseUrl}/help.do">
            <span class="text">[@s.text name="menu.help" /]</span>
          </a>
        </li>
        
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
	      <li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>
  	      <a href="${baseUrl}/">[@s.text name="menu.login" /]</a>
	      </li>

	      [#-- P&R Overview 
        <li [#if currentSection?? && currentSection == "overview"] class="currentSection" [/#if]>
          <a href="${baseUrl}/overview.do"><span class="text">[@s.text name="menu.overview" /]</span></a>
        </li>
        --]
        [#-- P&R Help --]
        <li [#if currentSection?? && currentSection == "help"] class="currentSection" [/#if]>
          <a href="${baseUrl}/help.do"><span class="text">[@s.text name="menu.help" /]</span></a>
        </li>
	      
	    [/#if]
	  </ul>
	  
	  [#if logged]
      <div id="userInfo">
        <a id="userLogOut" href="[@s.url action="logout" namespace="/" /]">[@s.text name="header.logout" /]</a>
        <p class="userId" style="display:none">${currentUser.id}</p> 
        <p class="name">${currentUser.firstName} ${currentUser.lastName}</p>  
        <p class="institution">${currentUser.email}</p>
        <p class="roles">${(securityContext.roles)!}
         [#if currentUser.liaisonInstitution??][#list currentUser.liaisonInstitution as liaison]${(liaison.acronym)!}[#sep], [/#list] [/#if]
        </p>
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
