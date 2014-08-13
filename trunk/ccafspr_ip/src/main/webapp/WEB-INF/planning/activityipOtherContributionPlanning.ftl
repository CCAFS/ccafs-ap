[#ftl]
[#assign title = "Impact Pathways - Other Contribution" /]
[#assign globalLibs = ["jquery", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "activities" /]
[#assign currentStage = "Other Contribution" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.impactPathways.otherContributions.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  
  [@s.form action="ipOtherContribution" cssClass="pure-form"]  
  <article class="halfContent borderBox" id="projectOutcomes"> 
  	 
    <h6 class="contentTitle">
    [@customForm.textArea name="ipOtherContribution.contribution" i18nkey="planning.impactPathways.otherContributions.contribution" /]  
    </h6> <br/>
    <h6 class="contentTitle">
    [@customForm.textArea name="ipOtherContribution.additionalContribution" i18nkey="planning.impactPathways.otherContributions.additionalcontribution" /]  
    </h6> 
     <!-- internal parameter -->
    <input name="activityID" type="hidden" value="" />
    
    <input name="activity.ipOtherContribution.id" type="hidden" value="" />
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]