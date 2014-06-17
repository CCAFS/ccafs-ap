[#ftl]
[#--
 
 * This file is part of CCAFS Planning and Reporting Platform.
 *
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see 
 * <http://www.gnu.org/licenses/>
  
--]

<script type="text/javascript">
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-42082138-1', 'cgiar.org');
  
  [#if currentUser?has_content]
    // Create a custom variable to track the user who enter into the platform
    // This variable is create with session-level to send this data once per session
    // not in every page.
    
    [#-- Dimension1: Users --]
    [#-- Remove the @cgiar.org in order to prevent sending emails to analytics  --]
    ga('set', {
      'dimension1': '${currentUser.email?substring(0, currentUser.email?last_index_of("@"))}',
      'metric1': 1
    });      
  [/#if]
    
  ga('send', 'pageview');

</script>