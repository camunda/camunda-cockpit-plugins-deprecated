package org.camunda.bpm.cockpit.flowNodeCounterPlugin.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.camunda.bpm.cockpit.db.QueryParameters;
import org.camunda.bpm.cockpit.plugin.resource.AbstractPluginResource;

import org.camunda.bpm.cockpit.flowNodeCounterPlugin.db.FlowNodeCountDto;

public class FlowNodesResource extends AbstractPluginResource {

  public FlowNodesResource(String engineName) {
    super(engineName);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<FlowNodeCountDto> getFlowNodeCounts() {

    return getQueryService()
        .executeQuery(
          "cockpit.sample.selectFlowNodeCounterPerMonth",
          new QueryParameters<FlowNodeCountDto>());
  }
}
