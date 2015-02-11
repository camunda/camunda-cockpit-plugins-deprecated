package com.camunda.bpm.demo.cockpit.plugin.rest.resources;

public class RestRequest {

  private String method;
  private String payload;
  private String url;
  
  public String getMethod() {
    return method;
  }
  public void setMethod(String method) {
    this.method = method;
  }
  public String getPayload() {
    return payload;
  }
  public void setPayload(String payload) {
    this.payload = payload;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  @Override
  public String toString() {
    return "RestRequest [method=" + method + ", payload=" + payload + ", url=" + url + "]";
  }
  
}
