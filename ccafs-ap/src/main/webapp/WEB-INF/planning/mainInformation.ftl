[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/planning/mainInformation.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "mainInformation" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  
  [@s.form action="mainInformation"]  
  <article class="halfContent" id="#mainInformation">
    <h1 class="contentTitle">
      ${currentUser.leader.acronym} - [@s.text name="planning.mainInformation.activity" /] ${activity.id}
    </h1>
    
    [#-- Activity identifier --]
    <input name="activityID" value="${activity.id}" type="hidden"/>
    [#-- Budget identifier --]
    <input name="activity.budget.id" value="${activity.budget.id}" type="hidden"/>
    [#-- Budget 'No funds' text --]
    <input id="activity.budget.noFunds" value="[@s.text name="planning.mainInformation.budget.noFunds" /] " type="hidden"/>
    
    
    [#-- Hidden values used by js --]
    <input id="minDateValue" value="${startYear?c}-01-01" type="hidden"/>
    <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/>
    
    [#-- Commissioned and continuous --]
    [#if activity.continuousActivity?has_content || activity.commissioned]
      <div class="fullblock">
        <div class="halfPartBlock continuation">
          [#if activity.continuousActivity?has_content]
            [@s.text name="planning.mainInformation.continuationActivity" /] 
            <a href="[@s.url action='activity' namespace="/"][@s.param name='${publicActivtyRequestParameter}']${activity.continuousActivity.id}[/@s.param][/@s.url]" target="_blank"> 
              [@s.text name="planning.mainInformation.activity" /] ${activity.continuousActivity.id} 
            </a>
          [/#if]
        </div>
        <div class="halfPartBlock commisioned">
          [#if activity.commissioned]
            [@s.text name="planning.mainInformation.commissioned" /]
          [/#if]
        </div>
      </div>
    [/#if]
    
    [#-- Title --]
    <div class="fullBlock">
      [@customForm.textArea name="activity.title"  i18nkey="planning.mainInformation.title" disabled=true /]
    </div>
    
    [#-- Description --]
    <div class="fullBlock">
      [@customForm.textArea name="activity.description" i18nkey="planning.mainInformation.descripition" /]
    </div>
    
    [#-- Milestones --]
    <div class="halfPartBlock">
      [@customForm.select name="activity.milestone" label="" i18nkey="planning.mainInformation.milestone" listName="milestones" keyFieldName="id"  displayFieldName="code" value="${activity.milestone.id}" className="milestones" /]
    </div>
    
    [#-- Logframe link --]
    <div class="halfPartBlock">
      <a href="../documents/Logframe_2013-2015.pdf" target="_blank">view logframe </a> 
    </div>
    
    [#-- Budget --]
    <div class="halfPartBlock">
      [@customForm.input name="activity.budget.usd" type="text" i18nkey="planning.mainInformation.budget" /]
    </div>
    
    [#-- Budget Percentages --]
    <div class="halfPartBlock">
      <div class="halfPartBlock">
        [@customForm.select name="activity.budget.cgFund" label="" i18nkey="planning.mainInformation.budget.cgfunds" listName="budgetPercentages" keyFieldName="id"  displayFieldName="percentage" value="activity.budget.cgFund.id" /]              
      </div>
      <div class="halfPartBlock">
        [@customForm.select name="activity.budget.bilateral" label="" i18nkey="planning.mainInformation.budget.bilateral" listName="budgetPercentages" keyFieldName="id"  displayFieldName="percentage" value="activity.budget.bilateral.id" /]
      </div>
    </div>
    
    [#-- Start Date --]
    <div class="halfPartBlock">
      [@customForm.input name="activity.startDate" type="text" i18nkey="planning.mainInformation.startDate" /]
    </div>
    
    [#-- End Date --]
    <div class="halfPartBlock">
      [@customForm.input name="activity.endDate" type="text" i18nkey="planning.mainInformation.endDate" /]
    </div>
    
    [#-- Gender integration --]
    <div id="gender">
      <div class="fullBlock">
        [@customForm.radioButtonGroup label="Gender Integration" name="genderIntegrationOption" listName="genderOptions" value="${hasGender?string('1', '0')}" /]
      </div>
      <div class="fullBlock genderIntegrationsDescription">
        [@customForm.textArea name="activity.genderIntegrationsDescription" i18nkey="planning.mainInformation.genderIntegrationDescription" required=true /]
      </div>
    </div>
        
    <fieldset class="fullBlock">
        <legend>Contact persons</legend>
        <div id="contactPersonBlock">
          [#if activity.contactPersons?has_content]
            [#-- If there is contact persons show the list --]
            [#list activity.contactPersons as contactPerson]
              
              <div id="contactPerson-${contactPerson_index}" class="contactPerson">
                [#-- Contact person id--]
                <input type="hidden" name="activity.contactPersons[${contactPerson_index}].id" value="${contactPerson.id}">
                
                [#-- Contact name --]
                <div class="halfPartBlock">
                  [@customForm.input name="activity.contactPersons[${contactPerson_index}].name" type="text" i18nkey="planning.mainInformation.contactName" /]
                </div>
                
                [#-- Contact email --]
                <div class="halfPartBlock">
                  [@customForm.input name="activity.contactPersons[${contactPerson_index}].email" type="text" i18nkey="planning.mainInformation.contactEmail" /]
                </div>
        
                [#-- Adding remove image --]
                <a href="#" >
                  <img src="${baseUrl}/images/global/icon-remove.png" class="removeContactPerson" />
                </a>
                
              </div>
            [/#list]
          [#else]
            <div class="contactPerson">
              [#-- Contact person id--]
              <input type="hidden" name="activity.contactPersons[0].id" value="-1">
              
              [#-- Contact person --]
              <div class="halfPartBlock">
                [@customForm.input name="activity.contactPersons[0].name" type="text" i18nkey="planning.mainInformation.contactName" value="" /]
              </div>
              
              [#-- Contact email --]
              <div class="halfPartBlock">
                [@customForm.input name="activity.contactPersons[0].email" type="text" i18nkey="planning.mainInformation.contactEmail" value="" /]
              </div>
              
              [#-- Adding remove image --]
              <a href="#" >
                <img src="${baseUrl}/images/global/icon-remove.png" class="removeContactPerson" />
              </a>
            </div>
          [/#if]
        </div>
    
    <div id="addContactPerson" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addContactPerson" >[@s.text name="planning.mainInformation.addContactPerson" /]</a>
    </div>
    </fieldset>
    
    
    [#-- Contact person template --]
    <div id="contactPersonTemplate" style="display:none;">
      [#-- Contact person id--]
      <input type="hidden" name="id" value="-1">
      
      [#-- Contact name --]
      <div class="halfPartBlock">
        [@customForm.input name="name" type="text" i18nkey="planning.mainInformation.contactName" /]
      </div>
      
      [#-- Contact email --]
      <div class="halfPartBlock">
        [@customForm.input name="email" type="text" i18nkey="planning.mainInformation.contactEmail" /]
      </div>

      [#-- Adding remove image --]
      <a href="#" >
        <img src="${baseUrl}/images/global/icon-remove.png" class="removeContactPerson" />
      </a>
      
    </div>
    
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="saveNext"][@s.text name="form.buttons.saveNext" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]