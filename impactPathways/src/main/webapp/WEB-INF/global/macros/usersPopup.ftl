[#ftl] 
[#macro searchUsers]
  <div id="dialog-searchUsers" title="Search User"> 
    <div class="dialog-content">
      <form class="pure-form">
        <div class="search-content clearfix">
          [@customForm.input name="" type="text" i18nkey="form.buttons.searchUser"/]
          <div class="search-button">[@s.text name="form.buttons.search" /]</div>
        </div> 
        <div class="usersList panel secondary">
          <div class="panel-head"> [@s.text name="users.usersList" /]</div>
          <div class="panel-body">
            [#assign usersFound = [
              {"id":"1", "name":"Sebastian Amariles", "email":"s.amariles@cgiar.org"},
              {"id":"2", "name":"Hernan Carvajal", "email":"h.d.carvajal@cgiar.org"},
              {"id":"3", "name":"Hector Tobon", "email":"h.f.tobon@cgiar.org"}
            ]/]
            <ul>
              [#list usersFound as user]
                <li class="[#if !user_has_next]last[/#if]">
                  <span class="contact name">${user.name}</span> 
                  &lt;<span class="contact email">${user.email}</span>&gt;
                  <span class="listButton select">Select</span>
                  <span class="contactId" style="display:none">${user.id}</span>
                </li>
              [/#list]
            </ul>
          </div>
      </div>   
     </form>
  </div>
[/#macro]