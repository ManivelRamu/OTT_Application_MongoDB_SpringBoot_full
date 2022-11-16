package com.tel.ott.application.collection;
/*
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

*/


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Shows {
	private String _id;
	
	private LocalDate createdAt;
	
	private LocalDate updatedAt;
	
	private LocalDate deletedAt;
	
	private String showName;
	
	private String showDirector;
	
	private String showRating;
	
	private String showYear;
	
	private List<Season> season;
	
	private boolean isActive;
	
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Shows() {
		super();
	}

	public Shows( LocalDate createdAt, LocalDate updatedAt, LocalDate deletedAt, String showName,
			String showDirector, String showRating, String showYear, List<Season> season, boolean isActive) {
		super();
		
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.showName = showName;
		this.showDirector = showDirector;
		this.showRating = showRating;
		this.showYear = showYear;
		this.season = season;
		this.isActive = isActive;
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

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getShowDirector() {
		return showDirector;
	}

	public void setShowDirector(String showDirector) {
		this.showDirector = showDirector;
	}

	public String getShowRating() {
		return showRating;
	}

	public void setShowRating(String showRating) {
		this.showRating = showRating;
	}

	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public List<Season> getSeason() {
		return season;
	}

	public void setSeason(List<Season> season) {
		this.season = season;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Show [_id=" + _id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt
				+ ", showName=" + showName + ", showDirector=" + showDirector + ", showRating=" + showRating
				+ ", showYear=" + showYear + ", season=" + season + ", isActive=" + isActive + ", get_id()=" + get_id()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt() + ", getDeletedAt()="
				+ getDeletedAt() + ", getShowName()=" + getShowName() + ", getShowDirector()=" + getShowDirector()
				+ ", getShowRating()=" + getShowRating() + ", getShowYear()=" + getShowYear() + ", getSeason()="
				+ getSeason() + ", isActive()=" + isActive() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
