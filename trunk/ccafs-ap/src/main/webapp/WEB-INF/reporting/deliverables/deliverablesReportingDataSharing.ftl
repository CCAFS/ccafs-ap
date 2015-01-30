[#ftl]
[#assign title = "Activity deliverables Data Sharing Report" /]
[#assign globalLibs = ["jquery", "noty", "dropzone"] /]
[#assign customJS = ["${baseUrl}/js/reporting/deliverables/deliverablesReportingDataSharing.js" ,"${baseUrl}/js/global/utils.js"] /]
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
  
  [@s.form action="deliverablesData" enctype="multipart/form-data"]
  <article class="halfContent">
    [#include "/WEB-INF/reporting/deliverables/deliverablesReportingSubMenu.ftl" /]
    <h1 class="contentTitle">
      [@s.text name="reporting.activityDeliverables.deliverable" /] - ${deliverableID}
    </h1> 
    [#-- Deliverable file List --]
    <h6>[@s.text name="reporting.activityDeliverables.dataSharing.deliverableFiles" /]</h6>
    <div id="filesUploaded">
      <ul>
       [#if deliverable.files?has_content]
        [#list deliverable.files as file] 
         <li class="fileUploaded">
           <input class="fileID" name="deliverable.files[${file_index}].id" type="hidden" value="${file.id}">
           <input class="fileHosted" name="deliverable.files[${file_index}].hosted" type="hidden" value="${file.hosted}">
           <input class="fileLink" name="deliverable.files[${file_index}].link" type="hidden" value="">
           <div class="fileName">${file.name!file.link}</div>
           <div class="fileFormat">${file.hosted}</div>
           <div class="fileSize">[#if file.size > 0]${(file.size/1024)?string("0.00")} KB[#else] <span title="Unknown size">- -</span> [/#if]</div>
           <img class="removeInput" src="${baseUrl}/images/global/icon-remove.png" alt="Remove"/>
         </li>
        [/#list]
       </ul>
       [#else]
        </ul>
         <p class="text">Use the following options to upload deliverable files</p>
       [/#if] 
    </div>
    
    
    [#-- Deliverable options to upload --]
    <h6>[@s.text name="reporting.activityDeliverables.dataSharing.chooseOptions" /]</h6> 
    <div id="dataSharingOptions">
      <label for="option-1">
          <input id="option-1" type="radio" name="sharingOption" value="Externally" >
          [@s.text name="reporting.activityDeliverables.dataSharing.hostedInstitutional" /]
          <span class="quote">[@s.text name="reporting.activityDeliverables.dataSharing.hostedInstitutional.help" /]</span>
          
          <div id="fileURL" class="fullBlock uploadBlock" style="display:none">
            [@customForm.input name="linkExternally" type="text" i18nkey="reporting.activityDeliverables.filename" value="http://"/]
            <div id="addFileURL-external" class="addButton addFileURL">[@s.text name="reporting.activityDeliverables.dataSharing.addURL" /]</div>
          </div> 
      </label> 
      
      <label for="option-2">
          <input id="option-2" type="radio" name="sharingOption" value="To download" >
          [@s.text name="reporting.activityDeliverables.dataSharing.fileGreater" /]
          <span class="quote">[@s.text name="reporting.activityDeliverables.dataSharing.fileGreater.help" /]</span>
          
          <div id="fileURL" class="fullBlock uploadBlock" style="display:none">
            [@customForm.input name="linkLocally" type="text" i18nkey="reporting.activityDeliverables.filename" value="http://" /]
            <div id="addFileURL-ccafs" class="addButton addFileURL">[@s.text name="reporting.activityDeliverables.dataSharing.addURL" /]</div>
          </div> 
      </label> 
      
      <label for="option-3">
          <input id="option-3" type="radio" name="sharingOption" value="Locally" >
          [@s.text name="reporting.activityDeliverables.dataSharing.fileSmaller" /]
          <span class="quote">[@s.text name="reporting.activityDeliverables.dataSharing.fileSmaller.help" /]</span>
          
          [#-- This is used for run a JQuery (dropzone) plugin to drag and drop deliverables files--]
          <div id="dragAndDrop" class="dropzone uploadBlock" style="display:none">
            <div class="fallback"> 
              <div id="addFileInput" class="addButton">[@s.text name="reporting.activityDeliverables.dataSharing.addFile" /]</div>
            </div>
          </div>
      </label> 
    </div>  
     
   
        
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activityID}" />
    <input name="deliverableID" type="hidden" value="${deliverableID?c}" />
    
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
   <input name="filesUploaded" type="file" />
 </div>
 
 [#-- File uploaded template --]
 <ul>
   <li id="deliverableFileTemplate" class="fileUploaded" style="display:none">
     <input class="fileID" name="" type="hidden">
     <input class="fileHosted" name="" type="hidden">
     <input class="fileLink" name="" type="hidden">
     <div class="fileName">filename</div>
     <div class="fileFormat">- -</div>
     <div class="fileSize">- -</div>
     <img class="removeInput" src="${baseUrl}/images/global/icon-remove.png" alt="Remove"/>
   </li>
 </ul>
 [#-- Remove deliverable files modal  template --]
<div id="removeDeliverableFiles" style="display:none" title="Modal title"> 
 Modal content
</div> 

[#include "/WEB-INF/global/pages/footer.ftl"]