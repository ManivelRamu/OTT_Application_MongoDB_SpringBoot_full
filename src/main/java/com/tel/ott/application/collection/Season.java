package com.tel.ott.application.collection;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class Season {
        
	    @Id
	    private String seasonId;	
	    private LocalDate createdAt;
	    private LocalDate updatedAt;
		private LocalDate deletedAt;
	    private String seasonName;
	    private String showName;
	    
	    private List<Episode> episode;	    
	    private String availableOn;
	    private boolean isActive = Boolean.FALSE;
	   
	    
	    
	  
		@Override
		public String toString() {
			return "Season [seasonId=" + seasonId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
					+ ", deletedAt=" + deletedAt + ", seasonName=" + seasonName + ", showName=" + showName
					+ ", episode=" + episode + ", availableOn=" + availableOn + ", isActive=" + isActive
					+ ", getSeasonId()=" + getSeasonId() + ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()="
					+ getUpdatedAt() + ", getDeletedAt()=" + getDeletedAt() + ", getSeasonName()=" + getSeasonName()
					+ ", getShowName()=" + getShowName() + ", getEpisode()=" + getEpisode() + ", getAvailableOn()="
					+ getAvailableOn() + ", isActive()=" + isActive() + ", getClass()=" + getClass() + ", hashCode()="
					+ hashCode() + ", toString()=" + super.toString() + "]";
		}


		public Season() {
			super();
			
		}
		
		
		public Season(String seasonId, LocalDate createdAt, LocalDate updatedAt, LocalDate deletedAt, String seasonName,
				String showName, List<Episode> episode, String availableOn, boolean isActive) {
			super();
			this.seasonId = seasonId;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.deletedAt = deletedAt;
			this.seasonName = seasonName;
			this.showName = showName;
			this.episode = episode;
			this.availableOn = availableOn;
			this.isActive = isActive;
		}
		public String getSeasonId() {
			return seasonId;
		}
		public void setSeasonId(String seasonId) {
			this.seasonId = seasonId;
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
		public String getSeasonName() {
			return seasonName;
		}
		public void setSeasonName(String seasonName) {
			this.seasonName = seasonName;
		}
		public String getShowName() {
			return showName;
		}
		public void setShowName(String showName) {
			this.showName = showName;
		}
		public List<Episode> getEpisode() {
			return episode;
		}
		public void setEpisode(List<Episode> episode) {
			this.episode = episode;
		}
		public String getAvailableOn() {
			return availableOn;
		}
		public void setAvailableOn(String availableOn) {
			this.availableOn = availableOn;
		}
		public boolean isActive() {
			return isActive;
		}
		public void setActive(boolean isActive) {
			this.isActive = isActive;
		};



}
