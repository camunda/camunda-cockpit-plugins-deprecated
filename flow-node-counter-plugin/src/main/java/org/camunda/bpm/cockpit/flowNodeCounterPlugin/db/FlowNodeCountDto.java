package org.camunda.bpm.cockpit.flowNodeCounterPlugin.db;

public class FlowNodeCountDto {

  private String month;
  private String year;
  private int flowNodeCount;
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
  public int getFlowNodeCount() {
    return flowNodeCount;
  }
  public void setFlowNodeCount(int flowNodeCount) {
    this.flowNodeCount = flowNodeCount;
  }

}
