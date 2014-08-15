package com.camunda.bpm.demo.cockpit.plugin.kpiview.sample;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;

/**
 * Process Application exposing this application's resources the process engine. 
 */
@ProcessApplication
public class CamundaBpmProcessApplication extends ServletProcessApplication {

  private static final String PROCESS_DEFINITION_KEY = "cockpit-plugin-kpi-view-pa-sample";

  /**
   * In a @PostDeploy Hook you can interact with the process engine and access 
   * the processes the application has deployed. 
   */
  @PostDeploy
  public void onDeploymentFinished(ProcessEngine processEngine) {
    long count = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processDefinitionKey(PROCESS_DEFINITION_KEY).count();
    // only generate if no process instances exist yet to not flood our database every restart of the container
    if (count>0) {
      return;
    }
    
    for (int i = 0; i < 100; i++) {
      processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);      
    }

  }

}
