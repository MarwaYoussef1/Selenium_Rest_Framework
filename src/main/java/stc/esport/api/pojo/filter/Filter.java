package stc.esport.api.pojo.filter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Filter {

	
	@JsonProperty
	private String columnName;
	
	@JsonProperty
	private String columnValue;
	
	@JsonProperty
	private List<String> columnValues;
	
	@JsonProperty
	private String operator;
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public List<String> getColumnValues() {
		return columnValues;
	}

	public void setColumnValues(List<String> columnValues) {
		this.columnValues = columnValues;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}


}
