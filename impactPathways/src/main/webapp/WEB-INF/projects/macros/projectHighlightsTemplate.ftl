[#ftl]
[#macro highlightsList highlights canEdit=true]
  [#if highlights?has_content]
    [@s.set var="counter" value=0/] 
    <table id="projectHighlights">
      <thead>
        <tr>
          <th class="id" >ID</th> 
          <th class="name">Project highlight Name</th>
          <th class="type">Type</th>  
          <th class="removeHighlight">Remove</th> 
        </tr>
      </thead>
      <tbody>
      [#list highlights as hl]
        [#assign dlurl][@s.url namespace=namespace action='highlight' ][@s.param name='deliverableID']${hl.id}[/@s.param][/@s.url][/#assign]
        <tr>
          <td class="id" ><a href="${dlurl}">${hl.id}</a></td> 
          <td class="name"><a href="${dlurl}">${hl.title!"Untitled"}</a></td>
          <td class="type">${(hl.type.name)!"Not defined"}</td> 
          <td class="removeHighlight-row">
            [#if canEdit]
              <a id="removeHighlight-${hl.id}" class="removeHighlight" href="deliverableID${hl.id}" title="" >
                <img src="${baseUrl}/images/global/trash.png" title="[@s.text name="reporting.projectHighlights.removeHighlight" /]" /> 
              </a>
            [#else]
              <img src="${baseUrl}/images/global/trash_disable.png" title="[@s.text name="reporting.projectHighlights.cantDeleteHighlight" /]" />
            [/#if]
          </td> 
        </tr> 
      [/#list]
      </tbody> 
    </table>
    <div class="clearfix"></div>
  [/#if]  
[/#macro] 