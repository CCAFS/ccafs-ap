[#ftl]
[#assign title = "Summaries Section" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/summaries/boardSummaries.js"] /]
[#assign customCSS = [] /]
[#assign currentSection = "summaries" /]

[#assign breadCrumb = [
  {"label":"summaries", "nameSpace":"summaries", "action":"board"}
]/]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <article id="" class="fullBlock" > 
    <br /> 
    <div class="summariesButtons clearfix">
      <div id="projects" class="summariesSection current"><span></span><a href="">Projects</a></div>
      <div id="partners" class="summariesSection"><span></span><a href="">Partners</a></div>
      <div id="deliverables" class="summariesSection"><span></span><a href="">Deliverables</a></div>
      <div id="budget" class="summariesSection"><span></span><a href="">Budget</a></div>
    </div>
    <div class="summariesContent borderBox">
      <div class="loading" style="display:none"></div>
      <form action="">
      <h6>Select the project research cycle:</h6>
      <div class="summariesOption">
        <input type="radio" name="phase" id="planning" value="planning" checked="checked"/>
        <label for="planning">Planning</label>
      </div>
      <div class="summariesOption">
        <input type="radio" name="phase" id="reporting" value="reporting" disabled="disabled"/>
        <label for="reporting">Reporting</label>
      </div>
      
      <h6>Select a type of report:</h6>
      <div class="summariesOptions">
        [#-- Projects reports --]
        <div id="projects-contentOptions">
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="projectPartnersSummary" value="projectPartnersSummary"/>
            <label for="projectPartnersSummary">List of all projects and their leading institution  <span>XLSx</span></label>
            <p class="description">Here you find a list of all projects entered in P&R with basic information; e.g. title, description, start/end date, leader, etc. </p>
          </div>
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="projectPortfolio" value="project"/>
            <label for="projectPortfolio">Full Project Report <span>PDF</span></label>
            <p class="description">The “Full Project Report comprises of detail information of all sections completed in P&R; i.e. General Information, Outcomes, Outputs, Activities and Budget. Submission status can be found at the header. </p>
            <div class="extraOptions" style="display:none"> 
              [@customForm.select name="projectID" label="" i18nkey="" listName="allProjects" keyFieldName="id" displayFieldName="composedName" className="" disabled=true/]
            </div>
          </div>
        </div>
        [#-- Partners reports --]
        <div id="partners-contentOptions" style="display:none">
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="leadProjectInstitutionsSummary" value="leadProjectInstitutionsSummary"/>
            <label for="leadProjectInstitutionsSummary">Partners and lead projects  <span>XLSx</span></label>
            <p class="description">List of partners and the projects they are currently leading. </p>
          </div>
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="partnersWorkingWithProjects" value="" disabled="disabled"/>
            <label for="partnersWorkingWithProjects">Partners and projects they relate  <span>XLSx</span></label>
            <p class="description">List of partners and projects to which they are currently contributing. </p>
          </div>
        </div>
        [#-- Deliverables reports --]
        <div id="deliverables-contentOptions" style="display:none">
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="expectedDeliverables" value="expectedDeliverables"/>
            <label for="expectedDeliverables">Expected deliverables <span>XLSx</span></label>
            <p class="description">List of deliverables indicating: Project, Flagship, Region, Title, MOG, Year, Type/Sub-Type and responsible partners </p>
          </div>
        </div>
        [#-- Budget reports --]
        <div id="budget-contentOptions" style="display:none">
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="powb" value="powb" disabled="disabled"/>
            <label for="powb">POWB Report <span>XLSx</span></label>
            <p class="description"> </p>
          </div>
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="powbMOG" value="powbMOG" disabled="disabled"/>
            <label for="powbMOG">POWB Report by MOGs <span>XLSx</span></label>
            <p class="description"> </p>
          </div>
        </div>
      </div>
      <br />
      <a id="generateReport" target="_blank" class="addButton" style="display:none" href="#">Generate</a>
      </form>
    </div> 
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]