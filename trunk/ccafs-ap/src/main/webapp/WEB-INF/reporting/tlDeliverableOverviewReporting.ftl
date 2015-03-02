[#ftl]
[#assign title = "Deliverable overview" /]

[#include "/WEB-INF/global/pages/popup-header.ftl" /]

[#assign notDefined][@s.text name="reporting.deliverableOverview.notDefined" /][/#assign]
<section class="content">
  
  <article class="halfContent">

    [#-- Left column --]
    <div class="halfPartBlock">
    
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
        [#if deliverable.description?has_content]
          ${deliverable.description}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
      
    </div>
    
    [#-- Right column --]
    <div class="halfPartBlock">
      [#-- Status --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.status" /]</h6>
        [#if deliverable.status?has_content]
          ${deliverable.status.name}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
    
      [#-- Type --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.type" /]</h6>
        [#if deliverable.type.parent?has_content]
          ${deliverable.type.parent.name}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
      
      [#-- Sub Type --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.subtype" /]</h6>
        [#if deliverable.type?has_content]
          ${deliverable.type.name}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
      
      [#-- Metadata --]
      
      [#-- Creator --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.metadata.creator" /]</h6>
        [#if deliverable.getMetadataValue('Creator')?has_content]
          ${deliverable.getMetadataValue('Creator')}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
    
      [#-- Publisher --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.metadata.publisher" /]</h6>
        [#if deliverable.getMetadataValue('Publisher')?has_content]
          ${deliverable.getMetadataValue('Publisher')}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
      
      [#-- Format --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.metadata.format" /]</h6>
        [#if deliverable.getMetadataValue('Format')?has_content]
          ${deliverable.getMetadataValue('Format')!notDefined}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
      
      [#-- Language --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.metadata.language" /]</h6>
        [#if deliverable.getMetadataValue('Language')?has_content]
          ${deliverable.getMetadataValue('Language')!notDefined}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
    
      [#-- Coverage --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.metadata.coverage" /]</h6>
        [#if deliverable.getMetadataValue('Coverage')?has_content]
          ${deliverable.getMetadataValue('Coverage')}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
      
      [#-- Rights --]
      <div>
        <h6>[@s.text name="reporting.deliverableOverview.metadata.rights" /]</h6>
        [#if deliverable.getMetadataValue('Rights')?has_content]
          ${deliverable.getMetadataValue('Rights')}
        [#else]  
          ${notDefined} 
        [/#if]
      </div>
      
    </div>

  </article>
  </section>
</body>