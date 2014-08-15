package com.camunda.bpm.demo.cockpit.plugin.kpiview.sample;

import java.util.Calendar;
import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GenerateRandomDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    Random random = new Random();
    
    if (random.nextBoolean()) {
      execution.setVariable("randomDecision", "a");
    } else {
      execution.setVariable("randomDecision", "b");      
    }
    
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.SECOND, random.nextInt(60));
    
    execution.setVariable("randomDate", calendar.getTime());
  }

}
