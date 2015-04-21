[#ftl]
[#assign title = "Activity Summaries" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign currentSection = "summaries" /]
[#assign currentSummariesSection = "milestones" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="summaries.outcomes.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/summaries-secondary-menu.ftl" /]


  [@s.form action="milestones"]  
  <article class="halfContent" id="milestonesSummaries">
    <h1 class="contentTitle">
      [@s.text name="summaries.milestones.title" /] 
    </h1>

    <div class="options">

      <div class="reportType fullBlock">
        [@customForm.radioButtonGroup i18nkey="summaries.milestones.reportType" label="" name="summaryTypeSelected" listName="summaryTypes" /]
        
        <div id="reportOptions">          
          <div class="thirdPartBlock" class="year">
            [@customForm.select name="logframeSelected" label="" i18nkey="summaries.milestones.logframe" listName="logframes" keyFieldName="id" displayFieldName="name" /]
          </div>
                  
          <div class="thirdPartBlock" class="theme">
            [@customForm.select name="themeSelected" label="" i18nkey="summaries.milestones.theme" listName="themes" keyFieldName="id" displayFieldName="code" /]
          </div>
          
          <div class="thirdPartBlock" class="milestone">
            [@customForm.select name="milestoneSelected" label="" i18nkey="summaries.milestones.milestone" listName="milestones" keyFieldName="id" displayFieldName="code" /]
          </div>          
        </div>
        
      </div>
      
      
      <div class="fullBlock" id="MilestoneStatusReportOptions">
      </div>
      
      <div class="fullBlock" id="MilestoneReportOptions">
      </div>
      
    </div>

    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.generate" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]