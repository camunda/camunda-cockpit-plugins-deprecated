package org.camunda.bpm.cockpit.deploymentPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.cockpit.deploymentPlugin.resources.DeploymentPluginRootResource;
import org.camunda.bpm.cockpit.plugin.spi.impl.AbstractCockpitPlugin;

public class DeploymentPlugin extends AbstractCockpitPlugin {

  public static final String ID = "deployment-plugin";

  public String getId() {
    return ID;
  }

  @Override
  public Set<Class<?>> getResourceClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();

    classes.add(DeploymentPluginRootResource.class);

    return classes;
  }

  @Override
  public List<String> getMappingFiles() {
    return new ArrayList<String>();
  }
}
