[#ftl]
<div id="deliverable-dissemination" class="clearfix">
  [#if !editable && canEdit]
    <div class="editButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#deliverable-dissemination">[@s.text name="form.buttons.edit" /]</a></div>
  [#else]
    [#if canEdit && !newProject]
      <div class="viewButton"><a href="[@s.url][@s.param name ="deliverableID"]${deliverable.id}[/@s.param][/@s.url]#deliverable-dissemination">[@s.text name="form.buttons.unedit" /]</a></div>
    [/#if]
  [/#if]
  <h1 class="contentTitle">[@s.text name="reporting.projectDeliverable.disseminationTitle" /] </h1>
  [#-- Is this deliverable disseminated --]
  <div class="fullBlock">
    <table id="alreadyDisseminated" class="default">
      <tbody>
        <tr>
          <td class="key">
            <p>[@s.text name="reporting.projectDeliverable.dissemination.alreadyDisseminated" /]</p>
            <div id="aditional-alreadyDisseminated"class="aditional" style="display:none">
              <p>[@s.text name="reporting.projectDeliverable.dissemination.alreadyDisseminated.description" /]</p><br />
              <div class="fullBlock">
                [@customForm.select name="${params.deliverable.name}.disseminationChannel" label=""  disabled=false i18nkey="reporting.projectDeliverable.disseminationChannel" listName="channels" keyFieldName="id"  displayFieldName="description" required=true editable=editable /]
              </div>
              <div class="fullBlock">
                [@customForm.input name="${params.deliverable.name}.disseminationUrl" className="deliverableTitle" i18nkey="reporting.projectDeliverable.disseminationUrl" required=true editable=editable /]
              </div>
            </div><!-- End aditional-alreadyDisseminated -->
          </td> 
          <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.alreadyDisseminated" editable=editable/]</td>
        </tr>
      </tbody>
    </table>
  </div>
  [#-- Is there an Open Access restriction --]
  <div class="fullBlock">
    <table id="openAccessRestriction" class="default">
      <tbody>
        <tr>
          <td class="key"">
            <p>[@s.text name="reporting.projectDeliverable.dissemination.openAccessRestriction" /]</p>
            <div id="aditional-openAccessRestriction" class="aditional" style="display:block">
              <h6>[@s.text name="reporting.projectDeliverable.dissemination.openAccessRestriction.title" /]</h6>
              <div class="fullBlock">
                [#if editable]
                [#-- Intellectual Property Rights --]
                <div class="openAccessRestrictionOption">
                  <input id="intellectualProperty" type="radio" name="${params.deliverable.name}.openAccessRestrictionOption" value="1" />
                  <label for="intellectualProperty">[@s.text name="reporting.projectDeliverable.dissemination.intellectualProperty" /]</label>
                </div>
                [#-- Limited Exclusivity Agreements --]
                <div class="openAccessRestrictionOption">
                  <input id="limitedExclusivity" type="radio" name="${params.deliverable.name}.openAccessRestrictionOption" value="2" /> 
                  <label for="limitedExclusivity">[@s.text name="reporting.projectDeliverable.dissemination.limitedExclusivity" /]</label>
                </div>
                [#-- Restricted Use Agreement - Restricted access --]
                <div class="openAccessRestrictionOption">
                  <input id="restrictedAccess" type="radio" name="${params.deliverable.name}.openAccessRestrictionOption" value="3" />
                  <label for="restrictedAccess">[@s.text name="reporting.projectDeliverable.dissemination.restrictedAccess" /]</label>
                </div>
                [#-- Effective Date Restriction - embargoed periods --]
                <div class="openAccessRestrictionOption">
                  <input id="embargoedPeriods" type="radio" name="${params.deliverable.name}.openAccessRestrictionOption" value="4" />
                  <label for="embargoedPeriods">[@s.text name="reporting.projectDeliverable.dissemination.embargoedPeriod" /]</label>
                </div>
                [#else]
                  TODO: Option selected [@s.property value="${params.deliverable.name}.openAccessRestrictionOption"/]
                [/#if]
              </div>
              [#-- Periods --]
              <div class="fullBlock">
                <div id="period-restrictedAccess" class="halfPartBlock" style="display:none">
                  [@customForm.input name="${params.deliverable.name}.restrictedAccessDate" className="period" type="text" i18nkey="reporting.projectDeliverable.dissemination.restrictedAccessDate" editable=editable/]
                </div>
                <div id="period-embargoedPeriods" class="halfPartBlock" style="display:none">
                  [@customForm.input name="${params.deliverable.name}.embargoedPeriodDate" className="period" type="text" i18nkey="reporting.projectDeliverable.dissemination.embargoedPeriodDate" editable=editable/]
                </div>
              </div>
            </div><!-- End aditional-openAccessRestriction -->
          </td> 
          <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.openAccessRestriction" editable=editable inverse=true/]</td>
        </tr>
      </tbody>
    </table>
  </div>
</div>