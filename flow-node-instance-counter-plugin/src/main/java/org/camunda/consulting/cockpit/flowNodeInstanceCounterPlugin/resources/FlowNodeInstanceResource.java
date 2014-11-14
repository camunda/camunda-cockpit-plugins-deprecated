package org.camunda.consulting.cockpit.flowNodeInstanceCounterPlugin.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.camunda.bpm.cockpit.db.QueryParameters;
import org.camunda.bpm.cockpit.plugin.resource.AbstractPluginResource;

import org.camunda.consulting.cockpit.flowNodeInstanceCounterPlugin.db.FlowNodeInstanceCountDto;

public class FlowNodeInstanceResource extends AbstractPluginResource {

  public FlowNodeInstanceResource(String engineName) {
    super(engineName);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<FlowNodeInstanceCountDto> getProcessInstanceCounts() {

    return getQueryService()
        .executeQuery(
          "cockpit.flow-node-instance-counter-plugin.selectFlowNodeInstanceCountPerMonth",
          new QueryParameters<FlowNodeInstanceCountDto>());
  }
}
