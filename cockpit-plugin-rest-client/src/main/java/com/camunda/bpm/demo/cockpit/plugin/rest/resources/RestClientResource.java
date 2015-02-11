package com.camunda.bpm.demo.cockpit.plugin.rest.resources;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginResource;

public class RestClientResource extends AbstractCockpitPluginResource {

  public RestClientResource(String engineName) {
    super(engineName);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse executeRestRequest(RestRequest request) throws Exception {

    System.out.println(request);

    RestResponse restResponse = new RestResponse();

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
    return restResponse;
  }
}
