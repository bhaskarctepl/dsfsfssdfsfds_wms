package com.hillspet.wearables.dto;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class BaseFilter {

	@DefaultValue("0")
	@QueryParam("startIndex")
	@ApiParam(name = "startIndex", type = "integer", value = "Index of the page. Default value is 0")
	private int startIndex;

	@DefaultValue("1000")
	@QueryParam("limit")
	@ApiParam(name = "limit", type = "integer", value = "Maximum number of records returned in the response. Default is 1000")
	private int limit;

	@ApiParam(name = "sortBy", value = "This field specifies the column used for sorting")
	@QueryParam("sortBy")
	private String sortBy;

	@ApiParam(name = "order", value = "This field specifies the type of sorting whether ASC/DESC")
	@QueryParam("order")
	private String order;

	@ApiParam(name = "searchText", value = "This is the free text search box used in all the list pages")
	@QueryParam("searchText")
	private String searchText;

	@ApiParam(name = "filterType", value = "Filter Type is the first dropdown value that user selects")
	@QueryParam("filterType")
	private String filterType;

	@ApiParam(name = "filterValue", value = "Filter Value is the second dropdown value that user selects based on first dropdown")
	@QueryParam("filterValue")
	private String filterValue;

	private Boolean isSuper;
	private Integer userId;

	public BaseFilter() {

	}

	public BaseFilter(int startIndex, int limit, String sortBy, String order, String searchText, String filterType,
			String filterValue) {
		super();
		this.startIndex = startIndex;
		this.limit = limit;
		this.sortBy = sortBy;
		this.order = order;
		this.searchText = searchText;
		this.filterType = filterType;
		this.filterValue = filterValue;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSearchText() {
		return removeWildcardCharacter(searchText);
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public Boolean getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(Boolean isSuper) {
		this.isSuper = isSuper;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String removeWildcardCharacter(String searchText){
		if(searchText.contains("%")){
			searchText = searchText.replaceAll("%", "\\\\%");
		}
		if(searchText.contains("_")){
			searchText = searchText.replaceAll("_", "\\\\_");
		}
		if(searchText.contains("^")){
			searchText = searchText.replaceAll("^", "\\\\^");
		}
		if(searchText.contains("-")){
			searchText = searchText.replaceAll("-", "\\\\-");
		}
		if(searchText.contains("[")){
			searchText = searchText.replaceAll("\\[", "\\\\[");
		}
		if(searchText.contains("]")){
			searchText = searchText.replaceAll("]", "\\\\]");
		}
		return searchText;
	}

}
