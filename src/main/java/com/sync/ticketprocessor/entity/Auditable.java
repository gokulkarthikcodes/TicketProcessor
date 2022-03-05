package com.sync.ticketprocessor.entity;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auditable {

	@Id
	private String id;

	private String createdBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "Asia/Kolkata")
	private DateTime created;
	private String updatedBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "Asia/Kolkata")
	private DateTime updated;

	private boolean active;




	public void createAuditForSave(String createdBy) {
		DateTime dateTime = DateTime.now();

		if (this.created == null) {
			this.created = dateTime;
		}
		if (this.createdBy == null) {
			this.createdBy = createdBy;
		}

		this.updated = dateTime;
		this.updatedBy = createdBy;

		this.active = Boolean.TRUE;
	}

	public void createAuditForUpdate(String updatedBy) {

		DateTime dateTime = DateTime.now();
		this.updated = dateTime;
		this.updatedBy = updatedBy;
		this.active = Boolean.TRUE;
	}



}
