[#ftl] 
[#macro searchUsers]
  <div id="dialog-searchUsers" title="Search User"> 
    <div class="dialog-content">
      <form class="pure-form">
        [@customForm.input name="" type="text" i18nkey="users.searchByEmail"/]
        <div id="usersList">
          <h6>Users Lists <span></span><h6>
          <ul>
          </ul>
         </div>
       </div>  
     </form>
  </div>
[/#macro]