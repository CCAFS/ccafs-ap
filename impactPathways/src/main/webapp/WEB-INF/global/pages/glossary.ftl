[#ftl]

[#assign title = "Glossary" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign currentSection = "home" /]
[#assign currentPlanningSection = "glossary" /]
[#assign currentStage = "glossary" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

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


<section class="content">
  [@s.form action="glossary" cssClass="pure-form"]
    <article class="fullContent" id="glossary-content">
    <h1 class="contentTitle">[@s.text name="home.glossary.title"/]</h1>
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
  [/@s.form]
</section>

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

[#include "/WEB-INF/global/pages/footer.ftl"]