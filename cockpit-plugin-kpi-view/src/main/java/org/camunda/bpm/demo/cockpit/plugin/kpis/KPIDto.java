package org.camunda.bpm.demo.cockpit.plugin.kpis;

public class KPIDto {
	private String processDefinitionKey;
	private String stage;
	private int instanceCount;
	private double averageDuration;
	private int maxDuration;

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStage() {
		return stage;
	}

	public void setInstanceCount(int instanceCount) {
		this.instanceCount = instanceCount;
	}

	public int getInstanceCount() {
		return instanceCount;
	}

	public void setAverageDuration(double averageDuration) {
		this.averageDuration = averageDuration;
	}

	public double getAverageDuration() {
		return averageDuration;
	}

	public void setMaxDuration(int maxDuration) {
		this.maxDuration = maxDuration;
	}

	public int getMaxDuration() {
		return maxDuration;
	}
}
