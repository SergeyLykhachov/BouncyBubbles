package com.yahoo.slykhachov.bubbles.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;
import com.yahoo.slykhachov.bubbles.model.BubbleModel;
import com.yahoo.slykhachov.bubbles.util.Observer;

public class View extends JPanel implements Observer, MouseMotionListener {
	private static final int REACTION_DISTANCE_THRESHOLD = 100;
	private List<BubbleModel> bubbles;
	private int mouseX;
	private int mouseY;
	public View(Dimension dimension) {
		setPreferredSize(dimension);
		addMouseMotionListener(this);
	}
	@Override
	public void mouseMoved(MouseEvent me) {
		this.mouseX = me.getX();
		this.mouseY = me.getY();
	}
	@Override
	public void update(List<BubbleModel> list) {
		this.bubbles = list;
		this.repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();		
		for (BubbleModel bm : this.bubbles) {
			int x = (int) bm.getX();
			int y = (int) bm.getY();
			double distance = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));
			double scalar = distance <= REACTION_DISTANCE_THRESHOLD ? 1.5 + (1 - distance / REACTION_DISTANCE_THRESHOLD) : 1;
			int radius = (int) (bm.getRadius() * scalar);
			g2d.setColor(bm.getColor());
			g2d.fillOval(
				x - radius,
				y - radius,
				radius * 2,
				radius * 2
			);
		}
		g2d.dispose();
	}
	@Override
	public void mouseDragged(MouseEvent me) {}
}
