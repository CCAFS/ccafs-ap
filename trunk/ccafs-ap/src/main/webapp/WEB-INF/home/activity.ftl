[#ftl]
[#assign title = "Activity Information" /]
[#assign globalLibs = [""] /]
[#assign customJS = ["", ""] /]
[#assign customCSS = ["", ""] /]

[#include "/WEB-INF/global/pages/header.ftl" /]

<section class="content">
  <article class="content">
    <h1>${activity.leader.acronym} - [@s.text name="reporting.activityList.activity" /] ${activity.id}</h1>
    <div id="activityTitle" class="fullBlock">
      <h6>[@s.text name="reporting.activityStatus.title" /]</h6>
      <p>${activity.title}</p>
    </div>
    
    <div id="activityDescription" class="fullBlock">
      <h6>[@s.text name="reporting.activityStatus.description" /]</h6>
      <p>${activity.description}</p>
    </div>
    
    <table id="generalInformation"  class="fullBlock">
      <tbody>
        <tr>
          <td class="title">[@s.text name="reporting.activityStatus.startDate" /]</td>
          <td>${activity.startDate?string("MM/dd/yyyy")}</td>
          <td class="title">[@s.text name="reporting.activityStatus.budget" /]</td>
          <td>${activity.budget.usd}</td>
        </tr>
        <tr>
          <td class="title">[@s.text name="reporting.activityStatus.endDate" /]</td>
          <td>${activity.endDate?string("MM/dd/yyyy")}</td>
          <td class="title">[@s.text name="reporting.activityStatus.milestone" /]</td>
          <td>
            <a class="popup" href="[@s.url action='milestone'][@s.param name='${milestoneRequestParameter}']${activity.milestone.id}[/@s.param][/@s.url]">
              ${activity.milestone.code}
            </a>
          </td>
        </tr>
        [#if activity.contactPersons??]
          <tr>
            <td class="title">[@s.text name="reporting.activityStatus.contactPerson" /]</td>
            <td colspan="3">
               ${activity.contactPersons[0].name}
              (<a id="contactEmail" href="mailto: ${activity.contactPersons[0].email} ">${activity.contactPersons[0].email}</a>)
              [#if activity.contactPersons?size > 1] <a id="viewMoreContacts" href="">, [@s.text name="reporting.activityStatus.contactPerson.showTable" /]</a> [/#if]
            </td>        
          </tr>
        [/#if]
      </tbody>
    </table>
    
    [#if activity.genderIntegrationsDescription?has_content]
      <div id="genderIntegration" class="fullBlock">
        <h6>[@s.text name="reporting.activityStatus.genderIntegrationDescription" /]</h6>
        <p>${activity.genderIntegrationsDescription}</p>
      </div>
    [/#if]
    
    <div id="contactPersons" title="[@s.text name="reporting.activityStatus.contactPerson.table.title" /]">
      <table id="contactPersonsTable">
        <thead>
          <tr>
            <th>[@s.text name="reporting.activityStatus.contactPerson.table.name" /]</th>
            <th>[@s.text name="reporting.activityStatus.contactPerson.table.email" /]</th>
          </tr>
        </thead>
        <tbody>
          [#list activity.contactPersons as contactPerson]    
            <tr>
              <td>${contactPerson.name}</td>
              <td>${contactPerson.email}</td>
            </tr>
          [/#list]    
        </tbody>
      </table>
    </div>
    
    <div id="genderIntegration" class="fullBlock">
      <h6>[objectives][@s.text name="reporting.activityStatus.genderIntegrationDescription" /]</h6>
      <p>${activity.objectives[0].description}</p>
    </div>
    
    [#if activity.resources?has_content]
      <div id="genderIntegration" class="fullBlock">
        <h6>[@s.text name="reporting.activityStatus.genderIntegrationDescription" /]</h6>
        <p>${activity.resources[0].name}</p>
      </div>
    [/#if]
      
  </article>
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]