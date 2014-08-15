package org.camunda.bpm.demo.cockpit.plugin.kpis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.cockpit.plugin.spi.impl.AbstractCockpitPlugin;
import org.camunda.bpm.demo.cockpit.plugin.kpi.resources.KPIPluginRootResource;
import org.camunda.bpm.demo.cockpit.plugin.kpi.resources.KPIResource;

public class KPIPlugin extends AbstractCockpitPlugin {

	public static final String ID = "kpi";

	public String getId() {
		return ID;
	}

	@Override
	public Set<Class<?>> getResourceClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		classes.add(KPIPluginRootResource.class);

		return classes;
	}

	@Override
	public List<String> getMappingFiles() {
		return Arrays
				.asList("org/camunda/bpm/demo/cockpit/plugin/kpi/queries/kpi-queries.xml");
	}

}