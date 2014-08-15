package org.camunda.bpm.demo.cockpit.plugin.kpi.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginRootResource;
import org.camunda.bpm.demo.cockpit.plugin.kpis.KPIPlugin;

@Path("plugin/" + KPIPlugin.ID)
public class KPIPluginRootResource extends AbstractCockpitPluginRootResource {

	public KPIPluginRootResource() {
		super(KPIPlugin.ID);
	}

	@Path("{engineName}/kpi")
	public KPIResource getProcessInstanceResource(
			@PathParam("engineName") String engineName) {
		return subResource(new KPIResource(engineName), engineName);
	}

}