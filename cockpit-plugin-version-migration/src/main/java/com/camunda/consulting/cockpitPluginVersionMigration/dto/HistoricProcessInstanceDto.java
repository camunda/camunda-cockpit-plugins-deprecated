package com.camunda.consulting.cockpitPluginVersionMigration.dto;

public class HistoricProcessInstanceDto {
  
  private String id_;
  private String processDefinitionId_;
  
  public String getId_() {
    return id_;
  }
  public void setId_(String id_) {
    this.id_ = id_;
  }
  public String getProcessDefinitionId_() {
    return processDefinitionId_;
  }
  public void setProcessDefinitionId_(String processDefinitionId_) {
    this.processDefinitionId_ = processDefinitionId_;
  }
}
