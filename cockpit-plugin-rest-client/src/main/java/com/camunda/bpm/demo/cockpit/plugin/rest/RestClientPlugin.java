package com.camunda.bpm.demo.cockpit.plugin.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.camunda.bpm.demo.cockpit.plugin.rest.resources.SamplePluginRootResource;

import org.camunda.bpm.cockpit.plugin.spi.impl.AbstractCockpitPlugin;

public class RestClientPlugin extends AbstractCockpitPlugin {

  public static final String ID = "rest-client";

  public String getId() {
    return ID;
  }

  @Override
  public Set<Class<?>> getResourceClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();

    classes.add(SamplePluginRootResource.class);

    return classes;
  }

  @Override
  public List<String> getMappingFiles() {
    return new ArrayList<String>();
  }
}
