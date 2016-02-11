[#ftl]
[#import "/WEB-INF/global/macros/utils.ftl" as utilities/]
[#-- This macro is being used in projectsListPreplanning.ftl and projectsListPlanning.ftl The idea is to represent a table with specific information about projects --]
[#macro projectsList projects owned=true canValidate=false isPlanning=false namespace="/" tableID="defaultTable"]
  <table class="projectsHome" id="home">
	  <thead>
	    <tr>
	      <th id="ids">[@s.text name="preplanning.projects.projectids" /]</th>
	      <th id="projectTitles" >[@s.text name="preplanning.projects.projectTitles" /]</th>
	      <th id="projectType" >[@s.text name="preplanning.projects.projectType" /]</th>
	    </tr>
	  </thead>
    <tbody>
      [#list projects as project]
        [#assign projectURL][@s.url namespace=namespace action='description' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url][/#assign]
    		<tr>  		  
    		  [#-- ID --]
          <td class="projectId">
            <a href="${projectURL}">P${project.id}</a>
          </td>
          [#-- Project Title --]
          <td class="left"> 
            [#if project.title?has_content]
              <a href="${projectURL}" title="${project.title}">[#if project.title?length < 120] ${project.title}</a> [#else] [@utilities.wordCutter string=project.title maxPos=120 /]...</a> [/#if]
            [#else]
              <a href="${projectURL}">[@s.text name="preplanning.projects.title.none" /]</a>
            [/#if]
          </td>
          [#-- Project type --]
          <td>
          <center>[@s.text name="${project.type}" /]</center>
          </td>
        </tr>  
      [/#list]
	  </tbody>
	</table>

[/#macro]