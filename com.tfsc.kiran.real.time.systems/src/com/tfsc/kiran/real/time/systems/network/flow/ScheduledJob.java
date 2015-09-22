package com.tfsc.kiran.real.time.systems.network.flow;

public class ScheduledJob extends Job {

	private float executedTime = 0f;

	ScheduledJob(Job job, float executedTime) {
		super(job);
		this.executedTime = executedTime;
	}

	public float getExecutedTime() {
		return executedTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExecutedTime: ").append(executedTime)
				.append(super.toString());
		return builder.toString();
	}

}
