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

[#assign title = "Select other Location - Acitivty Planning" /]
[#assign globalLibs = ["jquery", "googleMaps"] /]
[#assign customJS = ["${baseUrl}/js/planning/selectLocations.js"] /]
[#include "/WEB-INF/global/pages/popup-header.ftl" /]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="planning.selectLocation.help" /]</p>
  </div>
  <article class="fullContent">
    <div class="fullBlock">
      <h6>[@s.text name="home.activity.otherLocation" /]</h6>
      <p id="otherLocationMessage"></p>
      <div id="other_locations"></div>
    </div>
      
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>

  </article>
</section>
[#include "/WEB-INF/global/pages/js-imports.ftl"]
</body>
</html>