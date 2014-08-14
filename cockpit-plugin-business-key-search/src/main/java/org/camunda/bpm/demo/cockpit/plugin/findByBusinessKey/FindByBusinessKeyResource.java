package org.camunda.bpm.demo.cockpit.plugin.findByBusinessKey;

import javax.ws.rs.Path;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginRootResource;

/**
 * root resource to select sub resource for the selected engine (as you could run multiple engines)
 * 
 * @author ruecker
 *
 */
@Path("plugin/" + FindByBusinessKeyPlugin.ID)
public class FindByBusinessKeyResource extends AbstractCockpitPluginRootResource {

  public FindByBusinessKeyResource() {
    super(FindByBusinessKeyPlugin.ID);
  }

}