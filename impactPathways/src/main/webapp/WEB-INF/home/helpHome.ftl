[#ftl]
[#assign title = "Help - CCAFS P&R" /]
[#assign globalLibs = ["jquery", "jqueryUI", "noty", "jreject"] /]
[#assign customJS = ["${baseUrl}/js/home/helpHome.js"] /]

[#assign currentSection = "help" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]


<section class="content">
  <article id="helpHome" class="content">
    <h1 class="">P&R Help</h1>

      <div id="tabs">
        <ul>
          <li><a href="#tabs-1">[@s.text name="home.help.menu.userManual" /]</a></li>
          <li><a href="#tabs-3">[@s.text name="home.help.menu.dmsp" /]</a></li>
          <li><a href="#tabs-4">[@s.text name="home.glossary.title"/]</a></li>
          [#-- <li><a href="#tabs-5">[@s.text name="home.help.menu.faq" /]</a></li>--]
        </ul>
        [#-- User Guides --]
        <div id="tabs-1">
          <div id="userManualSection">
            <a href="${baseUrl}/resources/helpDocs/CCAFS_PR_UserManual_20151015.pdf" target="_blank">
              [@s.text name="home.help.download" /]&nbsp;<p>[@s.text name="home.help.menu.userManual" /]</p>
              <img src="${baseUrl}/images/global/icon_pdf.png"  width="100"/>
            </a>
          </div>
          <div id="templatesSection">
            <div id="template-downloads1">
              <a href="${baseUrl}/resources/helpDocs/CCAFS_PR_UserManual_20151015.pdf" target="_blank">
                [@s.text name="home.help.download" /]&nbsp;<p>[@s.text name="home.help.projectCore" /]</p>
                <img src="${baseUrl}/images/global/icon_pdf.png"  width="100"/>
              </a>
              </div>
              <div id="template-downloads2">
              <a href="${baseUrl}/resources/helpDocs/CCAFS_PR_UserManual_20151015.pdf" target="_blank">
                [@s.text name="home.help.download" /]&nbsp;<p>[@s.text name="home.help.projectBilateral" /]</p>
                <img src="${baseUrl}/images/global/icon_pdf.png" width="100"/>
              </a>
            </div>
          </div>
        </div>
        [#-- Data Management Support Pack --]
        <div id="tabs-3">
          <div id="dmspSection">
            <iframe id="iframeDMSP" src="https://dmsp.ccafs.cgiar.org" frameborder="0" width="100%" height="1000" scrolling="yes"></iframe>
          </div>
        </div>
        [#-- Glossary --]
        <div id="tabs-4">
          [#assign glossaryContent= [ 
          [ "accountability", "mutualAccountability", "activities", "adoption", "appraisal", "attribution", "audit"],
          [ "baseline", "behavioralIndependence", "beneficiaries", "budget"],
          [ "clusterOfActivities", "clients", "comparativeAdvantage", "costEffectiveness", "counterfactual"],
          [ "deliverables"],
          [ "endUsers", "effectiveness", "efficiency", "evaluation", "evidence", "formativeEvaluation", "summativeEvaluation", "evaluationCriteria", "evaluationReference"],
          [ "globalPublicGoods"],
          [ "impact", "impactAssesment", "impactEvaluation", "impactPathway", "impartiality", "independence", "orgIndependence", "behIndependence", "indicator", "inputs", "ido", "internationalPublicGoods"],
          [ "legitimacy"],
          [ "mog", "managementLiason", "monitoring", "nextUsers"],
          [ "orgIndependence", "outcome", "outcomestatement", "outcomestory", "outputs"],
          [ "partners", "peerReview", "performanceManagement", "performanceMeasureement", "project", "projectLeader", "projectOutcome", "projectOutcomeStory", "projectPartner"],
          [ "relevance", "researchOutcomes", "results", "resultsBaseManagement", "review"],
          [ "scaling", "stakeholders", "sustainability", "systemLevelOutcomes"],
          [ "targetGroup", "targetnarrative", "targetvalue", "toc", "transactionCost", "triangulation"],
          [ "update" , "use"]
          ] /]
      
          
          [@s.text name="home.glossary.contact"/] [@s.text name="home.glossary.mailto"/]

          [#-- List the terms with his definition --]
          [#list glossaryContent as letter]
            <hr />
            [#list letter as word] 
              [@wordDefinition word=word /]
            [/#list]
          [/#list]  
          
          <div class="lastUpdate">
            <p>[@s.text name="home.glossary.lastUpdate"/]</p>
          </div>
            
    
          [#macro wordDefinition word]
          <div id="${word}" class="word">
            <div class="wordTitle">[@s.text name="home.glossary.${word}"/]</div>
            <div class="wordDefinition">[@s.text name="home.glossary.${word}.definition"/]
              [#if word == "budget"]
                <ul class="listGlossaryItems">
                  <li>[@s.text name="home.glossary.budget.definition.w1"/]</li>
                  <li>[@s.text name="home.glossary.budget.definition.w2"/]</li>
                  <li>[@s.text name="home.glossary.budget.definition.w3"/]</li>
                  <li>[@s.text name="home.glossary.budget.definition.bilateral"/]</li>
                </ul>
              [#elseif word == "partners"]
                <ul>
                  <li>[@s.text name="home.glossary.partners.definition2"/]</li>
                  <li>[@s.text name="home.glossary.partners.definition3"/]</li>
                </ul>
                [@s.text name="home.glossary.partners.definition4"/]  
              [/#if]
            </div>
          </div>
          [/#macro]
        </div> [#-- End Tabs 4 Glossary--]
        [#-- FAQ's --]
        [#--
        <div id="tabs-5">
          <h1 class="contentTitle">[@s.text name="home.help.menu.faq" /]</h1>
          <div >&nbsp;</div>
        </div>--]
      </div>

  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]