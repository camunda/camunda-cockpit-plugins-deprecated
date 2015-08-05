package com.camunda.consulting.cockpitPluginVersionMigration.resources;

import java.util.logging.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginRootResource;

import com.camunda.consulting.cockpitPluginVersionMigration.VersionMigrationPlugin;

@Path("plugin/" + VersionMigrationPlugin.ID)
public class VersionMigrationPluginRootResource extends AbstractCockpitPluginRootResource {
  
  private static final Logger log = Logger.getLogger(VersionMigrationPluginRootResource.class.getName());

  public VersionMigrationPluginRootResource() {
    super(VersionMigrationPlugin.ID);
  }

  @Path("{engineName}/process-instance-migration")
  public ProcessInstanceMigrationResource getProcessInstanceMigrationResource(@PathParam("engineName") String engineName) {
    log.fine("process instance migration");
    return subResource(new ProcessInstanceMigrationResource(engineName), engineName);
  }
}
