[#ftl]
[#assign title = "Manage Users" /]
[#assign globalLibs = ["jquery", "dataTable","jreject"] /]
[#assign customJS = ["${baseUrl}/js/user/usersList.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "user" /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
	  [@s.form action="projects"] 
		<table class="userList" id="">
		  <thead>
		    <tr>
				<th id="ids">[@s.text name="user.manageUsers.id" /]</th>
				<th id="userName">[@s.text name="user.manageUsers.name" /]</th>
				<th id="userEmail">[@s.text name="user.manageUsers.eMail" /]</th>
				<th id="userInstitution">[@s.text name="user.manageUsers.institution" /]</th>
				<th id="userRole">[@s.text name="user.manageUsers.role" /]</th>
				<th id="userAction">[@s.text name="user.manageUsers.action" /]</th>
		    </tr>
		  </thead>
		  <tbody>
		  [#if users?has_content]
        [#list users as user]
   		   <tr>
              <td>
                  <a href="[@s.url action='userDescription' ][@s.param name='employeeID']${user.employeeId?c}[/@s.param][/@s.url]">
                  ${user.id?c}
                  </a>
              </td>
              <td>
                  <a href="[@s.url action='userDescription' ] [@s.param name='employeeID']${user.employeeId?c}[/@s.param][/@s.url]">
                   ${user.lastName} ${user.firstName}
                  </a>
              </td>
              <td> 
                  <a href="[@s.url action='userDescription'] [@s.param name='employeeID']${user.employeeId?c}[/@s.param] [/@s.url]">
                    ${user.email}
                  </a>
              </td>
              <td> 
                  <a href="[@s.url action='userDescription' ] [@s.param name='employeeID']${user.employeeId?c}[/@s.param] [/@s.url]">
                     ${user.currentInstitution.name}
                  </a>
              </td>
              <td> 
                  <a href="[@s.url action='userDescription' ] [@s.param name='employeeID']${user.employeeId?c}[/@s.param] [/@s.url]">
                   ${user.role.name}
                  </a>
              </td>
              <td> 
                  	<img src="${baseUrl}/images/global/icon-add.png" />
		  			<a href="" class="addUser" >[@s.text name="user.manageUsers.action.edit" /]</a>&nbsp;-&nbsp;
		  			<img src="${baseUrl}/images/global/icon-remove.png" />
		  			<a href="" class="addUser" >[@s.text name="user.manageUsers.action.remove" /]</a>&nbsp;&nbsp;
              </td>
          	</tr>  
        	[/#list]
        [#else]
          <tr>
            <td colspan="5"><h6>[@s.text name="user.manageUsers.emptyList" /]</h6></td>
          </tr>
        [/#if]
		  </tbody>
		</table>
		<div id="addUser" class="addLink">
		  <img src="${baseUrl}/images/global/icon-add.png" />
		  <a href="" class="addUser" >[@s.text name="user.manageUsers.addUser" /]</a>
		</div>
		
		 [/@s.form] 
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]