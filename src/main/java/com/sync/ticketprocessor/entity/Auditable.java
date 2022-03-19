package com.sync.ticketprocessor.entity;

import com.sync.ticketprocessor.service.impl.UserDetailsImpl;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.Authentication;

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

	@Indexed
	private String createdBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "Asia/Kolkata")
	private DateTime created;
	private String updatedBy;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "Asia/Kolkata")
	private DateTime updated;

	private boolean active;




	public void createAuditForSave(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		DateTime dateTime = DateTime.now();

		if (this.created == null) {
			this.created = dateTime;
		}
		if (this.createdBy == null) {
			this.createdBy = userPrincipal.getUsername();
		}

		this.active = Boolean.TRUE;
	}

	public void createAuditForUpdate(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		DateTime dateTime = DateTime.now();
		this.updated = dateTime;
		this.updatedBy = userPrincipal.getUsername();
		this.active = Boolean.TRUE;
	}



}
