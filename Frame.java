import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Frame extends JFrame {

	private final int FRAME_WIDTH = 1000;
	private final int FRAME_HEIGHT = 1000;
	private final Dimension MAX_FRAME = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
	private final Color lightblue = new Color(51,204,255);
	
	public Frame() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		// Add title.
		JPanel top = new JPanel();
		JLabel label = new JLabel("Welcome to the Drone Survival Game!");
		top.add(label);

		// Add drone.
		JPanel skyField = new JPanel();
		skyField.setLayout(null);
		skyField.setPreferredSize(MAX_FRAME);
		skyField.setBackground(lightblue);
		Drone drn = new Drone(FRAME_WIDTH / 4, FRAME_HEIGHT / 4,
			"drone.png");
		JLabel dLabel = new JLabel(drn);
		skyField.add(dLabel);
		dLabel.setBounds(drn.getX(), drn.getY(),
				drn.getIconWidth(), drn.getIconHeight());

		// Add enemy planes.
		ArrayList<Aircraft> planes = new ArrayList<>();
		ArrayList<JLabel>  planeLabels = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			int adjust = (int)(Math.random() * FRAME_WIDTH / 2);
			planes.add(new Plane(FRAME_WIDTH + adjust,
					i * 10 + adjust,
					"airplane.png"));
			planeLabels.add(new JLabel(planes.get(i)));
			planeLabels.get(i).setBounds(planes.get(i).getX(),
						planes.get(i).getY(),
						planes.get(i).getIconWidth(),
						planes.get(i).getIconHeight());
		}
		
		//add Scoreboard, TimeClock, and Lives Left
		TimeClock time = new TimeClock();	      
		Scoreboard scores = new Scoreboard();
		JLabel scoreLabel = new JLabel(scores.setScore());
		String text = time.getTimeFormat();
		JLabel timeLabel= new JLabel(text);
		JLabel livesLabel = new JLabel("Total Lives Left: " + drn.getLives() + " |");


		Timer updateConditions = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time.addSecond();
				scores.checkScore(time, drn);
				scoreLabel.setText(scores.setScore());
				if (time.getSeconds() >= 90 || drn.getLives() <= 0)
					time.reset();
					drn.setLives(3);
				timeLabel.setText(time.changeTime());
				livesLabel.setText("Total Lives Left: " + drn.getLives() + " |");
			}
		});
		updateConditions.start();
 
		Timer updateMovement = new Timer(50, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Collision Detection: Implemented by Alvin and does not work.
				boolean collided;
				for (int i = 0; i < planeLabels.size(); i++) {
					/*
					collided = drn.getHitbox().hasCollided(planes.get(i).getHitbox());
					//sop("Collided: " + collided);
					if (collided == true) {
						drn.subtractLife();
						drn.getHitbox().setCollided(false);
						planes.get(i).getHitbox().setCollided(false);
						//sop("Collision of " + drn.toString());
						//sop("with\nCollision of " + pn.toString());
					}
					 */
					planeLabels.get(i).setLocation(planes.get(i).getX() - (int)Math.pow(2, scores.getWins()), planes.get(i).getY());
				}
				dLabel.setLocation(drn.getX() + 1, drn.getY());
				drn.setX(dLabel.getX());
				drn.setY(dLabel.getY());
			}
		});
		updateMovement.start();

		// For Airplane respawn
		final int DELAY = 10;
		Timer t = new Timer(DELAY, event -> {
			for (Aircraft pl : planes) {
				if (pl.getX() < 10) {
					pl.setX(FRAME_WIDTH);
					pl.setY((int)(Math.random() * FRAME_HEIGHT));
				}
				pl.setLocation(-5, 0);
			}
			for (JLabel pl : planeLabels)
				pl.repaint();
		});
		t.start();


		for (JLabel pl : planeLabels) skyField.add(pl);
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));

		bottom.add(livesLabel);
		bottom.add(timeLabel);
		bottom.add(scoreLabel);

		
		// Arrow Key Movement Implementation
		skyField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				if (key == KeyEvent.VK_UP) {
					if (dLabel.getY() > 20)
						dLabel.setLocation(drn.getX(), drn.getY() - 40);
					
				} else if(key == KeyEvent.VK_DOWN) {
					if (dLabel.getY() < FRAME_HEIGHT)
						dLabel.setLocation(drn.getX(), drn.getY() + 40);
					
				} else if(key == KeyEvent.VK_RIGHT) {
					if (dLabel.getX() < FRAME_WIDTH)
						dLabel.setLocation(drn.getX() + 40, drn.getY());
				} else if(key == KeyEvent.VK_LEFT) {
					if (dLabel.getX() > 20)
						dLabel.setLocation(drn.getX() - 40, drn.getY());
				}
				drn.setX(dLabel.getX());
				drn.setY(dLabel.getY());
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		skyField.setFocusable(true);

		// Combine all panels into single panel.
		panel.add(top);
		panel.add(skyField);
		panel.add(bottom);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setSize(new Dimension(FRAME_SIZE, FRAME_SIZE));
		this.setSize(MAX_FRAME);
		this.getContentPane().add(panel);
		this.setVisible(true);
	}

	public static void sop(Object o) {
		System.out.println(o);
	}
}
