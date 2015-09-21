[#ftl]
[#assign title = "Summaries Section" /]
[#assign globalLibs = ["jquery", "noty"] /]
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
    <h1 class="contentTitle">Summaries Section</h1>
    <div class="summariesButtons clearfix">
      <div id="projects" class="summariesSection current"><span></span><a href="">Projects</a></div>
      <div id="partners" class="summariesSection"><span></span><a href="">Partners</a></div>
      <div id="deliverables" class="summariesSection"><span></span><a href="">Deliverables</a></div>
      <div id="budget" class="summariesSection"><span></span><a href="">Budget</a></div>
    </div>
    <div class="summariesContent borderBox">
      <form action="">
      <h6>What Phase of report would you like to generate</h6>
      <div class="summariesOption">
        <input type="radio" name="phase" id="planning" value="planning" checked="checked"/>
        <label for="planning">Planning</label>
      </div>
      <div class="summariesOption">
        <input type="radio" name="phase" id="reporting" value="reporting" disabled="disabled"/>
        <label for="reporting">Reporting</label>
      </div>
      
      <h6>What kind of report would you like to generate?</h6>
      <div class="summariesOptions">
        <div id="projects-contentOptions">
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="projectsPartners" value="projectsPartners" />
            <label for="projectsPartners">List of all projects and their leading partners <span>XLS</span></label>
          </div>
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="projectPortfolio" value="projects" />
            <label for="projectPortfolio">Project portfolio <span>PDF</span></label>
            <div class="extraOptions">
              <select name="" id=""></select>
            </div>
          </div>
        </div>
        <div id="partners-contentOptions" style="display:none">
          Soon
        </div>
        <div id="deliverables-contentOptions" style="display:none">
          Soon
        </div>
        <div id="budget-contentOptions" style="display:none">
          <div class="summariesOption">
            <input type="radio" name="formOptions" id="powb" value="powb" />
            <label for="powb">POWB Report <span>PDF</span></label>
          </div>
        </div>
      </div>
      <br />
      <a class="addButton" href="#">Generate</a>
      </form>
    </div> 
  </article>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]