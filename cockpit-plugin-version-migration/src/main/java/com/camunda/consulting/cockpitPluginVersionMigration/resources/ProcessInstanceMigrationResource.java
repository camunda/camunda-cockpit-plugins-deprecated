package com.camunda.consulting.cockpitPluginVersionMigration.resources;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response.Status;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginResource;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.impl.cmd.SetProcessDefinitionVersionCmd;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;

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
    ((ProcessEngineImpl) ProcessEngines.getProcessEngine(engineName))
         .getProcessEngineConfiguration()
         .getCommandExecutorTxRequired().execute(command);
  }
}
