[#ftl]
[#assign title = "Help - CCAFS P&R" /]
[#assign globalLibs = ["jquery", "noty", "jreject"] /]
[#assign customJS = ["${baseUrl}/js/home/helpHome.js"] /]

[#assign currentSection = "help" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
  [#--<nav id="secondaryMenu" class="projectMenu">
  <ul> 
    <li class="currentSection">
      <p>[@s.text name="home.help.menu" /]</p>
      <ul>
        <li class="currentSection toSubmit">[@s.text name="home.help.menu.userManual" /]</li>
        <li class="toSubmit">[@s.text name="home.help.menu.templates" /]</li>
        <li class="toSubmit">[@s.text name="home.help.menu.dmsp" /]</li>
        <li class="toSubmit">[@s.text name="home.help.menu.faq" /]</li>
        <li class="toSubmit">[@s.text name="home.help.menu.glossary" /]</li>
      </ul>
    </li>
  </ul>
  </nav>--]
  
  <article id="helpHome" class="content">
    <h1 class="">P&R Help</h1>
    <div class="borderBox">
      [#-- User Manual --]
      <div id="userManual">
        <a id="helpTitleSection" href="#" onClick="displaySection('userManualSection')"> <h1 class="contentTitle">[@s.text name="home.help.menu.userManual" /]</h1></a>
        <div class="helpContent" id="userManualSection">
          <a href="${baseUrl}/resources/helpDocs/CCAFS_PR_UserManual_20151015.pdf" target="_blank">
            [@s.text name="home.help.download" /]&nbsp;<p>[@s.text name="home.help.menu.userManual" /]</p>
            <img src="${baseUrl}/images/global/icon_pdf.png"  width="100"/>
          </a>
        </div>
      </div>
      
      [#-- Templates --]
      <div id="templates">
        <a href="#" onClick="displaySection('templatesSection')"><h1 class="contentTitle">[@s.text name="home.help.menu.templates" /]</h1></a>
        <div class="helpContent" id="templatesSection">
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
      <div id="dmsp">
        <a href="#" onClick="displaySection('dmspSection')"><h1 class="contentTitle">[@s.text name="home.help.menu.dmsp" /]</h1></a>
        <div class="helpContent" id="dmspSection">
          <iframe src="https://dmsp.ccafs.cgiar.org" frameborder="0" width="100%" height="644" scrolling="no"></iframe>
        </div>
      </div>
      
      [#-- FAQ --]
      [#-- <div id="faq">
        <h1 class="contentTitle">[@s.text name="home.help.menu.faq" /]</h1>
        <div class="helpContent">&nbsp;</div>
      </div>--]
      [#-- Glossary --]
      <div id="helpGlossary">
        <a href="#" onClick="displaySection('helpGlossarySection')"><h1 class="contentTitle">[@s.text name="home.glossary.title"/]</h1></a>
        <div class="helpContent" id="helpGlossarySection">
        <article class="fullContent" id="helpGlossary">
        [@s.text name="home.glossary.contact"/] [@s.text name="home.glossary.mailto"/]
        <hr/>
        <div id="note" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.note"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.note.definition"/]
          </div>
        </div>
        <hr/>
        <div id="accountability" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.accountability"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.accountability.definition"/]
          </div>
        </div>
        
        <div id="mutualAccountability" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.mutualaccountability"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.mutualaccountability.definition"/]
          </div>
        </div>
        
        <div id="activity" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.activities"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.activities.definition"/]
          </div>
        </div>
        
        <div id="adoption" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.adoption"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.adoption.definition"/]
          </div>
        </div>
    
        <div id="appraisal" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.appraisal"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.appraisal.definition"/]
          </div>
        </div>
        
        <div id="attribution" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.attribution"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.attribution.definition"/]
          </div>
        </div>
          
        <div id="audit" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.audit"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.audit.definition"/]
          </div>
        </div>
        
        <hr/>
        
        <div id="baseline" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.baseline"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.baseline.definition"/]
          </div>
        </div>
        
        <div id="behavioralindependence" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.behavioralindependence"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.behavioralindependence.definition"/]
          </div>
        </div>
        
        <div id="beneficiaries" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.beneficiaries"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.beneficiaries.definition"/]
          </div>
        </div>
        
        <hr/>
        
        <div id="budget" class="word">
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
        
        <div id="clustersofactivities" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.clustersofactivities"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.clustersofactivities.definition"/]
          </div>
        </div>
        
        <div id="clients" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.clients"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.clients.definition"/]
          </div>
        </div>
        
        <div id="comparativeadvantage" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.comparativeadvantage"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.comparativeadvantage.definition"/]
          </div>
        </div>
        
        <div id="costeffectiveness" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.costeffectiveness"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.costeffectiveness.definition"/]
          </div>
        </div>
        
        <div id="counterfactual" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.counterfactual"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.counterfactual.definition"/]
          </div>
        </div>
        
        <hr/>
        <div id="deliverables" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.deliverables"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.deliverables.definition"/]
          </div>
        </div>
        <hr/>
        <div id="endUsers" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.endUsers"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.endUsers.definition"/]
          </div>
        </div>
        
        <div id="effectiveness" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.effectiveness"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.effectiveness.definition"/]
          </div>
        </div>
        
        <div id="efficiency" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.efficiency"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.efficiency.definition"/]
          </div>
        </div>
        
        <div id="evaluation" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.evaluation"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.evaluation.definition"/]
          </div>
        </div>
        
        <div id="formativeevaluation" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.formativeevaluation"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.formativeevaluation.definition"/]
          </div>
        </div>
        
        <div id="summativeevaluation" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.summativeevaluation"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.summativeevaluation.definition"/]
          </div>
        </div>
        
        <div id="evaluationcriteria" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.evaluationcriteria"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.evaluationcriteria.definition"/]
          </div>
        </div>
          
        <div id="evaluationreference" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.evaluationreference"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.evaluationreference.definition"/]
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
        
        <div id="globalpublicgoods" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.globalpublicgoods"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.globalpublicgoods.definition"/]
          </div>
        </div>
        
        <hr/>
        
        <div id="impact" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.impact"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.impact.definition"/]
          </div>
        </div>
        
        <div id="impactassessment" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.impactassessment"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.impactassessment.definition"/]
          </div>
        </div>
          
        <div id="impactevaluation" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.impactevaluation"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.impactevaluation.definition"/]
          </div>
        </div>
        
        <div id="impactPathway" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.impactPathway"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.impactPathway.definition"/]
          </div>
        </div>
        
        <div id="impartiality" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.impartiality"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.impartiality.definition"/]
          </div>
        </div>
        
        <div id="independence" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.independence"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.independence.definition"/]
          </div>
        </div>
        
        <div id="organizationalindependence" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.organizationalindependence"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.organizationalindependence.definition"/]
          </div>
        </div>
        
        <div id="behavioralindependence" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.behavioralindependence"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.behavioralindependence.definition"/]
          </div>
        </div>
        
        <div id="indicator" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.indicator"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.indicator.definition"/]
          </div>
        </div>
        
        <div id="inputs" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.inputs"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.inputs.definition"/]
          </div>
        </div>
        
        <div id="ido" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.ido"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.ido.definition"/]
          </div>
        </div>
        
        <div id="ipg" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.ipg"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.ipg.definition"/]
          </div>
        </div>
        
        <hr/>
        
        <div id="legitimacy" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.legitimacy"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.legitimacy.definition"/]
          </div>
        </div>
        
        <hr/>
        
        <div id="mog" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.mog"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.mog.definition"/]
          </div>
        </div>
        
        <div id="managementLiaison" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.managementLiason"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.managementLiason.definition"/]
          </div>
        </div>
        
        <div id="monitoring" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.monitoring"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.monitoring.definition"/]
          </div>
        </div>
        
        <hr/>
        <div id="next-users" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.nextUsers"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.nextUsers.definition"/]
          </div>
        </div>
        <hr/>
        
        <div id="organizationalindependence" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.organizationalindependence"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.organizationalindependence.definition"/]
          </div>
        </div>
        
        <div id="project-outcome" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.outcome"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.outcome.definition"/]
          </div>
        </div>
        <div id="outcome-statement" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.outcomestatement"/]
          </div>
          <div class="wordTitle">
            [@s.text name="home.glossary.outcomestory"/]
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
        <div id="partners" class="word">
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
        
        <div id="peerreview" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.peerreview"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.peerreview.definition"/]
          </div>
        </div>
        
        <div id="performancemanagement" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.performancemanagement"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.performancemanagement.definition"/]
          </div>
        </div>
        
        <div id="performancemeasurement" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.performancemeasurement"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.performancemeasurement.definition"/]
          </div>
        </div>
        
        <div id="project" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.project"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.project.definition"/]
          </div>
        </div>
        
        <div id="projectLeader" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.projectLeader"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.projectLeader.definition"/]
          </div>
        </div>
        
        <div id="projectOutcome" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.projectOutcome"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.projectOutcome.definition"/]
          </div>
        </div>
        
        <div class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.projectOutcome.story"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.projectOutcome.story.definition"/]
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
        
        <div id="relevance" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.relevance"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.relevance.definition"/]
          </div>
        </div>
        
        <div id="researchoutcomes" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.researchoutcomes"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.researchoutcomes.definition"/]
          </div>
        </div>
        
        <div id="results" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.results"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.results.definition"/]
          </div>
        </div>
        
        <div class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.resultsBaseManagement"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.resultsBaseManagement.definition"/]
          </div>
        </div>
        
        <div id="review" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.review"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.review.definition"/]
          </div>
        </div>
        
        <hr/>
        
        <div id="scalingupandout" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.scalingupandout"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.scalingupandout.definition"/]
          </div>
        </div>
        
        <div id="stakeholders" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.stakeholders"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.stakeholders.definition"/]
          </div>
        </div>
        
        <div id="sustainability" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.sustainability"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.sustainability.definition"/]
          </div>
        </div>
        
        <div id="slo" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.slo"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.slo.definition"/]
          </div>
        </div>
        
        <hr/>
        
        <div id="targetgroup" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.targetgroup"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.targetgroup.definition"/]
          </div>
        </div>
        
        <div id="targetnarrative" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.targetnarrative"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.targetnarrative.definition"/]
          </div>
        </div>
        
        <div id="targetvalue" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.targetvalue"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.targetvalue.definition"/]
          </div>
        </div>
        
        <div class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.toc"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.toc.definition"/]
          </div>
        </div>
        
        <div id="transactioncost" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.transactioncost"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.transactioncost.definition"/]
          </div>
        </div>
        
        <div id="triangulation" class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.triangulation"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.triangulation.definition"/]
          </div>
        </div>
        
        <hr/>
        <div class="word">
          <div class="wordTitle">
            [@s.text name="home.glossary.update"/]
          </div>
          <div class="wordDefinition">
            [@s.text name="home.glossary.update.definition"/]
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
        <hr/>
        <div id="adapted">
          <p>[@s.text name="home.glossary.adapted"/]</p>
        </div>
        <hr/>
        <div class="lastUpdate">
          <p>[@s.text name="home.glossary.lastUpdate"/]</p>
        </div>
      </div>
      </div>
    </div>
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]