package com.sync.ticketprocessor.entity;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auditable {

	private String id;
	private String createdBy;
	private String createdByUserName;
	private String updatedBy;
	private boolean active;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "Asia/Kolkata")
	private DateTime createdDate;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "Asia/Kolkata")
	private DateTime updatedDate;

	public void createAudit(String createdBy, String createdByUserName) {
		DateTime dateTime = DateTime.now();

		if (this.createdDate == null) {
			this.createdDate = dateTime;
		}
		if (this.createdBy == null) {
			this.createdBy = createdBy;
		}
		if (this.createdByUserName == null) {
			this.createdByUserName = createdByUserName;
		}

		this.updatedDate = dateTime;
		this.updatedBy = createdBy;
		this.active = Boolean.TRUE;

	}

}
