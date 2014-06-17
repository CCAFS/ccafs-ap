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

[#assign title = "Communications Report" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#-- assign customJS = ["${baseUrl}/js/reporting/outcomesReporting.js", "${baseUrl}/js/global/utils.js"] / --]
[#-- assign customCSS = ["${baseUrl}/css/reporting/partnersReporting.css"] / --]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "communications" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.communications.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  [@s.form action="communications"]
    <article class="halfContent">
      <h1 class="contentTitle">
        [@s.text name="reporting.communications" /] - ${currentUser.leader.acronym} 
      </h1>
      <div id="items">      
        <fieldset id="CommunicationReport">
          <input type="hidden" id="communicationReportId" name="communicationReport.id" value="${communicationReport.id?c}">
        
          [#-- Media Campaigns --]
          <div class="fullBlock">
            [@customForm.textArea name="communicationReport.mediaCampaings" i18nkey="reporting.communications.mediaCampaings"  /]
          </div> 

          [#-- Blogs --]
          <div class="fullBlock">
            [@customForm.textArea name="communicationReport.blogs" i18nkey="reporting.communications.blogs"  /]
          </div> 

          [#-- Websites --]
          <div class="fullBlock">
            [@customForm.textArea name="communicationReport.websites" i18nkey="reporting.communications.websites"  /]
          </div> 

          [#-- Social media campaigns --]
          <div class="fullBlock">
            [@customForm.textArea name="communicationReport.sociaMediaCampaigns" i18nkey="reporting.communications.sociaMediaCampaigns"  /]
          </div> 

          [#-- Newsletters--]
          <div class="fullBlock">
            [@customForm.textArea name="communicationReport.newsletters" i18nkey="reporting.communications.newsletters"  /]
          </div> 

          [#-- Events --]
          <div class="fullBlock">
            [@customForm.textArea name="communicationReport.events" i18nkey="reporting.communications.events"  /]
          </div> 

          [#-- Videos and other multimedia --]
          <div class="fullBlock">
            [@customForm.textArea name="communicationReport.videosMultimedia" i18nkey="reporting.communications.videosMultimedia"  /]
          </div> 

          [#-- Other communications and outreach --]
          <div class="fullBlock">
            [@customForm.textArea name="communicationReport.otherCommunications" i18nkey="reporting.communications.otherCommunications"  /]
          </div> 

        </fieldset>
      </div>

      [#if canSubmit]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      [/#if]
    </article>
  [/@s.form]
 
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]