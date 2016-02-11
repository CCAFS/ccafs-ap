[#ftl]
[#assign title = "Create Co-Funded Project" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/projects/addCofundedProject.js"] /]
[#assign currentSection = "planning" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
<section class="content">
   <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>//TODO</p>
  </div>
  <article id="addCoFundedProject" class="container container_9">
    <div class="borderBox">
      <h1>[@s.text name="projects.addCoFundedProject.title" /]</h1>
      [@s.form action="addCoFundedProject" cssClass="pure-form"]
        [#-- Bilateral Projects List--]
        <div id="" class="fullPartBlock ">
          [@customForm.select name="bilateralProjectID" label="" i18nkey="projects.addCoFundedProject.selectBilateralProject" listName="bilateralProjects" keyFieldName="id"  displayFieldName="title" /]
        </div> 
        <p>[@s.text name="projects.addCoFundedProject.ifBilateralProjectIsNotListed" /] 
          [#if securityContext.canAddBilateralProject()]<a href="[@s.url namespace="/planning" action='addNewBilateralProject'/]">[@s.text name="preplanning.projects.addBilateralProject" /]</a>[/#if]
        </p>
        <br />
        [#-- Core Projects list --]
        <div id="" class="fullPartBlock ">
          [@customForm.select name="coreProjectID" label="" i18nkey="projects.addCoFundedProject.selectCoreProject" listName="coreProjects" keyFieldName="id"  displayFieldName="title" /]
        </div>
        <br />
        [#-- Add project --]
        <div class="fullPartBlock right">
          [@s.submit type="button" name="save"][@s.text name="projects.addCoFundedProject.create" /][/@s.submit]
        </div>
      [/@s.form]
    </div>
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]