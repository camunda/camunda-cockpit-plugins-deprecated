package org.camunda.bpm.cockpit.flowNodeCounterPlugin.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.camunda.bpm.cockpit.plugin.resource.AbstractPluginRootResource;
import org.camunda.bpm.cockpit.flowNodeCounterPlugin.FlowNodeCounterPlugin;

@Path("plugin/" + FlowNodeCounterPlugin.ID)
public class FlowNodeCounterPluginRootResource extends AbstractPluginRootResource {

  public FlowNodeCounterPluginRootResource() {
    super(FlowNodeCounterPlugin.ID);
  }

  @Path("{engineName}/flow-node-count-per-month")
  public FlowNodesResource getProcessInstanceResource(@PathParam("engineName") String engineName) {
    return subResource(new FlowNodesResource(engineName), engineName);
  }
}
