package com.tfsc.kiran.real.time.systems.network.flow;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class Frame {
	private int frameNumber;
	private float timeLeft = 0f;
	private float totalExecutionTime = 0f;
	private List<ScheduledJob> scheduledJobs = new LinkedList<ScheduledJob>();

	public List<ScheduledJob> getScheduledJobs() {
		return scheduledJobs;
	}

	Frame(int frameNumber, float frameSize) {
		this.frameNumber = frameNumber;
		this.timeLeft = frameSize;
	}

	public void addJobToFrame(Job job, float executionTimeRequested) {
		float executedTime = (timeLeft - executionTimeRequested) > 0f ? executionTimeRequested
				: timeLeft;
		scheduledJobs.add(new ScheduledJob(job, executedTime));
		timeLeft -= executedTime;
		totalExecutionTime += executedTime;
		if (timeLeft < 0f)
			System.err.println("Time left is less than 0. Check computation."
					+ this);
	}

	public float getTimeLeft() {
		return timeLeft;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void moveForward(float duration) {
		timeLeft -= duration;
	}

	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.####");
		StringBuilder builder = new StringBuilder();
		builder.append("{FrameNumber: ").append(frameNumber)
				.append(", ExecutionTime:")
				.append(format.format(totalExecutionTime))
				.append(", ScheduledJobs:").append(scheduledJobs).append("}\n");
		return builder.toString();
	}
}
