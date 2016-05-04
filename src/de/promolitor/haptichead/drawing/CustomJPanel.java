package de.promolitor.haptichead.drawing;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CustomJPanel extends JPanel {

	public CustomJPanel() {
		// set a preferred size for the custom panel.
		setPreferredSize(new Dimension(340, 340));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// DRAW GRID
		// 1 Kasten = 0.5 cm
		for (int i = 0; i < this.getSize().getWidth(); i = i + 20) {
			for (int j = 0; j < this.getSize().getHeight(); j = j + 20) {
				g.drawRect(i, j, 20, 20);
			}
		}
		// DRAW Phantom Motors with Offset
		for (int i = 10; i < this.getSize().getWidth(); i = i + 100) {
			for (int j = 10; j < this.getSize().getHeight(); j = j + 100) {
				g.drawOval(i, j, 20, 20);
			}
		}

	}
}
