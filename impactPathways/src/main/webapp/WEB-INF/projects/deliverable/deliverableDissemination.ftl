[#ftl]
<h1 class="contentTitle">[@s.text name="reporting.projectDeliverable.disseminationTitle" /] </h1> 
[#-- Is there an Open Access restriction --]
<div class="fullBlock">
  <table id="openAccessRestriction" class="default">
    <tbody>
      <tr>
        <td class="key""><p>[@s.text name="reporting.projectDeliverable.dissemination.openAccessRestriction" /]</p></td> 
        <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.dissemination.isOpenAccess" editable=editable inverse=true/]</td>
      </tr>
    </tbody>
  </table>
  <div id="aditional-isOpenAccess" class="aditional" style="display:${(deliverable.dissemination.isOpenAccess?string('none','block'))!'none'}">
    <h6>[@s.text name="reporting.projectDeliverable.dissemination.openAccessRestriction.title" /]</h6>
    <div class="fullBlock">
      [#if editable]
      [#-- Intellectual Property Rights --]
      <div class="openAccessRestrictionOption">
        <input id="intellectualProperty" type="radio" name="${params.deliverable.name}.dissemination.type" value="intellectualProperty" [#if (deliverable.dissemination.type == "intellectualProperty")!false]checked="checked"[/#if] />
        <label for="intellectualProperty">[@s.text name="reporting.projectDeliverable.dissemination.intellectualProperty" /]</label>
      </div>
      [#-- Limited Exclusivity Agreements --]
      <div class="openAccessRestrictionOption">
        <input id="limitedExclusivity" type="radio" name="${params.deliverable.name}.dissemination.type" value="limitedExclusivity" [#if (deliverable.dissemination.type == "limitedExclusivity")!false]checked="checked"[/#if] />
        <label for="limitedExclusivity">[@s.text name="reporting.projectDeliverable.dissemination.limitedExclusivity" /]</label>
      </div>
      [#-- Restricted Use Agreement - Restricted access --]
      <div class="openAccessRestrictionOption">
        <input id="restrictedAccess" type="radio" name="${params.deliverable.name}.dissemination.type" value="restrictedAccess" [#if (deliverable.dissemination.type == "restrictedAccess")!false]checked="checked"[/#if] />
        <label for="restrictedAccess">[@s.text name="reporting.projectDeliverable.dissemination.restrictedAccess" /]</label>
      </div>
      [#-- Effective Date Restriction - embargoed periods --]
      <div class="openAccessRestrictionOption">
        <input id="embargoedPeriods" type="radio" name="${params.deliverable.name}.dissemination.type" value="embargoedPeriods" [#if (deliverable.dissemination.type == "embargoedPeriods")!false]checked="checked"[/#if] />
        <label for="embargoedPeriods">[@s.text name="reporting.projectDeliverable.dissemination.embargoedPeriods" /]</label>
      </div>
      [#else]
        <div class="input"><p>[#if (deliverable.dissemination.type??)!false][@s.text name="reporting.projectDeliverable.dissemination.${deliverable.dissemination.type}" /][#else]Open restriction is not selected[/#if]</p></div>
      [/#if]
    </div>
    [#-- Periods --]
    <div class="fullBlock"> 
      <div id="period-restrictedAccess" class="halfPartBlock openAccessPeriods" style="display:${((deliverable.dissemination.type == "restrictedAccess")!false)?string('block','none')}">
        [@customForm.input name="${params.deliverable.name}.dissemination.restrictedAccessUntilText" className="period" type="text" i18nkey="reporting.projectDeliverable.dissemination.restrictedAccessDate" editable=editable/]
      </div>
      <div id="period-embargoedPeriods" class="halfPartBlock openAccessPeriods" style="display:${((deliverable.dissemination.type == "embargoedPeriods")!false)?string('block','none')}">
        [@customForm.input name="${params.deliverable.name}.dissemination.restrictedEmbargoedText" className="period" type="text" i18nkey="reporting.projectDeliverable.dissemination.embargoedPeriodDate" editable=editable/]
      </div>
    </div>
  </div><!-- End aditional-openAccessRestriction -->
</div>

[#-- Have you adopted a license --]
<div class="fullBlock">
  [#assign adoptedLicense][@s.property value="${params.deliverable.name}.metadata[${deliverable.getMetadataIndex('dc.rights')}].value"/][/#assign]
  <table id="adoptedLicense" class="default">
    <tbody>
      <tr>
        <td class="key"><p>[@s.text name="reporting.projectDeliverable.dissemination.adoptedLicense" /][@customForm.req required=finalizingCurrently /]</p></td> 
        <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.dissemination.adoptedLicense" editable=editable value="${(adoptedLicense?has_content)?string}"/]</td>
      </tr>
    </tbody>
  </table>
  <div id="aditional-adoptedLicense"class="aditional" style="display:${((adoptedLicense?has_content)?string('block','none'))!'none'}">
    [#-- Rights --]
    <div class="fullPartBlock" >
      [@metadataField title="rights" encodedName="dc.rights" type="textArea"/]
    </div>
    [#if editable]
    <div id="licenses-examples">
      <h6>Computer Software:</h6>
      <ul>
        <li>MIT License</li>
        <li>GNU General Public Licence</li>
      </ul>
      <h6>Data:</h6>
      <ul>
        <li>CC licenses version 4.0</li>
        <li>CC Public Domain Dedication (CC0 1.0)</li>
        <li>Open Data Commons (ODC)</li>
      </ul>
      <h6>Other research types:</h6>
      <ul>
        <li>CC-BY (allow modifications and commercial use)</li>
        <li>CC-BY-SA (allow modifications as long as other share alike and commercial use)</li>
        <li>CC-BY-ND (allow commercial use but no modifications)</li>
        <li>CC-BY-NC (allow modifications but no commercial use)</li>
        <li>CC-BY-NC-SA (allow modifications as long as other share alike, but no commercial use)</li>
        <li>CC-BY-NC-ND (don't allow modifications neither commercial use)</li>
      </ul>
    </div>
    [/#if]
  </div><!-- End aditional-alreadyDisseminated -->
</div>

[#-- Is this deliverable disseminated --]
<div class="fullBlock">
  <table id="alreadyDisseminated" class="default">
    <tbody>
      <tr>
        <td class="key"><p>[@s.text name="reporting.projectDeliverable.dissemination.alreadyDisseminated" /][@customForm.req required=finalizingCurrently /]</p></td> 
        <td class="value">[@deliverableTemplate.yesNoInput name="${params.deliverable.name}.dissemination.alreadyDisseminated" editable=editable/]</td>
      </tr>
    </tbody>
  </table>
  <div id="aditional-alreadyDisseminated"class="aditional" style="display:${(deliverable.dissemination.alreadyDisseminated?string('block','none'))!'none'}">
    <p>[@s.text name="reporting.projectDeliverable.dissemination.alreadyDisseminated.description" /]</p><br />
    [#-- Dissemination channel list --]
    <div class="fullBlock">
      <div class="halfPartBlock"> 
        [@customForm.select name="${params.deliverable.name}.dissemination.disseminationChannel" label="" i18nkey="reporting.projectDeliverable.disseminationChannel" listName="disseminationChannels"  stringKey=true value="'${(deliverable.dissemination.disseminationChannel)!}'" required=finalizingCurrently editable=editable /]
      </div>
      <div class="halfPartBlock"> 
        <div id="examples">
          <p class="example" id="info-cgspace" style="display:none"><strong>Example of URL :</strong> <code>https://cgspace.cgiar.org/handle/10568/52163</code></p> 
          <p class="example" id="info-agtrials" style="display:none"><strong>Example of URL :</strong> <code>oai:agtrials.org:60</code></p>
          <p class="example" id="info-amkn" style="display:none"><strong>Example of URL :</strong> <code>oai:amkn.org:4260</code></p>
        </div>
      </div>
    </div>
    [#-- Dissemination channel URL / URI --]
    <div id="disseminationUrl" class="fullBlock" style="display:${((deliverable.dissemination.disseminationChannel?has_content)?string('block','none'))!'none'}">
      [@customForm.input name="${params.deliverable.name}.dissemination.disseminationUrl" className="" i18nkey="reporting.projectDeliverable.disseminationUrl" required=finalizingCurrently editable=editable /]
      [#if editable]<div id="fillMetadata" class="checkButton" style="display:${deliverable.otherChannel?string('none','block')}">Search & Fill Metadata</div>[/#if]
      <div class="clearfix"></div>
    </div>
    <div id="metadata-output" class="fullBlock">
    </div>
  </div><!-- End aditional-alreadyDisseminated -->
</div>