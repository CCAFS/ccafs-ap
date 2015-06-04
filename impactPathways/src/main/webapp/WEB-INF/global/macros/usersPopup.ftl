[#ftl] 
[#macro searchUsers]
  <div id="dialog-searchUsers" title="Search User"> 
    <div class="dialog-content">
      <form class="pure-form">
        <div class="search-content clearfix">
          <div class="search-input">
            [@customForm.input name="" type="text" i18nkey="form.buttons.searchUser"/]
            <div class="search-loader" style="display:none"><img src="${baseUrl}/images/global/loading_2.gif"></div>
          </div>  
          <div class="search-button">[@s.text name="form.buttons.search" /]</div>
        </div> 
        <div class="usersList panel secondary">
          <div class="panel-head"> [@s.text name="users.usersList" /]</div>
          <div class="panel-body"> 
            <p class="userMessage">We have not been found users according to search field</p>
            <ul></ul>
          </div>
        </div>   
      </form>
    [#-- User Template --]
    <ul style="display:none"> 
      <li id="userTemplate">
        <span class="contact name">{composedName}</span>  
        <span class="listButton select">[@s.text name="form.buttons.select" /]</span>
        <span class="contactId" style="display:none">{userId}</span>
      </li> 
    </ul>   
  </div>
  
[/#macro]