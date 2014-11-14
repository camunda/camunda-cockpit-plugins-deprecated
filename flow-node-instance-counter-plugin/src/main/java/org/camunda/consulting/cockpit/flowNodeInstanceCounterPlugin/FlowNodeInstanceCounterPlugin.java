package org.camunda.consulting.cockpit.flowNodeInstanceCounterPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.consulting.cockpit.flowNodeInstanceCounterPlugin.resources.FlowNodeInstanceCounterPluginRootResource;
import org.camunda.bpm.cockpit.plugin.spi.impl.AbstractCockpitPlugin;

public class FlowNodeInstanceCounterPlugin extends AbstractCockpitPlugin {

  public static final String ID = "flow-node-instance-counter-plugin";

  public String getId() {
    return ID;
  }

  @Override
  public Set<Class<?>> getResourceClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();

    classes.add(FlowNodeInstanceCounterPluginRootResource.class);

    return classes;
  }

  @Override
  public List<String> getMappingFiles() {
    return Arrays.asList("org.camunda.consulting.cockpit.flowNodeInstanceCounterPlugin".replace(".", "/") + "/flow-node-instance-query.xml");
  }
}
