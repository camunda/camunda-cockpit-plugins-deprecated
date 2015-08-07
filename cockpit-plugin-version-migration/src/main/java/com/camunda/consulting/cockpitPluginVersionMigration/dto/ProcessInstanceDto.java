package com.camunda.consulting.cockpitPluginVersionMigration.dto;

public class ProcessInstanceDto {
  
  private String processInstanceId;
  private String processDefinitionId;
  
  public String getProcessInstanceId() {
    return processInstanceId;
  }
  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }
  public String getProcessDefinitionId() {
    return processDefinitionId;
  }
  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

}
