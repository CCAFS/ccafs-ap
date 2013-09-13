[#ftl]
[#assign title = "Activity Information" /]
[#assign globalLibs = ["jquery", "googleAPI", "googleMaps"] /]
[#assign customJS = ["${baseUrl}/js/home/activity.js"] /]
[#assign customCSS = ["", ""] /]

[#include "/WEB-INF/global/pages/header.ftl" /]

<section class="content activityInformation">
  <article class="content">
    [#-- Hidden values --]
    <input type="hidden" id="activityID" value="${activity.id}" />
    <input type="hidden" id="noCountriesMessage" value="[@s.text name="home.activity.noCountry" /]" />
    <input type="hidden" id="noOtherLocationsMessage" value="[@s.text name="home.activity.noOtherLocation" /]" />
    
    <h1>${activity.leader.acronym} - [@s.text name="home.activity" /] ${activity.id}</h1>
    <div id="activityTitle" class="fullBlock">
      <h6>[@s.text name="home.activity.title" /]</h6>
      <p>${activity.title}</p>
    </div>
    
    <div id="activityDescription" class="fullBlock">
      <h6>[@s.text name="home.activity.description" /]</h6>
      <p>${activity.description}</p>
    </div>
    
    <table id="generalInformation"  class="fullBlock">
      <tbody>
        <tr>
          <td class="title">[@s.text name="home.activity.startDate" /]</td>
          <td>${activity.startDate?string("MM/dd/yyyy")}</td>
          <td class="title">[@s.text name="home.activity.budget" /]</td>
          <td>${activity.budget.usd}</td>
        </tr>
        <tr>
          <td class="title">[@s.text name="home.activity.endDate" /]</td>
          <td>${activity.endDate?string("MM/dd/yyyy")}</td>
          <td class="title">[@s.text name="home.activity.milestone" /]</td>
          <td>
            <a class="popup" href="[@s.url action='milestone'][@s.param name='${milestoneRequestParameter}']${activity.milestone.id}[/@s.param][/@s.url]">
              ${activity.milestone.code}
            </a>
          </td>
        </tr>
        [#if activity.contactPersons??]
          <tr>
            <td class="title">[@s.text name="home.activity.contactPerson" /]</td>
            <td colspan="3">
              [#if contactPersons?has_content]
                 ${activity.contactPersons[0].name}
                (<a id="contactEmail" href="mailto: ${activity.contactPersons[0].email} ">${activity.contactPersons[0].email}</a>)
                [#if activity.contactPersons?size > 1] <a id="viewMoreContacts" href="">, [@s.text name="home.activity.contactPerson.showTable" /]</a> [/#if]
              [#else]
                [@s.text name="home.activity.contactPerson.none" /]
              [/#if]
            </td>        
          </tr>
        [/#if]
      </tbody>
    </table>
    
    <div id="contactPersons" title="[@s.text name="home.activity.contactPerson.table.title" /]">
      <table id="contactPersonsTable">
        <thead>
          <tr>
            <th>[@s.text name="home.activity.contactPerson.table.name" /]</th>
            <th>[@s.text name="home.activity.contactPerson.table.email" /]</th>
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
    
    [#-- Objectives --]
    [#if activity.objectives?has_content]
      <div id="objectives" class="fullBlock">
        <h6>[@s.text name="home.activity.objectives" /]</h6>
        <ol>
        [#list activity.objectives as objective]    
          <li>${objective.description}</li>
        [/#list]
        </ol>
      </div>
    [/#if]
    
    [#-- Gender integration --]
    [#if activity.genderIntegrationsDescription?has_content]
      <div id="genderIntegration" class="fullBlock">
        <h6>[@s.text name="home.activity.genderIntegrationDescription" /]</h6>
        <p>${activity.genderIntegrationsDescription}</p>
      </div>
    [/#if]
    
    [#-- Deliverables --]
    [#if activity.deliverables?has_content]
      <div id="deliverables" class="fullBlock">
        <h6>[@s.text name="home.activity.deliverables" /]</h6>
        <ol>
        [#list activity.deliverables as deliverable]    
          <li>
            <div class="fullBlock name">${deliverable.description}</div>
            <div class="halfPartBlock type">
              <span class="label"> [@s.text name="home.activity.deliverables.type" /] </span>
              <span class="value"> ${deliverable.type.name} </span>
            </div>
            <div class="halfPartBlock year">
              <span class="label"> [@s.text name="home.activity.deliverables.year" /] </span>
              <span class="value"> ${deliverable.year?c} </span>
            </div>
            [#if deliverable.fileName?has_content]
              <div class="fullBlock name">
                <span class="label"> [@s.text name="home.activity.deliverables.file" /] </span>
                <span class="value"> 
                  <a href="${deliverable.fileName}" target="_blank">${deliverable.fileName}</a>
                </span>
              </div>
            [/#if]
          </li>          
        [/#list]
        </ol>    
      </div>
    [/#if]
    
    [#-- Partners --]
    [#if activity.activityPartners?has_content]
      <div id="partners" class="fullBlock">
        <h6>[@s.text name="home.activity.partners" /]</h6>
        <ol>
        [#list activity.activityPartners as activityPartner]    
          <li>
            <div class="fullBlock name">${activityPartner.partner.name}</div>
            <div class="halfPartBlock type">
              <span class="label"> [@s.text name="home.activity.partner.acronym" /] </span>
              [#if activityPartner.partner.acronym?has_content]
                <span class="value"> ${activityPartner.partner.acronym} </span>
              [/#if]
            </div>
            <div class="halfPartBlock type">
              <span class="label"> [@s.text name="home.activity.partner.country" /] </span>
              [#if activityPartner.partner.country?has_content]
                <span class="value"> ${activityPartner.partner.country.name} </span>
              [/#if]            
            </div>
            <div class="halfPartBlock type">
              <span class="label"> [@s.text name="home.activity.partner.contactName" /] </span>
              [#if activityPartner.contactName?has_content]
                <span class="value"> ${activityPartner.contactName} </span>
              [/#if]
            </div>
            <div class="halfPartBlock type">
              <span class="label"> [@s.text name="home.activity.partner.contactEmail" /] </span>
              [#if activityPartner.contactEmail?has_content]
                <span class="value"> ${activityPartner.contactEmail} </span>
              [/#if]
            </div>
          </li>
        [/#list]
        </ol>    
      </div>
    [/#if]
    
    [#-- Country locations --]
    <div class="halfPartBlock">
      <h6>[@s.text name="home.activity.countryLocation" /]</h6>
      [#if activity.global]
        <p id="countriesMessage">The activity is global</p>
      [#else]        
        <p id="countriesMessage"></p>        
      [/#if]      
      <div id="country_locations" ></div>
    </div>
    
    [#-- Benchmark sites and other locations --]
    <div class="halfPartBlock">
      <h6>[@s.text name="home.activity.otherLocation" /]</h6>
      <p id="otherLocationMessage"></p>
      <div id="other_locations"></div>
    </div>
    
    [#-- Keywords --]
    [#if activity.keywords?has_content]
      <div id="keywords" class="fullBlock">
        <h6>[@s.text name="home.activity.keywords" /]</h6>
        [#list activity.keywords as activityKeyword]    
          [#if activityKeyword.keyword?has_content]
            <span class="thirdPartBlock">${activityKeyword.keyword.name?cap_first}</span>
          [#else]
            <span class="thirdPartBlock">${activityKeyword.other?cap_first}</span>
          [/#if]
        [/#list]        
      </div>      
    [/#if]
    
    [#-- Resources --]
    [#if activity.resources?has_content]
      <div id="resources" class="fullBlock">
        <h6>[@s.text name="home.activity.resources" /]</h6>
        <ol>
        [#list activity.resources as resource]    
          <li>${resource.name}</li>
        [/#list]
        </ol>
      </div>      
    [/#if]
      
  </article>
  </section>
  
[#include "/WEB-INF/global/pages/footer.ftl"]