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

[#assign title = "Welcome to CCAFS Activity Planning" /]
[#assign globalLibs = ["jquery", "jreject"] /]
[#assign customJS = ["${baseUrl}/js/home/login.js"] /]
[#assign currentSection = "home" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<article>
  <div class="content">
    <h1>[@s.text name="home.home.title" /]</h1>

    [#-- Home introduction  --]
    <p> [@s.text name="home.home.introduction" /] </p>
    
    <div id="loginFormContainer">
      <p class="alreadyLogged">[@s.text name="home.login.alreadyLogged" /]</p>
      <span class="alreadyLoggedEmail">${currentUser.email}</span>
    </div>
      
    <br>
  </div>
</article>
[#include "/WEB-INF/global/pages/footer-logos.ftl"]