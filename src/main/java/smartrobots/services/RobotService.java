package smartrobots.services;


import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.LinkedList;
import java.util.List;

import smartrobots.model.actors.Robot;

public class RobotService {
	
	private ContainerController containerController;
	private List<Robot> robots;

	public RobotService(ContainerController containerController) {
		this.containerController = containerController;
		this.robots = new LinkedList<>();
	}
	
	public List<Robot> getRobots() {
		return robots;
	}
	
	
	public Robot createRobot(String name, int x, int y, double speed) {
		Robot robot = new Robot(name, x, y, speed, false, Robot.Movement.NONE);
		AgentController agentController;
		try {
			agentController = containerController.createNewAgent(name, "smartrobots.agents.RobotAgent", new Object[] { robot });
			agentController.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		robots.add(robot);
		return robot;
	}
	
	public Robot getRobot(String name) {
		return robots.stream().filter(robot -> robot.getName().equals(name)).findAny().get();
	}
}
