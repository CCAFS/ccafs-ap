[#ftl]
[#macro highlightsList highlights canEdit=true]
  [#if highlights?has_content]
    [@s.set var="counter" value=0/] 
    <table id="projectHighlights">
      <thead>
        <tr>
          <th class="id" >ID</th> 
          <th class="name">Highlight Name</th>
          <th class="type">Author</th>
          <th class="year">Year</th>
          <th class="removeHighlight">Remove</th> 
        </tr>
      </thead>
      <tbody>
      [#list highlights as hl]
        [#if editable]
          [#assign dlurl][@s.url namespace=namespace action='highlight' ][@s.param name='highlightID']${hl.id}[/@s.param][@s.param name='edit']true[/@s.param][/@s.url][/#assign]
        [#else]
          [#assign dlurl][@s.url namespace=namespace action='highlight' ][@s.param name='highlightID']${hl.id}[/@s.param][/@s.url][/#assign]
        [/#if]
        <tr>
          <td class="id" ><a href="${dlurl}">${hl.id}</a></td> 
          <td class="name"><a href="${dlurl}">[#if hl.title?trim?has_content]${hl.title}[#else]Untitled[/#if]</a></td>
          <td class="type">[#if hl.title?trim?has_content]${hl.author}[#else]Not defined[/#if]</td>
          <td class="year">[#if hl.title?trim?has_content]${hl.year}[#else]Not defined[/#if]</td>
          <td class="removeHighlight-row">
            [#if canEdit && action.hasProjectPermission("removeHighlight", project.id)]
              <a id="removeHighlight-${hl.id}" class="removeHighlight" href="highlightID${hl.id}" title="" >
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