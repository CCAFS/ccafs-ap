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

[#include "/WEB-INF/global/pages/head.ftl"]
<body>
[#if !(avoidHeader!false)]
  <div class="container">    
    <header class="clearfix">
      [#if logged]
        <div id="userInfo">
          <span class="email">${currentUser.email}</span>
          <span class="logout"><a href="[@s.url action="logout" namespace="/" /]">[@s.text name="header.logout" /]</a></span>
        </div>
      [/#if]
      <div id="mainLogo"><img src="${baseUrl}/images/global/logo-ap.png" alt="CCAFS Activity Planning Logo" width="500px"></img></div>
      <div id="ccafsLogo"><img src="${baseUrl}/images/global/logo-ccafs.png" alt="CCAFS Logo" width="350px"></div>      
    </header>
[/#if]