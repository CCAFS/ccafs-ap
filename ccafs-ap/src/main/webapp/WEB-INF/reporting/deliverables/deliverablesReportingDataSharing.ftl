[#ftl]
[#assign title = "Activity deliverables Data Sharing Report" /]
[#assign globalLibs = ["jquery", "noty", "dropzone"] /]
[#assign customJS = ["${baseUrl}/js/reporting/deliverables/deliverablesReportingDataSharing.js"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "dataSharing" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.activityDeliverables.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [@s.form action="deliverables"]
  <article class="halfContent">
    [#include "/WEB-INF/reporting/deliverables/deliverablesReportingSubMenu.ftl" /]
    <h1 class="contentTitle">
      [@s.text name="reporting.activityDeliverables.deliverable" /] - ${deliverableID}
    </h1> 
    
    <h6>[@s.text name="reporting.activityDeliverables.dataSharing.chooseOptions" /]</h6>
    
    <div id="dataSharingOptions">
      <label for="option-1">
          <input id="option-1" type="radio" name="dataSharingOptions" value="option1" >
          [@s.text name="reporting.activityDeliverables.dataSharing.hostedInstitutional" /]
          <span class="quote">[@s.text name="reporting.activityDeliverables.dataSharing.hostedInstitutional.help" /]</span>
      </label> 
      
      <label for="option-2">
          <input id="option-2" type="radio" name="dataSharingOptions" value="option2" checked>
          [@s.text name="reporting.activityDeliverables.dataSharing.fileGreater" /]
          <span class="quote">[@s.text name="reporting.activityDeliverables.dataSharing.fileGreater.help" /]</span>
      </label> 
      
      <label for="option-3">
          <input id="option-3" type="radio" name="dataSharingOptions" value="option3">
          [@s.text name="reporting.activityDeliverables.dataSharing.fileSmaller" /]
          <span class="quote">[@s.text name="reporting.activityDeliverables.dataSharing.fileSmaller.help" /]</span>
      </label> 
    </div>

    <h6>[@s.text name="reporting.activityDeliverables.dataSharing.uploadFiles" /]</h6>
    <div id="fileURL" class="fullBlock" style="display:none">
      [@customForm.input name="deliverable.fileURL" type="text" i18nkey="reporting.activityDeliverables.filename" /]
    </div> 
    <div id="ccafsFiles">
      [#-- This is used for run a JQuery (dropzone) plugin to drag and drop deliverables files--]
      <div id="dragAndDrop" class="dropzone">
        <div class="fallback"> 
          <div id="addFileInput">[@s.text name="reporting.activityDeliverables.dataSharing.addFile" /]</div>
        </div>
      </div>
      <br>
      <h6>[@s.text name="reporting.activityDeliverables.dataSharing.deliverableFiles" /]</h6>
      <div id="filesUploaded">
        <ul>
           <li id="deliberableFile-1" class="fileUploaded">
             <div class="fileName">Filename #1</div>
             <div class="fileFormat">PDF</div>
             <div class="fileSize">50 KB</div>
             <span>Remove</span>
           </li>
           
           <li id="deliberableFile-2" class="fileUploaded">
             <div class="fileName">Filename #2</div>
             <div class="fileFormat">PDF</div>
             <div class="fileSize">20 KB</div>
             <span>Remove</span>
           </li>
        </ul>
      </div>
    </div>

    
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activityID}" />
    <input name="deliverableID" type="hidden" value="${deliverable.id?c}" />
    
    [#if canSubmit]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]

    </article>
  [/@s.form]
 
  </section>

  [#-- File Input template --]
 <div id="fileInputTemplate" class="fileInput" style="display:none">
   <img class="removeInput" src="${baseUrl}/images/global/icon-remove.png" alt="Remove"> 
   <input name="file" type="file" multiple />
 </div>
 
 [#-- File uploaded template --]
 <li id="deliberableFileTemplate" class="fileUploaded" style="display:none">
   <div class="fileName">filename</div>
   <div class="fileFormat">format</div>
   <div class="fileSize">size</div>
   <span>Remove</span>
 </li>

[#include "/WEB-INF/global/pages/footer.ftl"]