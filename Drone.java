/**
 * Flying Drone.
 * @author Alvin Nguyen
 * @author Spencer Enriquez
 */
import java.awt.event.KeyEvent;

public class Drone extends Aircraft {
	
	// Initial number of lives depends on game's parameters.
	private int lives;

	public Drone(int x, int y, String imgFile) {
		super(x, y, imgFile);
		lives = 2;
	}

	/**
	 * Set the number of lives for the drone.
	 * @param lives
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Retrieve number of lives from the drone.
	 * @return lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Add a life point to the drone.
	 */
	public void addLife() {
		lives++;
	}

	/**
	 * Subtract a life point from the drone.
	 */
	public void subtractLife() {
		lives--;
	}

	public String toString() {
		return "";
	}
	
	/**
	 * Drone moves in direction of pressed input command:
	 * UP, DOWN, LEFT, RIGHT.
	 * @param input pressed
	 */
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP){
			setLocation(0, 40);
		}else if(key == KeyEvent.VK_DOWN){
			setLocation(0, -40);
		}else if(key == KeyEvent.VK_RIGHT){
			setLocation(40, 0);
		}else if(key == KeyEvent.VK_LEFT){
			setLocation(-40, 0);
		}
	}
	
	/**
	 * Drone does not move after key press is released.
	 * @param input released
	 */
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP){
			setLocation(0, 0);
		}else if(key == KeyEvent.VK_DOWN){
			setLocation(0, 0);
		}else if(key == KeyEvent.VK_RIGHT){
			setLocation(0, 0);
		}else if(key == KeyEvent.VK_LEFT){
			setLocation(0, 0);
		}
	}
}
