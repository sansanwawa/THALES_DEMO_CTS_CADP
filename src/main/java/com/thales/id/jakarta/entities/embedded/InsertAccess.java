package com.thales.id.jakarta.entities.embedded;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;

@Embeddable 
public class InsertAccess implements Serializable {
 
	private static InsertAccess instance = null;
 
	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "insertDateTime", updatable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date insertDateTime = new Date();

	public InsertAccess() {
		this("");
	}

	public InsertAccess(String createdBy) {
		this.createdBy = createdBy;
	}

	public static InsertAccess getInstance(String createdBy) {
		return new InsertAccess(createdBy);
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(Date insertDateTime) {
		this.insertDateTime = insertDateTime;
	}

}
