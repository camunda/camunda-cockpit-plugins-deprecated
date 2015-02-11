package com.camunda.bpm.demo.cockpit.plugin.rest.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginRootResource;

import com.camunda.bpm.demo.cockpit.plugin.rest.RestClientPlugin;

@Path("plugin/" + RestClientPlugin.ID)
public class SamplePluginRootResource extends AbstractCockpitPluginRootResource {

  public SamplePluginRootResource() {
    super(RestClientPlugin.ID);
  }

  @Path("{engineName}")
  public RestClientResource getProcessInstanceResource(@PathParam("engineName") String engineName) {
    return subResource(new RestClientResource(engineName), engineName);
  }
}
