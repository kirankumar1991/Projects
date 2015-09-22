package com.tfsc.kiran.real.time.systems.network.flow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IterativeNetworkFlow {

	static float FRAME_SIZE = 40;

	public static void main(String[] args) {
		String syncInput = "( 0, 250, 6.4545, 250 ),( 0, 250, 30.1303, 250 ),( 0, 250, 0.3437, 250 ),( 0, 250, 0.1022, 250 ),( 0, 250, 5.7349, 250 ),( 0, 250, 5.7557, 250 ),( 0, 250, 5.7974, 250 ),( 0, 250, 5.8807, 250 ),( 0, 250, 6.0472, 250 ),( 0, 250, 4.3071, 250 ),( 0, 250, 7.7672, 250 ),( 0, 250, 14.6875, 250 ),( 0, 250, 7.183, 250 ),( 0, 250, 7.3999, 250 ),( 0, 250, 7.8337, 250 ),( 0, 250, 8.7012, 250 ),( 0, 250, 8.7012, 250 ),( 0, 250, 8.1264, 250 ),( 0, 250, 8.1264, 250 ),( 0, 250, 8.4815, 250 ),( 0, 250, 9.1918, 250 ),( 0, 250, 9.1918, 250 ),( 0, 250, 3.217, 250 ),( 0, 250, 3.5179, 250 ),( 0, 250, 3.6363, 250 ),( 0, 250, 5.1914, 250 ),( 0, 250, 0.1496, 250 ),( 0, 500, 3.3671, 500 ),( 0, 500, 3.3671, 500 ),( 0, 500, 3.3671, 500 ),( 0, 500, 3.3671, 500 ),( 0, 500, 3.3671, 500 ),( 0, 2000, 3.1913, 2000 ),( 0, 2000, 5.1122, 2000 ),( 0, 2000, 0.5047, 2000 ),( 0, 2000, 0.5906, 2000 ),( 0, 6000, 2.4799, 6000 ),( 0, 6000, 0.199, 6000 ),( 0, 6000, 0.6898, 6000 )";
		String asyncInput = "( 0, 250, 6.4545, 250 ),( 0.001, 250, 30.1303, 250 ),( 0.002, 250, 0.3437, 250 ),( 0.003, 250, 0.1022, 250 ),( 0.004, 250, 5.7349, 250 ),( 0.004, 250, 5.7557, 250 ),( 0.004, 250, 5.7974, 250 ),( 0.004, 250, 5.8807, 250 ),( 0.004, 250, 6.0472, 250 ),( 0.004, 250, 4.3071, 250 ),( 0.004, 250, 7.7672, 250 ),( 0.004, 250, 14.6875, 250 ),( 0.005, 250, 7.183, 250 ),( 0.005, 250, 7.3999, 250 ),( 0.005, 250, 7.8337, 250 ),( 0.005, 250, 8.7012, 250 ),( 0.005, 250, 8.7012, 250 ),( 0.006, 250, 8.1264, 250 ),( 0.006, 250, 8.1264, 250 ),( 0.006, 250, 8.4815, 250 ),( 0.006, 250, 9.1918, 250 ),( 0.006, 250, 9.1918, 250 ),( 250.007, 500, 3.3671, 500 ),( 250.007, 500, 3.3671, 500 ),( 250.007, 500, 3.3671, 500 ),( 250.007, 500, 3.3671, 500 ),( 250.007, 500, 3.3671, 500 ),( 250.008, 250, 3.217, 250 ),( 250.009, 250, 3.5179, 250 ),( 250.010, 250, 3.6363, 250 ),( 250.010, 250, 5.1914, 250 ),( 250.011, 250, 0.1496, 250 ),( 1750.011, 2000, 3.1913, 2000 ),( 1750.011, 2000, 5.1122, 2000 ),( 1750.012, 2000, 0.5047, 2000 ),( 1750.012, 2000, 0.5906, 2000 ),( 5750.012, 6000, 2.4799, 6000 ),( 5750.013, 6000, 0.199, 6000 ),( 5750.013, 6000, 0.6898, 6000 )";
		System.out.println("Schedule for Sync Task Set:");
		computeScheduleForTaskSet(syncInput, TASK_TYPE.SYNC);
		System.out.println("\n\n\nSchedule for Async Task Set:");
		computeScheduleForTaskSet(asyncInput, TASK_TYPE.ASYNC);

	}

	private static void computeScheduleForTaskSet(String asyncInput,
			TASK_TYPE taskType) {
		Map<Integer, Integer> taskVsCurrentJobId = new HashMap<Integer, Integer>();
		InputParser parser = new InputParser(asyncInput);
		List<Task> taskList = parser.getTaskList();
		int hyperperiod = computeHyperPeriod(taskList);
		List<Job> jobList = computeJobListForTasks(taskList, hyperperiod,
				taskType, taskVsCurrentJobId);
		List<Frame> frames = generateFrames(hyperperiod);
		computeSchedule(taskList, jobList, frames, hyperperiod);
	}

	private static void computeSchedule(List<Task> taskList, List<Job> jobList,
			List<Frame> frames, int hyperperiod) {
		List<Job> missedJobs = new LinkedList<Job>();
		Iterator<Job> jobIterator = jobList.iterator();
		Job currentJob = jobIterator.next();
		float executionTimeLeftForCurrentJob = currentJob.getExecutionTime();
		float currentTime = 0f;
		Iterator<Frame> framesIterator = frames.iterator();

		for (Frame frame = framesIterator.next(); framesIterator.hasNext()
				&& jobIterator.hasNext();) {
			for (; jobIterator.hasNext(); currentJob = jobIterator.next(), executionTimeLeftForCurrentJob = currentJob
					.getExecutionTime()) {
				while ((frame.getTimeLeft() == 0f || currentJob
						.getReleaseTime() > frame.getFrameNumber() * FRAME_SIZE
						* 1.0f)
						&& framesIterator.hasNext()) {
					currentTime = frame.getFrameNumber() * FRAME_SIZE;
					frame = framesIterator.next();
				}
				if (!framesIterator.hasNext() && frame.getTimeLeft() == 0f)
					break;
				if (currentJob.getReleaseTime() > currentTime
						&& currentJob.getReleaseTime() < frame.getFrameNumber()
								* FRAME_SIZE) {
					float noExecutionPeriod = currentJob.getReleaseTime()
							- currentTime;
					currentTime += noExecutionPeriod;
					frame.moveForward(noExecutionPeriod);
				}
				if (executionTimeLeftForCurrentJob == 0f) {
					if (!jobIterator.hasNext()) {
						System.out.println("Scheduling complete!!");
						break;
					}
					continue;
				}
				if (currentTime >= currentJob.getAbsoluteDeadline()
						|| (currentTime + executionTimeLeftForCurrentJob) > currentJob
								.getAbsoluteDeadline()) {
					System.err.println("Job missed deadline!!. Current Time: "
							+ currentTime + " Job details" + currentJob);
					missedJobs.add(currentJob);
					if (!jobIterator.hasNext()) {
						System.out.println("Scheduling complete!!");
						break;
					}
					continue;
				}

				frame.addJobToFrame(currentJob, executionTimeLeftForCurrentJob);
				float jobExecutedTime = frame.getScheduledJobs()
						.get(frame.getScheduledJobs().size() - 1)
						.getExecutedTime();
				executionTimeLeftForCurrentJob -= jobExecutedTime;
				if (executionTimeLeftForCurrentJob < 0f)
					System.err
							.println("Execution time left for job is less than 0. Check computation logic!. "
									+ frame);
				currentTime += jobExecutedTime;
			}

		}
		if (jobIterator.hasNext()) {
			Job job = jobIterator.next();
			System.err.println("Job " + job + " were not allocated");
		}
		for (Job job : missedJobs) {
			System.err.println("Job " + job + "missed deadline");
		}
		System.out.println("Result: " + frames);
	}

	private static List<Frame> generateFrames(int hyperperiod) {
		List<Frame> frames = new LinkedList<Frame>();
		for (int i = 1; i <= hyperperiod / FRAME_SIZE; i++)
			frames.add(new Frame(i, FRAME_SIZE));
		return frames;
	}

	private static int computeHyperPeriod(List<Task> taskList) {
		Set<Integer> periods = new HashSet<Integer>();
		for (Task task : taskList) {
			if (!periods.contains(task.period)) {
				periods.add(task.period);
			}
		}
		Integer[] array = periods.toArray(new Integer[periods.size()]);
		return NumberUtility.lcm(array);
	}

	private static List<Job> computeJobListForTasks(List<Task> taskList,
			int hyperperiod, TASK_TYPE taskType,
			Map<Integer, Integer> taskVsCurrentJobId) {
		List<Job> jobList = new LinkedList<Job>();
		for (int i = 0; i < hyperperiod; i++) {
			for (Task task : taskList) {
				if (i % task.period == 0) {
					Job job = new Job(
							getJobIdForTask(task, taskVsCurrentJobId), i, task);
					if (job.getReleaseTime() > hyperperiod)
						continue;
					int j = 0;
					for (; j < jobList.size(); j++) {
						Job jobInList = jobList.get(j);
						if (jobInList.getReleaseTime() >= job.getReleaseTime())
							break;
					}
					if (taskType.equals(TASK_TYPE.ASYNC))
						jobList.add(j, job);
					else if (taskType.equals(TASK_TYPE.SYNC))
						jobList.add(job);
				}
			}
		}
		return jobList;
	}

	private static int getJobIdForTask(Task task,
			Map<Integer, Integer> taskVsCurrentJobId) {
		if (!taskVsCurrentJobId.containsKey(task.getTaskId()))
			taskVsCurrentJobId.put(task.getTaskId(), 0);
		int jobId = taskVsCurrentJobId.get(task.getTaskId());
		taskVsCurrentJobId.put(task.getTaskId(), ++jobId);
		return jobId;
	}

}
