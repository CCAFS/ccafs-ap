/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Communication {

  private int id;
  private String mediaCampaings;
  private String blogs;
  private String websites;
  private String sociaMediaCampaigns;
  private String newsletters;
  private String events;
  private String videosMultimedia;
  private String otherCommunications;
  private Leader leader;
  private Logframe logframe;

  public String getBlogs() {
    return blogs;
  }

  public String getEvents() {
    return events;
  }

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public Logframe getLogframe() {
    return logframe;
  }

  public String getMediaCampaings() {
    return mediaCampaings;
  }

  public String getNewsletters() {
    return newsletters;
  }

  public String getOtherCommunications() {
    return otherCommunications;
  }

  public String getSociaMediaCampaigns() {
    return sociaMediaCampaigns;
  }

  public String getVideosMultimedia() {
    return videosMultimedia;
  }

  public String getWebsites() {
    return websites;
  }

  public void setBlogs(String blogs) {
    this.blogs = blogs;
  }

  public void setEvents(String events) {
    this.events = events;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

  public void setMediaCampaings(String mediaCamapaings) {
    this.mediaCampaings = mediaCamapaings;
  }

  public void setNewsletters(String newsletters) {
    this.newsletters = newsletters;
  }

  public void setOtherCommunications(String otherCommunications) {
    this.otherCommunications = otherCommunications;
  }

  public void setSociaMediaCampaigns(String sociaMediaCampaigns) {
    this.sociaMediaCampaigns = sociaMediaCampaigns;
  }

  public void setVideosMultimedia(String videosMultimedia) {
    this.videosMultimedia = videosMultimedia;
  }

  public void setWebsites(String websites) {
    this.websites = websites;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}