[#ftl]
[#assign title = "Welcome to CCAFS Activity Planning" /]
[#assign globalLibs = ["jquery"] /]
[#assign currentSection = "reporting" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<article>
  <div class="content">
    <h1>[@s.text name="reporting.introduction.title" /]</h1>
    
    <div id="explanatoryNotes">
      
      <ol>
        <li>Four kinds of reports are required (where applicable):</li>
        <ul>
          <li>Report based on Center Annual Activity Plans - deadline <span class="red">31st January</span> of each year.</li>
          <li>Report for Theme Leaders - deadline <span class="red">15th February</span> of each year.</li>
          <li>Report for Regional Program Leaders - deadline <span class="red">15th February</span> of each year.</li>
          <li>Report for Coordinating Unit - deadline <span class="red">24th February</span> of each year.</li>
        </ul>      
        <li>Three levels of funding are recognised for annual Activity Plans, each with their specific reporting requirements (based on total budget from Fund Council, bilateral funding and other funding sources):</li>
        <ul>
          <li>Small: < US$1.5 million/annum.</li>
          <li>Medium: US$1.5 million/annum - US$3 million/annum.</li>
          <li>Large: > US$3 million/annum.</li>
        </ul>
        <li>The level of detail require for different reporting components is as follows:</li>
        <table id="reportComponent">
          <thead>
            <tr>
              <th>Report component</th>
              <th>Small</th>
              <th>Medium</th>
              <th>Large</th>
            </tr>
          </thead>
          <tbody>
           <tr>
             <td>Number of outcomes</td>
             <td>At least 1 every 3rd year</td>
             <td>At least 1 every 2nd year</td>
             <td>At least 1 per year</td>
           </tr>
           <tr>
            <td>Impact studies</td>
            <td>1 every 5 years</td>
             <td>1 every 4 years</td>
             <td>1 every 3 years</td>
           </tr>
           <tr>
             <td>Summary of activities</td>
             <td>Half page</td>
             <td>1 page</td>
             <td>2 pages</td>
           </tr>
           <tr>
             <td>Case studies</td>
             <td>2 per year</td>
             <td>3 per year</td>
             <td>4 per year</td>
           </tr>
          </tbody>
        </table>
      </ol>
     </div> <!-- End explanatory notes -->  
    <div class="fullBlock"></div>
    <p>
      <h6>[@s.text name="reporting.introduction.start" /] <a class="popup-link" href="[@s.url action='activities' /]">[@s.text name="reporting.introduction.followLink" /]</a></h6>
    </p>
    <br>
  </div>
</article>
[#include "/WEB-INF/global/pages/footer.ftl"]