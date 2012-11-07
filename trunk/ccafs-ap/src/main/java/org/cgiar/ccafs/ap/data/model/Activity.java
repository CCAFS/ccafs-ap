package org.cgiar.ccafs.ap.data.model;

import java.util.Date;

public class Activity {
	private int id;
	private String title;
	private Date startDate;
	private Date endDate;
	private String description;
	// private Milestone milestone;
	// private ActivityLeader leader;
	private boolean isPlanning;
	private boolean isGlobal;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPlanning() {
		return isPlanning;
	}
	public void setPlanning(boolean isPlanning) {
		this.isPlanning = isPlanning;
	}
	public boolean isGlobal() {
		return isGlobal;
	}
	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}
	
	
}
