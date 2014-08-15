package org.camunda.bpm.demo.cockpit.plugin.kpi.resources;

import java.util.Date;

import org.camunda.bpm.cockpit.db.QueryParameters;
import org.camunda.bpm.demo.cockpit.plugin.kpis.KPIDto;

public class KPIQueryParameters extends QueryParameters<KPIDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2506721992235082326L;
	private Date startDate;
	private Date endDate;
	private String processKey;

	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
