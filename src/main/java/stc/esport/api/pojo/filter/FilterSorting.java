package stc.esport.api.pojo.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterSorting {

	
	@JsonProperty
	private String sortName;
	
	@JsonProperty
	private String sortType;
	
	@JsonProperty
	private String sortColumnType;

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortColumnType() {
		return sortColumnType;
	}

	public void setSortColumnType(String sortColumnType) {
		this.sortColumnType = sortColumnType;
	}
	
	
}
