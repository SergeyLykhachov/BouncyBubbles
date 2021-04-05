package com.yahoo.slykhachov.bubbles.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import com.yahoo.slykhachov.bubbles.util.Subject;
import com.yahoo.slykhachov.bubbles.util.Observer;

public class Model implements Subject {
	public static final int MAX_BUBBLE_RADIUS = 40;
	public static final long NUM_OF_BUBBLES = 700;
	private List<Observer> observers;
	private List<BubbleModel> bubbles;
	private Dimension dimension;
	private Random random;
	public Model(Dimension dimension) {
		random = new Random();
		this.dimension = dimension;
		this.observers = new ArrayList<>();
		this.bubbles = Stream.iterate(
				new BubbleModel(
					this,
					new Color(random.nextInt(16777215)),
					trimRange(dimension.getWidth(), MAX_BUBBLE_RADIUS),
					trimRange(dimension.getHeight(), MAX_BUBBLE_RADIUS),
					random.nextInt(5),
					random.nextInt(360),
					random.nextInt(MAX_BUBBLE_RADIUS)
				),
				b -> new BubbleModel(
					this,
					new Color(random.nextInt(16777215)),
					trimRange(dimension.getWidth(), MAX_BUBBLE_RADIUS),
					trimRange(dimension.getHeight(), MAX_BUBBLE_RADIUS),
					random.nextInt(5),
					random.nextInt(360),
					random.nextInt(MAX_BUBBLE_RADIUS)
				)
			)
			.limit(NUM_OF_BUBBLES)
			.collect(Collectors.toList());
		new Timer(
			33,
			ae -> {
				this.bubbles.forEach(bubble -> bubble.doActionPerformed());
				this.notifyObservers();
		}).start();
	}
	private double trimRange(double sideLength, int trimmingLength) {
		double result = this.random.nextInt((int) sideLength - trimmingLength);
		return result <= trimmingLength ? trimmingLength + 1 : result;
	}
	CollisionType getCollisionType(double x, double y, double radius) {
		if (x - radius < 0) {
			return CollisionType.LEFT_COLLIDING;
		} else {
			if (x + radius > this.dimension.getWidth()) {
				return CollisionType.RIGHT_COLLIDING;
			} else {
				if (y - radius < 0) {
					return CollisionType.TOP_COLLIDING;
				} else {
					if (y + radius > this.dimension.getHeight()) {
						return CollisionType.BOTTOM_COLLIDING;
					} else {
						return CollisionType.NON_COLLIDING;
					}
				}
			}
		}
	}
	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}
	@Override
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
	}
	@Override
	public void notifyObservers() {
		this.observers.forEach(observer -> observer.update(this.bubbles));
	}
	static enum CollisionType {
		NON_COLLIDING {
			double computeHeading(double heading) {
				return heading;
			}
		},
		LEFT_COLLIDING {
			double computeHeading(double heading) {
				return heading < 180 ? 180 - heading : 360 - heading + 180;
			}
		},
		RIGHT_COLLIDING {
			double computeHeading(double heading) {
				return heading < 90 ? 180 - heading : 360 - heading + 180;
			}
		},
		TOP_COLLIDING {
			double computeHeading(double heading) {
				return 360 - heading;
			}
		},
		BOTTOM_COLLIDING {
			double computeHeading(double heading) {
				return 360 - heading;
			}
		};
		abstract double computeHeading(double heading);
	}
}
