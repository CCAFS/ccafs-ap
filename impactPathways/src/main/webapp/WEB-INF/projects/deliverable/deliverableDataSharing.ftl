[#ftl]
<div id="deliverable-dataSharing" class="clearfix">
  <h1 class="contentTitle">[@s.text name="planning.projectDeliverable.dataSharing" /] </h1> 
  <div class="fullBlock"> 
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
       <p class="text" style="display:none">Use the following options to upload here the deliverable files or links</p>
       [#else]
       </ul>
       <p class="text">Use the following options to upload here the deliverable files or links</p>
       [/#if] 
    </div>
    
    
    [#-- Deliverable options to upload --]
    <h6>[@s.text name="reporting.activityDeliverables.dataSharing.chooseOptions" /]</h6> 
    <div id="dataSharingOptions">
      [#-- Option #1--]
      <label for="option-1">
        <input id="option-1" type="radio" name="sharingOption" value="Externally" >
        [@s.text name="reporting.activityDeliverables.dataSharing.hostedInstitutional" /]
        <span class="quote">[@s.text name="reporting.activityDeliverables.dataSharing.hostedInstitutional.help" /]</span>
      </label> 
      <div id="fileURL" class="fullBlock uploadBlock" style="display:none">
        [@customForm.input name="linkExternally" type="text" i18nkey="reporting.activityDeliverables.filename" value="http://"/]
        <div id="addFileURL-external" class="addButton addFileURL">[@s.text name="reporting.activityDeliverables.dataSharing.addURL" /]</div>
      </div> 
      [#-- Option #2--]
      <label for="option-2">
        <input id="option-2" type="radio" name="sharingOption" value="To download" >
        [@s.text name="reporting.activityDeliverables.dataSharing.fileGreater" /]
        <span class="quote">[@s.text name="reporting.activityDeliverables.dataSharing.fileGreater.help" /]</span>
      </label>
      <div id="fileURL" class="fullBlock uploadBlock" style="display:none">
        [@customForm.input name="linkLocally" type="text" i18nkey="reporting.activityDeliverables.filename" value="http://" /]
        <div id="addFileURL-ccafs" class="addButton addFileURL">[@s.text name="reporting.activityDeliverables.dataSharing.addURL" /]</div>
      </div>
      [#-- Option #3--]
      <label for="option-3">
        <input id="option-3" type="radio" name="sharingOption" value="Locally" >
        [@s.text name="reporting.activityDeliverables.dataSharing.fileSmaller" /]
        <span class="quote">[@s.text name="reporting.activityDeliverables.dataSharing.fileSmaller.help" /]</span>
      </label> 
      [#-- This is used for run a JQuery (dropzone) plugin to drag and drop deliverables files--]
      <div id="dragAndDrop" class="dropzone uploadBlock" style="display:none">
        <div class="fallback"> 
          <div id="addFileInput" class="addButton">[@s.text name="reporting.activityDeliverables.dataSharing.addFile" /]</div>
        </div>
      </div>
    </div>
  </div>
</div>