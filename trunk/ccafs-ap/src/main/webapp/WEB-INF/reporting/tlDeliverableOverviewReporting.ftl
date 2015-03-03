[#ftl]
[#assign title = "Deliverable overview" /]

[#include "/WEB-INF/global/pages/popup-header.ftl" /]

[#assign notDefined][@s.text name="reporting.deliverableOverview.notDefined" /][/#assign]
<section class="content">
  
  <article id="deliverableOverview">

    [#-- Left column --]
    <div class="overview-column overview-left">
    
      [#-- Title --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.title" /]</h6> 
        [#if deliverable.getMetadataValue('Title')?has_content]
          ${deliverable.getMetadataValue('Title')}
        [#else]
          ${notDefined} 
        [/#if]
      </div>
    
      [#-- Description --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.description" /]</h6> 
        [#if deliverable.getMetadataValue('DisseminationDescription')?has_content]
         ${deliverable.getMetadataValue('DisseminationDescription')}
        [#elseif deliverable.description?has_content]
          ${deliverable.description}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
      
       [#-- Activity title --]
      <div>
        <h6>Belong to the activity</h6> 
         ${activity.title}
      </div>
      
    </div>
    
    [#-- Right column --]
    <div class="overview-column overview-right">
      [#-- Status --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.status" /]</h6>
        <p> 
        [#if deliverable.status?has_content]
          ${deliverable.status.name}
        [#else]  
          ${notDefined} 
        [/#if]
        </p> 
    
        [#-- Type --] 
        <h6>[@s.text name="reporting.deliverableOverview.type" /]</h6>
        <p> 
        [#if deliverable.type.parent?has_content]
          ${deliverable.type.parent.name}
        [#else]  
          ${notDefined} 
        [/#if]
        </p> 
      
        [#-- Sub Type --] 
        <h6>[@s.text name="reporting.deliverableOverview.subtype" /]</h6>
        <p> 
        [#if deliverable.type?has_content]
          ${deliverable.type.name}
        [#else]  
          ${notDefined} 
        [/#if]
        </p> 
      </div>
      
      [#-- Metadata --]
      <h5>Metadata</h5>
      [#-- Creator --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.metadata.creator" /]</h6>
        <p> 
        [#if deliverable.getMetadataValue('Creator')?has_content]
          ${deliverable.getMetadataValue('Creator')}
        [#else]  
          ${notDefined} 
        [/#if]
        </p> 
    
        [#-- Publisher --] 
        <h6>[@s.text name="reporting.deliverableOverview.metadata.publisher" /]</h6>
        <p>
        [#if deliverable.getMetadataValue('Publisher')?has_content]
          ${deliverable.getMetadataValue('Publisher')}
        [#else]  
          ${notDefined} 
        [/#if]
        </p> 
      
        [#-- Format --] 
        <h6>[@s.text name="reporting.deliverableOverview.metadata.format" /]</h6>
        <p> 
        [#if deliverable.getMetadataValue('Format')?has_content]
          ${deliverable.getMetadataValue('Format')!notDefined}
        [#else]  
          ${notDefined} 
        [/#if]
        </p> 
      
        [#-- Language --] 
        <h6>[@s.text name="reporting.deliverableOverview.metadata.language" /]</h6>
        <p> 
        [#if deliverable.getMetadataValue('Language')?has_content]
          ${deliverable.getMetadataValue('Language')!notDefined}
        [#else]  
          ${notDefined} 
        [/#if]
        </p> 
    
        [#-- Coverage --] 
        <h6>[@s.text name="reporting.deliverableOverview.metadata.coverage" /]</h6>
        <p> 
        [#if deliverable.getMetadataValue('Coverage')?has_content]
          ${deliverable.getMetadataValue('Coverage')}
        [#else]  
          ${notDefined} 
        [/#if]
        </p> 
      
        [#-- Rights --] 
        <h6>[@s.text name="reporting.deliverableOverview.metadata.rights" /]</h6>
        <p>
        [#if deliverable.getMetadataValue('Rights')?has_content]
          ${deliverable.getMetadataValue('Rights')}
        [#else]  
          ${notDefined} 
        [/#if]
        </p> 
      </div>
      
    </div>
    <div class="clearfix"></div>
  </article>
  
  </section>
</body>