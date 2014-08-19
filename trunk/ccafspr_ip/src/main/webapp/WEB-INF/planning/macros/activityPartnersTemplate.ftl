[#ftl]
[#macro activityPartner]
	<div class="borderBox">
		<div id="removeActivityPartner"class="removeElement removeLink" title="[@s.text name="planning.activityPartner.removePartner" /]"></div>
		[#-- Organizations List --]
		<div class="fullBlock chosen ">
			[@customForm.select name="activityPartner.institution" i18nkey="planning.activityPartner.partner" listName="allPartners" keyFieldName="id"  displayFieldName="name" /]
		</div> 
		[#-- Contact Name --] 
	    <div class="halfPartBlock">
	      [@customForm.input name="contactName" type="text" i18nkey="planning.activityPartner.contactName" required=true /]
	    </div>
	    [#-- Contact Email --]
	    <div class="halfPartBlock">
	      [@customForm.input name="contactEmail" type="text" i18nkey="planning.activityPartner.contactEmail" required=true /]
	    </div>
	    [#-- Contribution --]
	    <div class="fullBlock partnerResponsabilities">        
	      [@customForm.textArea name="responsabilities" i18nkey="planning.activityPartner.contribution" required=true /]
	    </div>
    </div>
[/#macro]