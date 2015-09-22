package com.tfsc.kiran.real.time.systems.network.flow.model;

public class Job extends Task {

	private int jobId;
	private float releaseTime;
	private Task task;
	private float absoluteDeadline;

	public Job(int taskId, int period, float executionTime, int relativeDeadline) {
		super(taskId, period, executionTime, relativeDeadline);
		this.absoluteDeadline = releaseTime + relativeDeadline;
	}

	public Job(int jobId, float releaseTime, Task task) {
		super(task.getTaskId(), task.getPeriod(), task.getExecutionTime(), task
				.getRelativeDeadline(), task.getPhaseTime());
		this.jobId = jobId;
		this.releaseTime = releaseTime + getPhaseTime();
		this.task = task;
		this.absoluteDeadline = this.releaseTime + relativeDeadline;
	}

	public Job(Job job) {
		super(job.getTask());
		this.jobId = job.getJobId();
		this.releaseTime = job.getReleaseTime();
		this.task = job.getTask();
		this.absoluteDeadline = job.getAbsoluteDeadline();
	}

	public int getJobId() {
		return jobId;
	}

	public float getReleaseTime() {
		return releaseTime;
	}

	public Task getTask() {
		return task;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[JobId:").append(jobId)
		// .append(", ReleaseTime:").append(releaseTime)
				.append(", TaskId:").append(taskId)
				// .append(", Period:").append(period).append(", ExecTime: ")
				// .append(executionTime).append(", RelativeDeadline: ")
				// .append(relativeDeadline).append(", AbsoluteDeadline:")
				// .append(absoluteDeadline)
				.append("]");
		return builder.toString();
	}

	public float getAbsoluteDeadline() {
		return absoluteDeadline;
	}

}
