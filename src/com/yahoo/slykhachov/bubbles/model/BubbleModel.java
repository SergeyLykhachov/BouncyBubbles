package com.yahoo.slykhachov.bubbles.model;

import java.awt.Color;

public class BubbleModel {
	private Model model;
	private Color color;
	private double x;
	private double y;
	private double speed;
	private double heading;
	private double radius;
	BubbleModel(Model model, Color color, double x, double y, double speed,
			double heading, double radius) {
		this.setModel(model);
		this.setColor(color);
		this.setX(x);
		this.setY(y);
		this.setSpeed(speed == 0 ? 1 : speed);
		this.setHeading(heading);
		this.setRadius(radius < 25 ? 25 : radius);
	}
	public Model getModel() {
		return this.model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public void doActionPerformed() {
		computeXY();
	}
	private void computeXY() {
		double newX = getSpeed() * Math.cos(Math.toRadians(getHeading())) + getX();
		double newY = getSpeed() * Math.sin(Math.toRadians(getHeading())) + getY();
		Model.CollisionType collisionType = getModel().getCollisionType(newX, newY, this.getRadius());
		if (collisionType.equals(Model.CollisionType.NON_COLLIDING)) {
			this.setX(newX);
			this.setY(newY);
		} else {
			this.setHeading(collisionType.computeHeading(this.getHeading()));
			newX = getSpeed() * Math.cos(Math.toRadians(getHeading())) + this.getX();
			newY = getSpeed() * Math.sin(Math.toRadians(getHeading())) + this.getY();
			this.setX(newX);
			this.setY(newY);
		}
	}
	void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return this.color;
	}
	void setHeading(double heading) {
		this.heading = heading;
	}
	double getHeading() {
		return this.heading;
	}
	void setX(double x) {
		this.x = x;
	}
	public double getX() {
		return this.x;
	}
	void setY(double y) {
		this.y = y;
	}
	public double getY() {
		return this.y;
	}
	void setSpeed(double speed) {
		this.speed = speed;
	}
	double getSpeed() {
		return this.speed;
	}
	void setRadius(double radius) {
		this.radius = radius;
	}
	public double getRadius() {
		return this.radius;
	}
}
