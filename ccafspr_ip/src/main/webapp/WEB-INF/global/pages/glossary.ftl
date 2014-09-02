[#ftl]

[#assign title = "Glossary" /]
[#assign globalLibs = ["jquery", "noty"] /]
[#assign currentSection = "home" /]
[#assign currentPlanningSection = "glossary" /]
[#assign currentStage = "glossary" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]


<section class="content">
  [@s.form action="glossary" cssClass="pure-form"]
    <article class="fullContent" id="glossary">
    <h1 class="contentTitle">[@s.text name="home.glossary.title"/]</h1>
    [@s.text name="home.glossary.contact"/] [@s.text name="home.glossary.mailto"/]
    <hr/>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.activities"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.activities.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.adoption"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.adoption.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.beneficiaries"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.beneficiaries.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.baseline"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.baseline.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.budget"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.budget.definition"/]
        <ul class="listGlossaryItems">
          <li>[@s.text name="home.glossary.budget.definition.w1"/]</li>
          <li>[@s.text name="home.glossary.budget.definition.w2"/]</li>
          <li>[@s.text name="home.glossary.budget.definition.w3"/]</li>
          <li>[@s.text name="home.glossary.budget.definition.bilateral"/]</li>
          [#-- <li>[@s.text name="home.glossary.budget.definition.leveraged"/]</li>
          <li>[@s.text name="home.glossary.budget.definition.activity"/]</li> --]
        </ul>
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.deliverables"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.deliverables.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.endUsers"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.endUsers.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.evidence"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.evidence.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.impact"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.impact.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.impactPathway"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.impactPathway.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.indicator"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.indicator.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.ido"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.ido.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.mog"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.mog.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.managementLiason"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.managementLiason.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.nextUsers"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.nextUsers.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.outcome"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.outcome.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.outputs"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.outputs.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.partners"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.partners.definition1"/]
        <ul>
          <li>[@s.text name="home.glossary.partners.definition2"/]</li>
          <li>[@s.text name="home.glossary.partners.definition3"/]</li>
        </ul>
        [@s.text name="home.glossary.partners.definition4"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.project"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.project.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.projectLeader"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.projectLeader.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.projectOutcome"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.projectOutcome.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.projectPartner"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.projectPartner.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.resultsBaseManagement"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.resultsBaseManagement.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.toc"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.toc.definition"/]
      </div>
    </div>
    <hr/>
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.uptake"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.uptake.definition"/]
      </div>
    </div>
    
    <div class="word">
      <div class="wordTitle">
        [@s.text name="home.glossary.use"/]
      </div>
      <div class="wordDefinition">
        [@s.text name="home.glossary.use.definition"/]
      </div>
    </div>
    <div class="lastUpdate">
      <p>[@s.text name="home.glossary.lastUpdate"/]</p>
    </div>
  [/@s.form]
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]