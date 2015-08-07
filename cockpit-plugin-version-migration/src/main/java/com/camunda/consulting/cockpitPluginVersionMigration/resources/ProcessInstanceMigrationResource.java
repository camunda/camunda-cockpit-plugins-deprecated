package com.camunda.consulting.cockpitPluginVersionMigration.resources;

import java.io.IOException;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response.Status;

import org.camunda.bpm.cockpit.db.QueryParameters;
import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginResource;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cmd.SetProcessDefinitionVersionCmd;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.db.entitymanager.operation.DbEntityOperation;
import org.camunda.bpm.engine.impl.db.entitymanager.operation.DbOperation;
import org.camunda.bpm.engine.impl.history.event.HistoricProcessInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.event.HistoryEventType;
import org.camunda.bpm.engine.impl.history.event.HistoryEventTypes;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;

import com.camunda.consulting.cockpitPluginVersionMigration.dto.HistoricProcessInstanceDto;
import com.camunda.consulting.cockpitPluginVersionMigration.dto.ProcessInstanceDto;
import com.camunda.consulting.cockpitPluginVersionMigration.dto.VersionDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcessInstanceMigrationResource extends AbstractCockpitPluginResource {
  
  private static final Logger log = Logger.getLogger(ProcessInstanceMigrationResource.class.getName());

  public ProcessInstanceMigrationResource(String engineName) {
    super(engineName);
  }

  @POST
  @Path("{id}")
  public void migrateProcessInstance(@PathParam("id") String processInstanceId, String payload) {
    log.fine("process instance id" + processInstanceId + ", payload: " + payload);
    int newVersion;
    if (payload != null && payload.length() > 0) {
      VersionDto versionDto = null;
      try {
        versionDto = new ObjectMapper().readValue(payload, VersionDto.class);
      } catch (JsonParseException e) {
        throw new InvalidRequestException(Status.BAD_REQUEST, "JsonParseException: " + e.getMessage());
      } catch (JsonMappingException e) {
        throw new InvalidRequestException(Status.BAD_REQUEST, "JsonMappingException: " + e.getMessage());
      } catch (IOException e) {
        throw new InvalidRequestException(Status.BAD_REQUEST, "IOException: " + e.getMessage());
      }
      newVersion = versionDto.getVersion();
    } else {
      throw new InvalidRequestException(Status.BAD_REQUEST, "new version is missing");
    }
    
    log.fine("new version: " + newVersion);
    
    SetProcessDefinitionVersionCmd command = 
       new SetProcessDefinitionVersionCmd(processInstanceId, newVersion);
    ProcessEngineImpl processEngineImpl = (ProcessEngineImpl) ProcessEngines.getProcessEngine(engineName);
    processEngineImpl
         .getProcessEngineConfiguration()
         .getCommandExecutorTxRequired().execute(command);

    log.info("setProcessDefinitionVersionCmd finished");
    
//    // update the historic process instance
////    log.info("statement mappings: " + processEngineImpl.getProcessEngineConfiguration().getDbSqlSessionFactory().getStatementMappings());
//    // returns null
//    
//    // use either the processEngine command context
//    processEngineImpl.getProcessEngineConfiguration().getCommandExecutorTxRequired().execute(
//        
//        // or the cockpit command context
////    getCommandExecutor().executeCommand(
//        // create a new command to update the historic process instance by SQL 
//        new Command<Void>() {
//      
//      String processInstanceId;
//      int newVersion;
//      
//      @Override
//      public Void execute(CommandContext commandContext) {
//        log.info(processInstanceId);
//        // get the new process definition of the process instance, because the transaction is already completed
//        // try it either by cockpit query service - how to set dynamic parameters to the query?
//        ProcessInstanceDto processInstanceDto = new ProcessInstanceDto();
//        processInstanceDto.setProcessInstanceId(processInstanceId);
////        ProcessInstanceDto processInstance = getQueryService().executeQuery("getProcessDefinition", new QueryParameters<ProcessInstanceDto>());
//        
//        // or try it with the query from the plugin-mapping.xml in the process engine commandContext:
//        //  Mapped Statements collection does not contain value for getProcessDefinition
////        ProcessInstanceDto processInstance = commandContext.getDbSqlSession().getSqlSession().selectOne("getProcessDefinition", processInstanceDto);
//        
//        // or try it with the execution manager. It works only in the commandContext from the process engine
//        // here we get the new processDefinitionId
//        ExecutionEntity processInstance = commandContext.getExecutionManager().findExecutionById(processInstanceId);
//     
//        log.info("new process definition? " + processInstance.getProcessDefinitionId());
//        
//        // try to execute the update from the migrate-historic-processinstance-update.xml
//        HistoricProcessInstanceDto historicProcessInstanceDto = new HistoricProcessInstanceDto();
//        historicProcessInstanceDto.setId_(processInstanceId);
//        historicProcessInstanceDto.setProcessDefinitionId_(processInstance.getProcessDefinitionId());
//        // ### Error updating database.  Cause: java.lang.IllegalArgumentException: Mapped Statements collection does not contain value for updateHistoricProcessInstance
//        commandContext.getDbSqlSession().getSqlSession().update("updateHistoricProcessInstance", historicProcessInstanceDto);
//        return null;
//      }
//      
//      public Command<Void> init(String processInstanceId, int newVersion) {
//        this.processInstanceId = processInstanceId;
//        this.newVersion = newVersion;
//        return this;
//      }
//      
//    }.init(processInstanceId, newVersion));
    
    // tried to fire the history event manually, butt didn't work either
//  String newProcessDefinitionId = ;
//  
//  ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) getProcessEngine().getProcessEngineConfiguration();
//  
//  HistoryEvent historyEvent = new HistoryEvent();
//  historyEvent.setEventType(HistoryEventTypes.TASK_INSTANCE_COMPLETE.getEntityType());
//  processEngineConfiguration.getHistoryEventHandler().handleEvent(historyEvent);
//  processEngineConfiguration.getClass();
//  DbEntityOperation operation = new DbEntityOperation();
//  HistoricProcessInstanceEventEntity historicProcessInstanceEntity = new HistoricProcessInstanceEventEntity();
//  historicProcessInstanceEntity.setId(processInstanceId);
//  historicProcessInstanceEntity.setProcessDefinitionId(newProcessDefinitionId);
//  operation.setEntity(historicProcessInstanceEntity);
//  Context.getCommandContext().getDbSqlSession().executeDbOperation(operation);
    
  }
}
