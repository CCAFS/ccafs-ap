[#ftl] 
[#macro logList list] 
  <div id="log-history" class="borderBox">
    <h1 class="simpleTitle"> Log History</h1> 
    <table class="log-table">
      <thead>
        <tr>
          <th class="type">&nbsp;</th>
          <th class="date">Date</th>
          <th class="person">Person</th>
          <th class="justification">Justification</th>
        </tr>
      </thead>
      <tbody>
        [#list list as log]
        <tr>
          <td class="type"><span class="logType ${log.action}" title="${log.action}">&nbsp;</span></td>
          <td class="date">${log.date?date}</td>
          <td class="person">${log.user.composedName}</td>
          <td class="justification">${log.justification}</td>
        </tr>
        [/#list]
      </tbody>
    </table>
  </div>
  
[/#macro]