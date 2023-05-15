package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author vvodyaram
 *
 */
@ApiModel(description = "Contains all information that needed to create customer", value = "UserRequest")
@JsonInclude(value = Include.NON_NULL)
public class UserRequest {

	UserRequest() {
	}

	private Integer userId;

	@ApiModelProperty(value = "Email", required = true, example = "john@ctepl.com")
	private String email;

	@ApiModelProperty(value = "First Name", required = true, example = "john")
	private String firstName;

	@ApiModelProperty(value = "Last Name", required = true, example = "joe")
	private String lastName;

	@ApiModelProperty(value = "password", required = true)
	private String password;

	@ApiModelProperty(value = "new password")
	private String newPassword;

	@ApiModelProperty(value = "confirm password")
	private String confirmPassword;

	private String mobile;

	private Integer roleId;

	private Integer status;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
