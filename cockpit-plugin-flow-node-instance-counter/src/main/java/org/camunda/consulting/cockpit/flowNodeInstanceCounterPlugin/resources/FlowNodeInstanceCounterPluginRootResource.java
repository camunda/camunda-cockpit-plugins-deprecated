package org.camunda.consulting.cockpit.flowNodeInstanceCounterPlugin.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.camunda.bpm.cockpit.plugin.resource.AbstractPluginRootResource;
import org.camunda.consulting.cockpit.flowNodeInstanceCounterPlugin.FlowNodeInstanceCounterPlugin;

@Path("plugin/" + FlowNodeInstanceCounterPlugin.ID)
public class FlowNodeInstanceCounterPluginRootResource extends AbstractPluginRootResource {

  public FlowNodeInstanceCounterPluginRootResource() {
    super(FlowNodeInstanceCounterPlugin.ID);
  }

  @Path("{engineName}/flow-node-instance-count-per-month")
  public FlowNodeInstanceResource getProcessInstanceResource(@PathParam("engineName") String engineName) {
    return subResource(new FlowNodeInstanceResource(engineName), engineName);
  }
}
