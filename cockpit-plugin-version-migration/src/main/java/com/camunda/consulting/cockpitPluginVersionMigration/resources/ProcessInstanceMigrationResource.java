package com.camunda.consulting.cockpitPluginVersionMigration.resources;

import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginResource;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.impl.cmd.SetProcessDefinitionVersionCmd;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import com.camunda.consulting.cockpitPluginVersionMigration.dto.VersionDto;

public class ProcessInstanceMigrationResource extends AbstractCockpitPluginResource {
  
  private static final Logger log = Logger.getLogger(ProcessInstanceMigrationResource.class.getName());

  public ProcessInstanceMigrationResource(String engineName) {
    super(engineName);
  }

  @POST
  @Path("{id}")
  public void migrateProcessInstance(@PathParam("id") final String processInstanceId, VersionDto versionDto) {
    log.fine("process instance id" + processInstanceId + ", payload: " + versionDto);
    if (versionDto==null) {
      throw new IllegalArgumentException("version to migrate to must be given");
    }
    final int newVersion = versionDto.getVersion();
   
    log.fine("new version: " + newVersion);
    
    SetProcessDefinitionVersionCmd command = new SetProcessDefinitionVersionCmd(processInstanceId, newVersion);
    ProcessEngineImpl processEngineImpl = (ProcessEngineImpl) ProcessEngines.getProcessEngine(engineName);
    processEngineImpl.getProcessEngineConfiguration().getCommandExecutorTxRequired().execute(command);

    log.info("setProcessDefinitionVersionCmd finished");
    
    // get the new process definition id from the migrated process instance.
    // This is the easiest and works because the above transaction is already completed
    ProcessInstance pi = getProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    final String processDefinitionId = pi.getProcessDefinitionId();
    
    // now do a SQL update via MyBatis
    getCommandExecutor().executeCommand(new Command<Void>() {
      
      @Override
      public Void execute(CommandContext commandContext) {
        log.info("Now tweaking histor of " + processInstanceId);
        
        HistoricProcessInstanceEntity processInstanceDto = new HistoricProcessInstanceEntity();
        processInstanceDto.setProcessInstanceId(processInstanceId);
        processInstanceDto.setProcessDefinitionId(processDefinitionId);
        
        try {       
          // execute the update from the migrate-historic-processinstance-update.xml
          commandContext.getDbSqlSession().getSqlSession().update("cockpit.version-migration.updateHistoricProcessInstance", processInstanceDto);
          
          log.info("History tweaked successfully");
        }
        catch (Throwable ex) {
          log.log(Level.INFO, "Could not tweak history due to exception", ex);
          // Classloading problems!
          // caused by: java.lang.NoClassDefFoundError: org/apache/ibatis/session/SqlSession
          // at com.camunda.consulting.cockpitPluginVersionMigration.resources.ProcessInstanceMigrationResource$1.execute(ProcessInstanceMigrationResource.java:63) [cockpit-plugin-version-migration.jar:]
        }
        return null;
      }
    });
    
  }
}
