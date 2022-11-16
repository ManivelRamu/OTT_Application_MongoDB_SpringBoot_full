package com.tel.ott.application.collection;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.annotation.Id;



public class Episode {
	
	@Id
	private String episodeId;
	
    private LocalDate createdAt;

	private LocalDate updatedAt;
		
	private LocalDate deletedAt;
	
	private boolean isActive;
	
	private String episodeNo;
	
	private String episodeName;
	
	private  String description;
	
	private String showName;
	
	private String seasonName;
	
	private String platform;
	
	

	public Episode(String episodeId, LocalDate createdAt, LocalDate updatedAt, LocalDate deletedAt, boolean isActive, String episodeNo,
			String episodeName, String description, String showName, String platform, String seasonName) {
		super();
		this.episodeId = episodeId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.isActive = isActive;
		this.episodeNo = episodeNo;
		this.episodeName = episodeName;
		this.description = description;
		this.showName = showName;
		this.platform = platform;
		this.seasonName = seasonName;
	}

	public Episode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDate getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDate deletedAt) {
		this.deletedAt = deletedAt;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getEpisodeNo() {
		return episodeNo;
	}

	public void setEpisodeNo(String episodeNo) {
		this.episodeNo = episodeNo;
	}

	public String getEpisodeName() {
		return episodeName;
	}

	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	@Override
	public String toString() {
		return "Episode [episodeId=" + episodeId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", deletedAt=" + deletedAt + ", isActive=" + isActive + ", episodeNo=" + episodeNo + ", episodeName="
				+ episodeName + ", description=" + description + ", showName=" + showName + ", platform=" + platform
				+ ", seasonName=" + seasonName + "]";
	}


	
	

}
