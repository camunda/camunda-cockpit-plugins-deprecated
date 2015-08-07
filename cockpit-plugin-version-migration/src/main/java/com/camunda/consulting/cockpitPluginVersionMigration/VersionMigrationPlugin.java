package com.camunda.consulting.cockpitPluginVersionMigration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.camunda.consulting.cockpitPluginVersionMigration.resources.VersionMigrationPluginRootResource;

import org.camunda.bpm.cockpit.plugin.spi.impl.AbstractCockpitPlugin;

public class VersionMigrationPlugin extends AbstractCockpitPlugin {

  public static final String ID = "version-migration";

  public String getId() {
    return ID;
  }

  @Override
  public Set<Class<?>> getResourceClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();

    classes.add(VersionMigrationPluginRootResource.class);

    return classes;
  }
  
  @Override
  public List<String> getMappingFiles() {
    return Arrays.asList("com.camunda.consulting.cockpitPluginVersionMigration".replace(".", "/") + "/migrate-historic-processinstance-update.xml");
  }
}
