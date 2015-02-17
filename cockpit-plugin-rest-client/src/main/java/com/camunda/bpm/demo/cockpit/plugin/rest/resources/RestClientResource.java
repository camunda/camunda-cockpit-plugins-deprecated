package com.camunda.bpm.demo.cockpit.plugin.rest.resources;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginResource;
import org.camunda.bpm.engine.runtime.EventSubscription;

public class RestClientResource extends AbstractCockpitPluginResource {

  private static Logger LOG = Logger.getLogger(RestClientResource.class.getName());

  public RestClientResource(String engineName) {
    super(engineName);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse executeRestRequest(RestRequest request) {
    RestResponse restResponse = new RestResponse();
    try {

      if (request.getMethod().equals("GET")) {
        URL obj = new URL(request.getUrl());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();

        restResponse.setHttpCode(String.valueOf(responseCode));
        restResponse.setPayload(response.toString());

      } else if (request.getMethod().equals("POST")) {
        URL obj = new URL(request.getUrl());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(request.getPayload());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();

        restResponse.setHttpCode(String.valueOf(responseCode));
        restResponse.setPayload(response.toString());
      }
    } catch (Exception ex) {
      LOG.log(Level.WARNING, "Could not execute REST request", ex);
      restResponse.setHttpCode(ex.getClass().getName());

      StringWriter exceptionStringWriter = new StringWriter();
      PrintWriter pw = new PrintWriter(exceptionStringWriter);
      ex.printStackTrace(pw);
      restResponse.setPayload(exceptionStringWriter.toString());
    }
    return restResponse;
  }

  /**
   * Hint: this is potentially slow if we have many message subscriptions. An
   * improved version would query the "distinct message name" directly via SQL.
   */
  @GET
  @Path("info/message")
  @Produces(MediaType.APPLICATION_JSON)  
  public List<MessageDto> getMessagesWithSubscription() {
    List<String> messageNames = new ArrayList<String>();
    
    List<EventSubscription> messageSubscriptions = super.getProcessEngine().getRuntimeService().createEventSubscriptionQuery().eventType("message").list();
    for (EventSubscription eventSubscription : messageSubscriptions) {
      if (!messageNames.contains(eventSubscription.getEventName())) {
        messageNames.add(eventSubscription.getEventName());
      }
    }
    
    Collections.sort(messageNames);
    
    ArrayList<MessageDto> messages = new ArrayList<MessageDto>();
    for (String name: messageNames) {
      messages.add(new MessageDto(name));
    }

    return messages;
  }
}
