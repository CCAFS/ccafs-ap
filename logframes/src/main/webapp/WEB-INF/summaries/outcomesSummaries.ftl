[#ftl]
[#assign title = "Activity Summaries" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign currentSection = "summaries" /]
[#assign currentSummariesSection = "outcomes" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="summaries.outcomes.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/summaries-secondary-menu.ftl" /]


  [@s.form action="outcomes"]  
  <article class="halfContent" id="outcomesSummaries">
    <h1 class="contentTitle">
      [@s.text name="summaries.outcomes.title" /] 
    </h1>

    <div class="options">

      <div class="filters">
        <h6>[@s.text name="summaries.outcomes.selectFilter" /]</h6>

        [#-- Leader --]        
        [#if securityContext.Admin]
          <div class="halfPartBlock">
            [@customForm.select name="leadersSelected" label="" i18nkey="summaries.outcomes.leader" listName="leaders" keyFieldName="id" displayFieldName="acronym" /]
          </div>
        [#else]
          <input type="hidden" name="leadersSelected" value="${currentUser.leader.id}" />
        [/#if]

        [#-- Year --]
        <div class="halfPartBlock">
          [@customForm.select name="yearSelected" label="" i18nkey="summaries.outcomes.year" listName="yearList" /]
        </div>

      </div>
    </div>

    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.generate" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]