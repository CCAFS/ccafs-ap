[#ftl]
[#assign title = "Select other Location - Acitivty Planning" /]
[#assign globalLibs = ["jquery"] /]
[#assign customJS = ["${baseUrl}/js/planning/selectLocations.js", "http://maps.googleapis.com/maps/api/js?key=AIzaSyC5BxhTe34V_hJjK6FLXgdm_YT2qSXmAoc&sensor=false"] /]
[#include "/WEB-INF/global/pages/popup-header.ftl" /]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="planning.locations.help" /]</p>
  </div>
  <article class="fullContent">
    <div class="fullBlock">
      <h6>[@s.text name="home.activity.otherLocation" /]</h6>
      <p id="otherLocationMessage"></p>
      <div id="other_locations"></div>
    </div>
      
    <div class="buttons">
      [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
      [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
    </div>

  </article>
</section>
</body>
</html>