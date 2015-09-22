package com.tfsc.kiran.real.time.systems.network.flow.model;

public class Task {

	protected int taskId;
	protected int period;
	protected float executionTime;
	protected int relativeDeadline;
	private float phaseTime;

	public Task(int taskId, int period, float executionTime,
			int relativeDeadline) {
		super();
		this.taskId = taskId;
		this.period = period;
		this.executionTime = executionTime;
		this.relativeDeadline = relativeDeadline;
	}

	public Task(int taskId, int period, float executionTime,
			int relativeDeadline, float phaseTime) {
		this(taskId, period, executionTime, relativeDeadline);
		this.phaseTime = phaseTime;
	}

	public Task(Task task) {
		this.taskId = task.getTaskId();
		this.period = task.getPeriod();
		this.executionTime = task.getExecutionTime();
		this.relativeDeadline = task.getRelativeDeadline();
		this.phaseTime = task.getPhaseTime();
	}

	public int getTaskId() {
		return taskId;
	}

	public int getPeriod() {
		return period;
	}

	public float getExecutionTime() {
		return executionTime;
	}

	public int getRelativeDeadline() {
		return relativeDeadline;
	}

	public float getPhaseTime() {
		return phaseTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[TaskId:").append(taskId).append(", Period:")
				.append(period).append(", ExecTime: ").append(executionTime)
				.append(", RelativeDeadline: ").append(relativeDeadline)
				.append("]");
		return builder.toString();
	}

}
