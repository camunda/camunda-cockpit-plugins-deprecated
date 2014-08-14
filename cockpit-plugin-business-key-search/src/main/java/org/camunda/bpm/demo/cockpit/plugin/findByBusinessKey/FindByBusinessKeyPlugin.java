package org.camunda.bpm.demo.cockpit.plugin.findByBusinessKey;

import java.util.HashSet;
import java.util.Set;

import org.camunda.bpm.cockpit.plugin.spi.impl.AbstractCockpitPlugin;

public class FindByBusinessKeyPlugin extends AbstractCockpitPlugin {

  public static final String ID = "findByBusinessKey";

  public String getId() {
    return ID;
  }

  @Override
  public Set<Class<?>> getResourceClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();

    classes.add(FindByBusinessKeyResource.class);

    return classes;
  }
}
