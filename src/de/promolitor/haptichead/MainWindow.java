package de.promolitor.haptichead;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.BevelBorder;

import de.promolitor.haptichead.drawing.CustomJPanel;
import de.promolitor.haptichead.helper.Motor;
import de.promolitor.haptichead.server.ServerConnection;

import java.awt.Color;

public class MainWindow {

	ServerConnection server;

	private JFrame frame;
	private boolean mousePressed = false;
	private Motor[] motorList;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		setMotor16x16();
		server = new ServerConnection();
		server.connect();
	}

	public void setMotor16x16() {
		motorList = new Motor[16];

		// Erste Reihe
		motorList[0] = new Motor(0, 0, 0, 5);
		motorList[1] = new Motor(100, 0, 1, 6);
		motorList[2] = new Motor(200, 0, 2, 7);
		motorList[3] = new Motor(300, 0, 3, 8);

		// Zweite Reihe
		motorList[4] = new Motor(0, 100, 4, 9);
		motorList[5] = new Motor(100, 100, 5, 10);
		motorList[6] = new Motor(200, 100, 6, 11);
		motorList[7] = new Motor(300, 100, 7, 12);

		// Dritte Reihe
		motorList[8] = new Motor(0, 200, 8, 13);
		motorList[9] = new Motor(100, 200, 9, 14);
		motorList[10] = new Motor(200, 200, 10, 15);
		motorList[11] = new Motor(300, 200, 11, 16);

		// Vierte Reihe
		motorList[12] = new Motor(0, 300, 12, 17);
		motorList[13] = new Motor(100, 300, 13, 18);
		motorList[14] = new Motor(200, 300, 14, 19);
		motorList[15] = new Motor(300, 300, 15, 20);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 402);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		CustomJPanel panel = new CustomJPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 340, 340);

		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				// jPanel2MousePressed(evt);
				mousePressed = true;
				System.out.println("Mouse Pressed");
			}

			public void mouseReleased(MouseEvent evt) {
				mousePressed = false;
				System.out.println("Mouse Released");
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evt) {
				if (mousePressed) {
					// clear intensities
					for (int i = 0; i < ServerConnection.intensities.length; i++) {
						ServerConnection.intensities[i] = 0;
					}
					int x = evt.getX() - 20; // OFFSET X
					int y = evt.getY() - 20; // OFFSET Y
					if (x >= 0 && x <= 300 && y >= 0 && y <= 300) {
						System.out.println("Mouse X: " + x + " // Mouse Y: " + y);
						updateMotors(x, y);
					}

				}
			}
		});

		frame.getContentPane().add(panel);

	}

	public int distanceToPower(double distance) {

		return (int) (100 - ((distance * (5.0 / 7.0))));

	}

	public void updateMotors(int x, int y) {

		// Stärke der Motoren im Moment nur Linear über Distance funktion im
		// Grid
		// 140 ist der Maximal Abstand. Geraden Gleichung aufgestellt.
		// Distance x ( 5/7 ) = Motorstärke
		double distance = 0;
		int grid = 0;
		// Grid 1 - Motors 0,1,4,5
		if (x < 100 && y < 100) {
			grid = 1;

			// Motor 0
			distance = Math.sqrt(Math.pow(x - motorList[0].x, 2) + Math.pow(y - motorList[0].y, 2));
			System.out.println(distance);
			ServerConnection.intensities[motorList[0].idOnBoard] = distanceToPower(distance);

			// Motor 1
			distance = Math.sqrt(Math.pow(x - motorList[1].x, 2) + Math.pow(y - motorList[1].y, 2));
			ServerConnection.intensities[motorList[1].idOnBoard] = distanceToPower(distance);

			// Motor 4
			distance = Math.sqrt(Math.pow(x - motorList[4].x, 2) + Math.pow(y - motorList[4].y, 2));
			ServerConnection.intensities[motorList[4].idOnBoard] = distanceToPower(distance);

			// Motor 5
			distance = Math.sqrt(Math.pow(x - motorList[5].x, 2) + Math.pow(y - motorList[5].y, 2));
			ServerConnection.intensities[motorList[5].idOnBoard] = distanceToPower(distance);

		} else // Grid 2 - Motors 1,2,5,6
		if (x < 200 && y < 100) {
			grid = 2;
			// Motor 1
			distance = Math.sqrt(Math.pow(x - motorList[1].x, 2) + Math.pow(y - motorList[1].y, 2));
			ServerConnection.intensities[motorList[1].idOnBoard] = distanceToPower(distance);

			// Motor 2
			distance = Math.sqrt(Math.pow(x - motorList[2].x, 2) + Math.pow(y - motorList[2].y, 2));
			ServerConnection.intensities[motorList[2].idOnBoard] = distanceToPower(distance);

			// Motor 5
			distance = Math.sqrt(Math.pow(x - motorList[5].x, 2) + Math.pow(y - motorList[5].y, 2));
			ServerConnection.intensities[motorList[5].idOnBoard] = distanceToPower(distance);

			// Motor 6
			distance = Math.sqrt(Math.pow(x - motorList[6].x, 2) + Math.pow(y - motorList[6].y, 2));
			ServerConnection.intensities[motorList[6].idOnBoard] = distanceToPower(distance);

		} else // Grid 3 - Motors 2,3,6,7
		if (x <= 300 && y < 100) {
			grid = 3;
			// Motor 2
			distance = Math.sqrt(Math.pow(x - motorList[2].x, 2) + Math.pow(y - motorList[2].y, 2));
			ServerConnection.intensities[motorList[2].idOnBoard] = distanceToPower(distance);

			// Motor 3
			distance = Math.sqrt(Math.pow(x - motorList[3].x, 2) + Math.pow(y - motorList[3].y, 2));
			ServerConnection.intensities[motorList[3].idOnBoard] = distanceToPower(distance);

			// Motor 6
			distance = Math.sqrt(Math.pow(x - motorList[6].x, 2) + Math.pow(y - motorList[6].y, 2));
			ServerConnection.intensities[motorList[6].idOnBoard] = distanceToPower(distance);

			// Motor 7
			distance = Math.sqrt(Math.pow(x - motorList[7].x, 2) + Math.pow(y - motorList[7].y, 2));
			ServerConnection.intensities[motorList[7].idOnBoard] = distanceToPower(distance);

		} else // Grid 4 - Motors 4,5,8,9
		if (x < 100 && y < 200) {
			grid = 4;
			// Motor 4
			distance = Math.sqrt(Math.pow(x - motorList[4].x, 2) + Math.pow(y - motorList[4].y, 2));
			ServerConnection.intensities[motorList[4].idOnBoard] = distanceToPower(distance);

			// Motor 5
			distance = Math.sqrt(Math.pow(x - motorList[5].x, 2) + Math.pow(y - motorList[5].y, 2));
			ServerConnection.intensities[motorList[5].idOnBoard] = distanceToPower(distance);

			// Motor 8
			distance = Math.sqrt(Math.pow(x - motorList[8].x, 2) + Math.pow(y - motorList[8].y, 2));
			ServerConnection.intensities[motorList[8].idOnBoard] = distanceToPower(distance);

			// Motor 9
			distance = Math.sqrt(Math.pow(x - motorList[9].x, 2) + Math.pow(y - motorList[9].y, 2));
			ServerConnection.intensities[motorList[9].idOnBoard] = distanceToPower(distance);

		} else // Grid 5 - Motors 5,6,9,10
		if (x < 200 && y < 200) {
			grid = 5;
			// Motor 5
			distance = Math.sqrt(Math.pow(x - motorList[5].x, 2) + Math.pow(y - motorList[5].y, 2));
			ServerConnection.intensities[motorList[5].idOnBoard] = distanceToPower(distance);

			// Motor 6
			distance = Math.sqrt(Math.pow(x - motorList[6].x, 2) + Math.pow(y - motorList[6].y, 2));
			ServerConnection.intensities[motorList[6].idOnBoard] = distanceToPower(distance);

			// Motor 9
			distance = Math.sqrt(Math.pow(x - motorList[9].x, 2) + Math.pow(y - motorList[9].y, 2));
			ServerConnection.intensities[motorList[9].idOnBoard] = distanceToPower(distance);

			// Motor 10
			distance = Math.sqrt(Math.pow(x - motorList[10].x, 2) + Math.pow(y - motorList[10].y, 2));
			ServerConnection.intensities[motorList[10].idOnBoard] = distanceToPower(distance);

		} else // Grid 6 - Motors 6,7,10,11
		if (x <= 300 && y < 200) {
			grid = 6;
			// Motor 6
			distance = Math.sqrt(Math.pow(x - motorList[6].x, 2) + Math.pow(y - motorList[6].y, 2));
			ServerConnection.intensities[motorList[6].idOnBoard] = distanceToPower(distance);

			// Motor 7
			distance = Math.sqrt(Math.pow(x - motorList[7].x, 2) + Math.pow(y - motorList[7].y, 2));
			ServerConnection.intensities[motorList[7].idOnBoard] = distanceToPower(distance);

			// Motor 10
			distance = Math.sqrt(Math.pow(x - motorList[10].x, 2) + Math.pow(y - motorList[10].y, 2));
			ServerConnection.intensities[motorList[10].idOnBoard] = distanceToPower(distance);

			// Motor 11
			distance = Math.sqrt(Math.pow(x - motorList[11].x, 2) + Math.pow(y - motorList[11].y, 2));
			ServerConnection.intensities[motorList[11].idOnBoard] = distanceToPower(distance);

		} else // Grid 7 - Motors 8,9,12,13
		if (x < 100 && y <= 300) {
			grid = 7;
			// Motor 8
			distance = Math.sqrt(Math.pow(x - motorList[8].x, 2) + Math.pow(y - motorList[8].y, 2));
			ServerConnection.intensities[motorList[8].idOnBoard] = distanceToPower(distance);

			// Motor 9
			distance = Math.sqrt(Math.pow(x - motorList[9].x, 2) + Math.pow(y - motorList[9].y, 2));
			ServerConnection.intensities[motorList[9].idOnBoard] = distanceToPower(distance);

			// Motor 12
			distance = Math.sqrt(Math.pow(x - motorList[12].x, 2) + Math.pow(y - motorList[12].y, 2));
			ServerConnection.intensities[motorList[12].idOnBoard] = distanceToPower(distance);

			// Motor 13
			distance = Math.sqrt(Math.pow(x - motorList[13].x, 2) + Math.pow(y - motorList[13].y, 2));
			ServerConnection.intensities[motorList[13].idOnBoard] = distanceToPower(distance);

		} else // Grid 8 - Motors 9,10,13,14
		if (x < 200 && y <= 300) {
			grid = 8;
			// Motor 9
			distance = Math.sqrt(Math.pow(x - motorList[9].x, 2) + Math.pow(y - motorList[9].y, 2));
			ServerConnection.intensities[motorList[9].idOnBoard] = distanceToPower(distance);

			// Motor 10
			distance = Math.sqrt(Math.pow(x - motorList[10].x, 2) + Math.pow(y - motorList[10].y, 2));
			ServerConnection.intensities[motorList[10].idOnBoard] = distanceToPower(distance);

			// Motor 13
			distance = Math.sqrt(Math.pow(x - motorList[13].x, 2) + Math.pow(y - motorList[13].y, 2));
			ServerConnection.intensities[motorList[13].idOnBoard] = distanceToPower(distance);

			// Motor 14
			distance = Math.sqrt(Math.pow(x - motorList[14].x, 2) + Math.pow(y - motorList[14].y, 2));
			ServerConnection.intensities[motorList[14].idOnBoard] = distanceToPower(distance);

		} else // Grid 9 - Motors 10,11,14,15
		if (x <= 300 && y <= 300) {
			grid = 9;
			// Motor 10
			distance = Math.sqrt(Math.pow(x - motorList[10].x, 2) + Math.pow(y - motorList[10].y, 2));
			ServerConnection.intensities[motorList[10].idOnBoard] = distanceToPower(distance);

			// Motor 11
			distance = Math.sqrt(Math.pow(x - motorList[11].x, 2) + Math.pow(y - motorList[11].y, 2));
			ServerConnection.intensities[motorList[1].idOnBoard] = distanceToPower(distance);

			// Motor 14
			distance = Math.sqrt(Math.pow(x - motorList[14].x, 2) + Math.pow(y - motorList[14].y, 2));
			ServerConnection.intensities[motorList[14].idOnBoard] = distanceToPower(distance);

			// Motor 15
			distance = Math.sqrt(Math.pow(x - motorList[15].x, 2) + Math.pow(y - motorList[15].y, 2));
			ServerConnection.intensities[motorList[15].idOnBoard] = distanceToPower(distance);

		}
		System.out.println(Arrays.toString(ServerConnection.intensities));
		server.send();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
