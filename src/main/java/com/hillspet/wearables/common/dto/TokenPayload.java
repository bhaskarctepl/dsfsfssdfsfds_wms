package com.hillspet.wearables.common.dto;

import java.util.Date;

public class TokenPayload {

	private String issuer;

	private String subject;

	private Date expirationTime;

	private Date issuedAt;

	private String userName;

	public TokenPayload(String issuer, String subject, Date expirationTime, Date issuedAt, String userName) {
		super();
		this.issuer = issuer;
		this.subject = subject;
		this.expirationTime = expirationTime;
		this.issuedAt = issuedAt;
		this.userName = userName;
	}

	public String getIssuer() {
		return this.issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "TokenPayload [issuer=" + issuer + ", subject=" + subject + ", expirationTime=" + expirationTime
				+ ", issuedAt=" + issuedAt + ", userName=" + userName + "]";
	}

}
