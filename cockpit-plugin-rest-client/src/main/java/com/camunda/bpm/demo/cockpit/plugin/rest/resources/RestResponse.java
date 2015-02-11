package com.camunda.bpm.demo.cockpit.plugin.rest.resources;

public class RestResponse {

  private String payload;
  private String httpCode;
  private String info;
  
  public String getPayload() {
    return payload;
  }
  public void setPayload(String payload) {
    this.payload = payload;
  }
  public String getHttpCode() {
    return httpCode;
  }
  public void setHttpCode(String httpCode) {
    this.httpCode = httpCode;
  }
  public String getInfo() {
    return info;
  }
  public void setInfo(String info) {
    this.info = info;
  }
}
