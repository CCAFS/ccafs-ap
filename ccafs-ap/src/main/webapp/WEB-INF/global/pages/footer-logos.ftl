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

      <footer>
        <div id="footerContainer">          
          <div id="centres-logos">
            <div id="rowOne">
              <img src="${baseUrl}/images/global/centers/logo_africarice.jpg" alt="Africa Rice Logo" height="60px" width="83" />
              <img src="${baseUrl}/images/global/centers/logo_bioversity.jpg" alt="Bioversity Logo" height="60px" width="68" />
              <img src="${baseUrl}/images/global/centers/logo_ciat.jpg" alt="CIAT Logo" height="60px" width="139" />
              <img src="${baseUrl}/images/global/centers/logo_cifor.jpg" alt="CIFOR Logo" height="60px" width="63" />
              <img src="${baseUrl}/images/global/centers/logo_cimmyt.jpg" alt="CIMMYT Logo" height="60px" width="80" />
              <img src="${baseUrl}/images/global/centers/logo_cip.jpg" alt="CIP Logo" height="60px" width="69" />
              <img src="${baseUrl}/images/global/centers/logo_icarda.gif" alt="ICARDA Logo" height="60px" width="64" />
              <img src="${baseUrl}/images/global/centers/logo_icraf.jpg" alt="ICRAF Logo" height="60px" width="110" />
              <img src="${baseUrl}/images/global/centers/logo_icrisat.jpg" alt="ICRISAT Logo" height="60px" width="43" />
            </div>
            <div id="rowTwo">
              <img src="${baseUrl}/images/global/centers/logo_ifpri.jpg" alt="IFPRI Logo" height="60px" width="38" />
              <img src="${baseUrl}/images/global/centers/logo_iita.gif" alt="IITA Logo" height="55px" width="116" style="vertical-align: super;"/>
              <img src="${baseUrl}/images/global/centers/logo_ilri.jpg" alt="ILRI Logo" height="60px" width="60" />
              <img src="${baseUrl}/images/global/centers/logo_irri.jpg" alt="IRRI Logo" height="55px" style="vertical-align: super;" width="135" />
              <img src="${baseUrl}/images/global/centers/logo_iwmi.png" alt="IWMI Logo" height="60px" width="80" />
              <img src="${baseUrl}/images/global/centers/logo_worldfish.jpg" alt="WORLDFISH Logo" height="60px" width="103" />
            </div>            
          </div> <!-- end centres-logos -->
          <div id="copyright">
            <p>&#64; Copyright 2013</p>
            <p>Current version 1.2.2</p>
          </div>
          <div id="reportIssue">
            <p><b>[@s.text name="footer.report.issue.first" /]<a target="_blank" href="https://docs.google.com/forms/d/1EouZJYHqERbDRB2DaT6Q1cY-_-Tqe0daf4fxXrBU-ts/viewform">[@s.text name="footer.report.issue.second" /]</a></b></p>
          </div>
        </div>
      </footer>
    </div> <!-- end container -->
    <div id="scriptsContainer">
      [#include "/WEB-INF/global/pages/js-imports.ftl"]
    </div>
    
  </body>
</html>