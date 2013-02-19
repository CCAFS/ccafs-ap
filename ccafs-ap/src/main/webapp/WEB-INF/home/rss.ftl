[#ftl]
<?xml version="1.0"?>
<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
  [#escape x as x?xml]
  <channel>
    <title>CCAFS Activity Planning</title>
    <link>${baseUrl}</link>
    <description>This site is for the internal use of CCAFS themes, centers and regions for the planning and subsequent reporting of yearly research activities</description>
    <language>en-us</language>
    [#list activities as activity]
    <item>
      <title>${activity.title}</title>      
      <description>${activity.description}</description>
      <guid>activity-${activity.id}</guid>
      <pubDate>${activity.dateAdded?string("EEE, dd MMM yyyy")}</pubDate>
    </item>
    [/#list]
  </channel>
  [/#escape]
</rss>
