package org.camunda.bpm.cockpit.deploymentPlugin.resources;

import javax.ws.rs.Path;
import org.camunda.bpm.cockpit.plugin.resource.AbstractPluginRootResource;
import org.camunda.bpm.cockpit.deploymentPlugin.DeploymentPlugin;

@Path("plugin/" + DeploymentPlugin.ID)
public class DeploymentPluginRootResource extends AbstractPluginRootResource {

  public DeploymentPluginRootResource() {
    super(DeploymentPlugin.ID);
  }
}
