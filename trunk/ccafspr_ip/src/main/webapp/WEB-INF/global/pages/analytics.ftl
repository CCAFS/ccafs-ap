[#ftl]

[#if !baseUrl?contains("localhost") && !baseUrl?contains("test")]
  <script type="text/javascript">
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
  
    ga('create', 'UA-42082138-1', 'cgiar.org');
    ga('require', 'displayfeatures');
    
    [#if currentUser?has_content]
      // Create a custom variable to track the user who enter into the platform
      // This variable is create with session-level to send this data once per session
      // not in every page.
     ga(‘set’, ‘&uid’, '${currentUser.email?substring(0, currentUser.email?last_index_of("@"))}'); // Set the user ID using signed-in user_id.
     
      [#-- Dimension1: Users --]
      [#-- Remove the @cgiar.org in order to prevent sending emails to analytics  --]
      ga('set', {
        'dimension1': '${currentUser.email?substring(0, currentUser.email?last_index_of("@"))}',
        'metric1': 1
      });      
     
    [/#if]
      
    ga('send', 'pageview');
  
  </script>
[/#if]

