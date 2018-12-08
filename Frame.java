/**
 * Create game where user controls a drone,
 * and must avoid enemy aircrafts to win.
 * @author Alvin Nguyen
 * @author Spencer Enriquez
 * @author Darren Wong
 */
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
		// Master(1): Primary panel for components.
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		// Master(2): Add title.
		JPanel top = new JPanel();
		JLabel label = new JLabel("Welcome to the Drone Survival Game!");
		top.add(label);

		// Drone(1): Add drone.
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
		// Hitbox dHitbox = new Hitbox(drn);
		//sop(dHitbox.printBounds());

		// Drone(2): Arrow Key Movement Implementation.
		skyField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				int drnX = drn.getX();
				int drnY = drn.getY();
				int drnI = drn.getIconWidth() / 4;
				
				if (key == KeyEvent.VK_UP) {
					if (dLabel.getY() > drnI)
						dLabel.setLocation(drnX, drnY - drnI);
				} else if(key == KeyEvent.VK_DOWN) {
					if (dLabel.getY() < FRAME_HEIGHT - drnI / 4)
						dLabel.setLocation(drnX, drnY + drnI);
				} else if(key == KeyEvent.VK_RIGHT) {
					if (dLabel.getX() < FRAME_WIDTH - drnI / 4)
						dLabel.setLocation(drnX + drnI, drnY);
				} else if(key == KeyEvent.VK_LEFT) {
					if (dLabel.getX() > drnI)
						dLabel.setLocation(drnX - drnI, drnY);
				}
				drn.setX(dLabel.getX());
				drn.setY(dLabel.getY());
				//drn.getHitbox().resetBounds();
				//sop(dHitbox.printBounds());
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		skyField.setFocusable(true);

		// Plane(1): Add enemy planes.
		ArrayList<Aircraft> planes = new ArrayList<>();
		ArrayList<JLabel>  planeLabels = new ArrayList<>();
		// ArrayList<Hitbox>  planeHitboxes = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			int adjust = (int)(Math.random() * FRAME_WIDTH / 2);
			planes.add(new Plane(FRAME_WIDTH + adjust,
					i * 10 + adjust,
					"airplane.png"));
			planeLabels.add(new JLabel(planes.get(i)));
			planeLabels.get(i).setBounds(planes.get(i).getX(),
						planes.get(i).getY(),
						planes.get(i).getIconWidth(),
						planes.get(i).getIconHeight());
			// planeHitboxes.add(new Hitbox(planes.get(i)));
			//for (Hitbox pl : planeHitboxes) sop(pl.printBounds());
		}

		// Planes(2): Add enemy planes to JFrame.
		for (JLabel pl : planeLabels) skyField.add(pl);

		// Planes(3): Move enemy Planes.
		final int DELAY = 10;
		Timer updateMovement = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dLabel.setLocation(drn.getX() + 1, drn.getY());
				drn.setX(dLabel.getX());
				drn.setY(dLabel.getY());
				for (int i = 0; i < planeLabels.size(); i++) {
					planeLabels.get(i).setLocation(
						planes.get(i).getX(),
						planes.get(i).getY());
					//planes.get(i).getHitbox().resetBounds();
					//sop(planeHitboxes.get(i).printBounds());
					planeLabels.get(i).repaint();
				}
			}
		});
		updateMovement.start();

		// Planes(4): Respawn Airplanes to the opposite side.
		Timer respawnPlanes = new Timer(DELAY, event -> {
			for (int i = 0; i < planes.size(); i++) {
				int plWidth = planes.get(i).getIconWidth();
				if (planes.get(i).getX() < -plWidth) {
					planes.get(i).setX(FRAME_WIDTH + plWidth);	
					planes.get(i).setY(
						(int)(Math.random() * FRAME_HEIGHT));
				}
				planes.get(i).setLocation(-5, 0);
				//planes.get(i).getHitbox().resetBounds();
				//sop(planeHitboxes.get(i).printBounds());
				planeLabels.get(i).repaint();
			}
		});
		respawnPlanes.start();
	
		// Game(1): Add Scoreboard and TimeClock
		TimeClock time = new TimeClock();	      
		Scoreboard scores = new Scoreboard();
		JLabel scoreLabel = new JLabel(scores.setScore());
		JLabel timeLabel= new JLabel(time.getTimeFormat());
		JLabel lifeLabel = new JLabel("Lives: " + drn.getLives());

		// Game(2): Main game functionality.
		Timer updateConditions = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean gameFinished = time.getSeconds() <= 90;
				//boolean collided = drn.getHitbox().getCollided();
				boolean collided = false;
				RectangleHitbox drnHitbox = drn.getHitArea();
				boolean dead = (drn.getLives() == 0);
				if (gameFinished && !dead) {
					time.addSecond();
					for (Aircraft pn : planes) {
					collided = drnHitbox.intersects(pn.getHitArea());
					//collided = drn.getHitbox().hasCollided(pn.getHitbox());
					//sop("Collided: " + collided);
					if (collided == true) {
						drn.subtractLife();
						//drn.getHitbox().setCollided(false);
						//pn.getHitbox().setCollided(false);
						//sop("Collision of " + drn.toString());
						//sop("with\nCollision of " + pn.toString());
					}
					//sop("Drone Lives: " + drn.getLives());
					//sop("Drone died: " + drn.isDead());
					}
				} else {
					drn.setLives(3);
					//drn.getHitbox().setCollided(false);
					collided = false;
					time.reset();
				}
				scores.checkScore(time, drn);
				//sop("WINS: " + scores.getWins());
				//sop("TOTAL: " + scores.getTotalGames());
				scoreLabel.setText(scores.setScore());
				timeLabel.setText(time.changeTime());
				lifeLabel.setText("Lives: " + drn.getLives());
			}
		});
		updateConditions.start();
		
		// Game(3): Add time and score information for the game.
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottom.add(timeLabel);
		bottom.add(new JLabel(" | ")); // Separator
		bottom.add(lifeLabel);
		bottom.add(new JLabel(" | ")); // Separator
		bottom.add(scoreLabel);
		
		// Master(3): Combine all panels into single panel.
		panel.add(top);
		panel.add(skyField);
		panel.add(bottom);

		// Master(4): Finalize JFrame creation.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(MAX_FRAME);
		this.getContentPane().add(panel);
		this.setVisible(true);
	}

	public static void sop(Object o) {
		System.out.println(o);
	}
}
