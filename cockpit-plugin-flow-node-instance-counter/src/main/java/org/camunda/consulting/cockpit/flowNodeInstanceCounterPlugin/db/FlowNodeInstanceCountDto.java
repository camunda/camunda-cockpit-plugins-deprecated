package org.camunda.consulting.cockpit.flowNodeInstanceCounterPlugin.db;

public class FlowNodeInstanceCountDto {

	private String month;
	private String year;
	private int flowNodeInstanceCount;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getFlowNodeInstanceCount() {
		return flowNodeInstanceCount;
	}

	public void setFlowNodeInstanceCount(int flowNodeInstanceCount) {
		this.flowNodeInstanceCount = flowNodeInstanceCount;
	}

}
