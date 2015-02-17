package com.camunda.bpm.demo.cockpit.plugin.rest.resources;

public class MessageDto {

  public MessageDto() {
  }

  public MessageDto(String name) {
    this.name = name;
  }

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
