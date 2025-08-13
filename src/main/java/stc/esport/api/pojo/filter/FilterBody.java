package stc.esport.api.pojo.filter;


import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterBody {
	
	@JsonProperty
	private ArrayList<Filter> filters;
	
	@JsonProperty
	private int pageNo;
	
	@JsonProperty
	private int pageLength;
	
	@JsonProperty
	private ArrayList<FilterSorting> sorting;
	
	

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageLength() {
		return pageLength;
	}

	public void setPageLength(int pageLength) {
		this.pageLength = pageLength;
	}

	public ArrayList<Filter> getFilters() {
		return filters;
	}

	public void setFilters(ArrayList<Filter> filters) {
		this.filters = filters;
	}

	public ArrayList<FilterSorting> getSorting() {
		return sorting;
	}

	public void setSorting(ArrayList<FilterSorting> sorting) {
		this.sorting = sorting;
	}

	

	

	
}
