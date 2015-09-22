package com.tfsc.kiran.real.time.systems.network.flow.impl;

import java.util.LinkedList;
import java.util.List;

import com.tfsc.kiran.real.time.systems.network.flow.model.Task;

public class InputParser {

	private List<Task> taskList = new LinkedList<Task>();

	InputParser(String input) {
		Integer taskId = new Integer(0);
		String[] tasks = input.split("\\(*\\),");
		;
		for (int i = 0; i < tasks.length; i++) {
			String temp = tasks[i].replace("( ", "");
			String[] tempSplit = temp.split(",");
			tempSplit[3] = tempSplit[3].replace(" )", "");
			taskList.add(new Task(++taskId,
					Integer.valueOf(tempSplit[1].trim()), Float
							.valueOf(tempSplit[2].trim()), Integer
							.valueOf(tempSplit[3].trim()), Float
							.valueOf(tempSplit[0].trim())));
		}
	}

	public List<Task> getTaskList() {
		return taskList;
	}

}
