package com.yahoo.slykhachov.bubbles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import com.yahoo.slykhachov.bubbles.model.Model;
import com.yahoo.slykhachov.bubbles.view.View;

public class Bubbles {
	private Dimension dimension;
	private Model model;
	private View view;
	public Bubbles() {
		JFrame frame = new JFrame("Bubbles");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDimension(new Dimension(1000, 700));
		this.setView(new View(this.getDimension()));
		this.setModel(new Model(this.getDimension()));
		this.getModel().registerObserver(this.getView());
		frame.getContentPane().add(this.getView(), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	void setView(View view) {
		this.view = view;
	}
	void setModel(Model model) {
		this.model = model;
	}
	Model getModel() {
		return this.model;
	}
	View getView() {
		return this.view;
	}
	void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	Dimension getDimension() {
		return this.dimension;
	}
	public static void main(String[] sa) {
		EventQueue.invokeLater(Bubbles::new);
	}
}
