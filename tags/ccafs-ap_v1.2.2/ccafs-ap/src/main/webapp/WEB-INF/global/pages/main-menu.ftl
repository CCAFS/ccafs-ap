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

<nav id="mainMenu">  
  <ul>
    [#if logged]
      [#-- Home element --]
      <a href="${baseUrl}/">
        <li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>
          <span class="icon">
            [#if currentSection?? && currentSection == "home"]
               <img class="icon-15" src="${baseUrl}/images/global/icon-home-menu-selected.png" />
            [#else]
               <img class="icon-15" src="${baseUrl}/images/global/icon-home-menu.png" />
            [/#if]
          </span>
          <span class="text">
            [@s.text name="menu.home" /]
          </span>
        </li>
      </a>
      
      [#-- Planning section --]
      [#if currentUser.CP || currentUser.TL || currentUser.RPL || currentUser.PI || currentUser.admin ]
        [#if planningActive ]               
          <a  href="${baseUrl}/planning/activities.do">
        [#else]
          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
        [/#if]
          <li [#if currentSection?? && currentSection == "planning"] class="currentSection" [/#if]>[@s.text name="menu.planning" /]</li>
        </a>
      [/#if]
      
      [#-- Reporting section --]
      [#if currentUser.CP || currentUser.TL || currentUser.RPL || currentUser.PI || currentUser.admin ] 
        [#if reportingActive ]               
          <a href="${baseUrl}/reporting/introduction.do" >
        [#else]
          <a href="javascript:void(0);" title="[@s.text name="menu.link.disabled" /]" class="disabled">
        [/#if]
          <li [#if currentSection?? && currentSection == "reporting"] class="currentSection" [/#if]>[@s.text name="menu.reporting" /]</li>
        </a>
      [/#if]
      
      [#-- Summaries section --]
      [#if currentUser.TL || currentUser.RPL || currentUser.admin ]
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
      
      [#-- Admin section --]
      [#if currentUser.admin ]                
        <a href="javascript:void(0);">
          <li [#if currentSection?? && currentSection == "admin"] class="currentSection" [/#if]>[@s.text name="menu.admin" /]</li>
        </a>
      [/#if]
    [#else]
      [#-- If the user is not logged show the login element in menu --]
      <a href="${baseUrl}/"><li [#if currentSection?? && currentSection == "home"] class="currentSection" [/#if]>[@s.text name="menu.login" /]</li></a>
    [/#if]
  </ul>
</nav>


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