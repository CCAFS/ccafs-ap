[#ftl]
<div id="deliverable-ranking" class="clearfix">
  [#if !editable && canEdit]
    <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-ranking">[@s.text name="form.buttons.edit" /]</a></div>
  [#else]
    [#if canEdit && !newProject]
      <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]#deliverable-ranking">[@s.text name="form.buttons.unedit" /]</a></div>
    [/#if]
  [/#if]
  <h1 class="contentTitle">[@s.text name="reporting.projectDeliverable.rankingTitle" /] </h1>
  [#-- Ranking --]
  <div class="fullBlock">
  <h6>Ranking</h6>
    <table id="rankingTable" class="default">
      <tbody>
        <tr>
          <td class="key">[@s.text name="reporting.projectDeliverable.ranking.addressGenderSocial" /]</td> 
          <td class="value">[@deliverableTemplate.rank name="${params.deliverable.name}.addressGenderSocial" editable=editable/]</td>
        </tr>
        <tr>
          <td class="key">[@s.text name="reporting.projectDeliverable.ranking.potencialActualContribution" /]</td>
          <td class="value">[@deliverableTemplate.rank name="${params.deliverable.name}.potencialActualContribution" editable=editable/]</td>
        </tr>
        <tr>
          <td class="key">[@s.text name="reporting.projectDeliverable.ranking.levelSharedOwnership" /] [@s.text name="reporting.projectDeliverable.ranking.levelSharedOwnership.complement" /]</td> 
          <td class="value">[@deliverableTemplate.rank name="${params.deliverable.name}.levelSharedOwnership" editable=editable/]</td>
        </tr>
        <tr>
          <td class="key">[@s.text name="reporting.projectDeliverable.ranking.productImportance" /]</td> 
          <td class="value">[@deliverableTemplate.rank name="${params.deliverable.name}.productImportance" editable=editable/]</td>
        </tr>
      </tbody>
    </table>
  </div>
  [#-- Compliance check (Data products only) --]
  <div class="fullBlock requiredForType-10 requiredForType-11 requiredForType-10 requiredForType-12 requiredForType-13 requiredForType-37">
  <h6>[@s.text name="reporting.projectDeliverable.complianceTitle" /]</h6>
    <table id="dataCompliance" class="default">
      <tbody>
        <tr>
          <td class="key">[@s.text name="reporting.projectDeliverable.compliance.dataQualityAssurance" /]
            <div id="aditional-dataQualityAssurance" class="aditional fileUpload" style="display:none">
              [@customForm.input name="${params.deliverable.name}.dataQualityAssuranceUpload" type="file" className="upload" i18nkey="reporting.projectDeliverable.compliance.dataQualityAssurance.upload" editable=editable/]
            </div>
          </td> 
          <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.dataQualityAssurance" editable=editable/]</td>
        </tr>
        <tr>
          <td class="key">[@s.text name="reporting.projectDeliverable.compliance.dataDictionary" /]</td> 
          <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.dataDictionary" editable=editable/]</td>
        </tr>
        <tr>
          <td class="key">[@s.text name="reporting.projectDeliverable.compliance.toolsUsedDataCollection" /]
            <div id="aditional-toolsUsedDataCollection" class="aditional" style="display:none">
              [@customForm.textArea name="${params.deliverable.name}.toolsUsedDataCollectionLinks" i18nkey="reporting.projectDeliverable.compliance.toolsUsedDataCollection.links" editable=editable/]
            </div>
          </td> 
          <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.toolsUsedDataCollection" editable=editable/]</td>
        </tr>
      </tbody>
    </table>
  </div>
</div>