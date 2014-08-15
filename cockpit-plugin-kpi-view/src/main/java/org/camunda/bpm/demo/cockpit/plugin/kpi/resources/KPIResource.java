package org.camunda.bpm.demo.cockpit.plugin.kpi.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;

import org.camunda.bpm.cockpit.plugin.resource.AbstractCockpitPluginResource;
import org.camunda.bpm.demo.cockpit.plugin.kpis.KPIDto;

public class KPIResource extends AbstractCockpitPluginResource {
  
  protected static final Logger LOGGER = Logger.getLogger(KPIResource.class.getName());

	public KPIResource(String engineName) {
		super(engineName);
	}

	@GET
	public List<KPIDto> getKPIs(@QueryParam("processKey") String processKey,
			@QueryParam("startDate") String startDateStr,
			@QueryParam("endDate") String endDateStr) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		
		KPIQueryParameters parameter = new KPIQueryParameters();

		try {
			parameter.setStartDate(df.parse(startDateStr));
			parameter.setEndDate(df.parse(endDateStr));		
		}
		catch(Exception ex)
		{
		  LOGGER.log(Level.WARNING, "could not parse date", ex);
		  Calendar calendar = Calendar.getInstance();
		  
		  // default
		  calendar.add(Calendar.DAY_OF_YEAR, -1);
		  parameter.setStartDate(calendar.getTime());
		  
      calendar.add(Calendar.DAY_OF_YEAR, 2);
      parameter.setEndDate(calendar.getTime());			
		}
		
		parameter.setProcessKey(processKey);
		
		return getQueryService().executeQuery(
				"cockpit.kpi.selectKPIByProcessDefinition",
				// TODO: Null possible?
				parameter);
	}
}