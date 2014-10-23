package org.camunda.bpm.cockpit.flowNodeCounterPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.cockpit.flowNodeCounterPlugin.resources.FlowNodeCounterPluginRootResource;
import org.camunda.bpm.cockpit.plugin.spi.impl.AbstractCockpitPlugin;

public class FlowNodeCounterPlugin extends AbstractCockpitPlugin {

  public static final String ID = "flow-node-counter-plugin";

  public String getId() {
    return ID;
  }

  @Override
  public Set<Class<?>> getResourceClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();

    classes.add(FlowNodeCounterPluginRootResource.class);

    return classes;
  }

  @Override
  public List<String> getMappingFiles() {
    return Arrays.asList("org.camunda.bpm.cockpit.flowNodeCounterPlugin".replace(".", "/") + "/sample-query.xml");
  }
}
