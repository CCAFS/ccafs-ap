[#ftl]
[#macro activityImpactPathwayContribute ]
	<div class="halfPartBlock">
    	<b>[@s.text name="planning.activityImpactPathways.contributingTo" /]</b> TODO Get Program
    	<div class="borderBox">
    		[@customForm.select name="activity" label=""  disabled=false i18nkey="planning.activityImpactPathways.outcome" listName="" keyFieldName="id"  displayFieldName="name" /]
    		<br/>
    		[@customForm.select name="activity" label=""  disabled=false i18nkey="planning.activityImpactPathways.mog" listName="" keyFieldName="id"  displayFieldName="name" /]
    	</div>
    </div>
[/#macro]

[#macro activityImpactPathwayTarget ]
	<div class="fullBlock">
		<b>[@s.text name="planning.activityImpactPathways.targets" /]</b>
		<div class="borderBox">
		<div id="removeImpactPathwayTarget"class="removeElement removeLink" title="[@s.text name="planning.activityImpactPathways.removeTarget" /]"></div>
		<div class="checkboxGroup">
			TODO bring Indicator[@customForm.checkbox  name=""  value="" /]
		</div>
			[@customForm.input name="" type="text" i18nkey="planning.activityImpactPathways.targetValue" required=true /]
			
			[@customForm.textArea name="" i18nkey="planning.activityImpactPathways.targetNarrative" required=true /]
		</div>
	</div>
[/#macro]