package com.tfsc.kiran.real.time.systems.network.flow.impl;

public class TaskUtilizationComputation {

	public static void main(String[] a) {
		String input = "( 0, 250, 6.4545, 250 ),( 0, 250, 30.1303, 250 ),( 0, 250, 0.3437, 250 ),( 0, 250, 0.1022, 250 ),( 0, 250, 5.7349, 250 ),( 0, 250, 5.7557, 250 ),( 0, 250, 5.7974, 250 ),( 0, 250, 5.8807, 250 ),( 0, 250, 6.0472, 250 ),( 0, 250, 4.3071, 250 ),( 0, 250, 7.7672, 250 ),( 0, 250, 14.6875, 250 ),( 0, 250, 7.183, 250 ),( 0, 250, 7.3999, 250 ),( 0, 250, 7.8337, 250 ),( 0, 250, 8.7012, 250 ),( 0, 250, 8.7012, 250 ),( 0, 250, 8.1264, 250 ),( 0, 250, 8.1264, 250 ),( 0, 250, 8.4815, 250 ),( 0, 250, 9.1918, 250 ),( 0, 250, 9.1918, 250 ),( 0, 250, 3.217, 250 ),( 0, 250, 3.5179, 250 ),( 0, 250, 3.6363, 250 ),( 0, 250, 5.1914, 250 ),( 0, 250, 0.1496, 250 ),( 0, 500, 3.3671, 500 ),( 0, 500, 3.3671, 500 ),( 0, 500, 3.3671, 500 ),( 0, 500, 3.3671, 500 ),( 0, 500, 3.3671, 500 ),( 0, 2000, 3.1913, 2000 ),( 0, 2000, 5.1122, 2000 ),( 0, 2000, 0.5047, 2000 ),( 0, 2000, 0.5906, 2000 ),( 0, 6000, 2.4799, 6000 ),( 0, 6000, 0.199, 6000 ),( 0, 6000, 0.6898, 6000 )";
		String tasks[] = input.split("\\(*\\),");
		String[] tt = new String[tasks.length];
		for (int i = 0; i < tasks.length; i++)
			tt[i] = tasks[0].replace("( ", "");

		printUtilization(tasks);

	}

	private static void printUtilization(String[] tasks) {
		int i = 0;
		float sumOfUtilizations = 0f;
		for (String task : tasks) {
			float executionTime = Float.valueOf(task.split(",")[2]);
			float period = Float.valueOf(task.split(",")[1]);
			float utilization = executionTime / period;
			sumOfUtilizations += utilization;
			System.out.println("T" + ++i + " = " + utilization);
		}
		System.out.println("System Utilization = " + sumOfUtilizations);
	}

}
