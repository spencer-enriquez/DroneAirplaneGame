/**
 * Create hitbox boundaries for aircraft.
 * @author Alvin Nguyen
 */
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Hitbox {

	private Aircraft ac;
	private boolean collided;

	// 0 = xMin, 1 = xMax, 2 = yMin, 3 = yMax.
	private int[] bounds = new int[4];

	// x-axis Measurements 
	private int xMin;
	private int xMax;

	// y-axis Measurements
	private int yMin;
	private int yMax;

	public Hitbox(Aircraft ac) {
		// Set aircraft.
		this.ac = ac;
		collided = false;
		setHitbox();
	}
	
	/**
	 * Set hitbox of the aircraft.
	 */
	private void setHitbox() {
		// Define bounds of hitbox.
		xMin = ac.getX();
		yMin = ac.getY();
		xMax = xMin + ac.getIconWidth();
		yMax = yMin + ac.getIconHeight();

		// Store bounds into an array.
		bounds[0] = xMin;
		bounds[1] = xMax; 
		bounds[2] = yMin; 
		bounds[3] = yMax; 
	}

	/**
	 * Print array with x and y bounds.
	 * @return String bounds 
	 */
	public String printBounds() {
		return "xMin: " + bounds[0] + "\n"
			+ "xMin: " + bounds[0] + "\n"
			+ "yMin: " + bounds[2] + "\n"
			+ "yMax: " + bounds[3];
	}

	/**
	 * Return array with x and y bounds.
	 * @return bounds
	 */
	public int[] getBounds() {
		return bounds;
	}

	/**
	 * Reset bounds of hitbox after movement.
	 */
	public void resetBounds() {
		setHitbox();
	}
	
	/**
	 * Return collided value.
	 * @return collided
	 */
	public boolean getCollided() {
		return collided;
	}

	/**
	 * Set collided to new boolean value.
	 * @param boolean
	 */
	public void setCollided(boolean b) {
		collided = b;
	}

	/**
	 * Return whether hitbox has collided with another aircraft.
	 * @return boolean
	 */
	public boolean hasCollided(Hitbox other) {
		// Create arrays that store the measurements of other's hitboxes.
		int[] otherBounds = other.getBounds();

		/*
		 * Compare each x and y bounds.
		 * Collision occurs when certain conditions are met.
		 * bounds[0] = xMin; // Left
		 * bounds[1] = xMax; // Right
		 * bounds[2] = yMin; // Top
		 * bounds[3] = yMax; // Bottom
		 * compBounds[0] = this.xMin ≤ other.xMax;
		 * 		   this.Left ≤ other.Right;
		 * compBounds[1] = this.xMax ≤ other.xMin;
		 * 		   this.Right ≤ other.Left;
		 * compBounds[2] = this.yMin ≥ other.yMax;
		 * 		   this.Top ≥ other.Bottom;
		 * compBounds[3] = this.yMax ≤ other.yMin;
		 * 		   this.Bottom ≤ other.Top;
		 * compBounds[4] = this.xMin == other.xMin;
		 * 		   this.Left == other.Left;
		 * compBounds[5] = this.xMax == other.xMax;
		 * 		   this.Left == other.Left;
		/*
		boolean[] compBounds = new boolean[8];
		compBounds[0] = bounds[0] <= otherBounds[1];
		compBounds[1] = bounds[1] <= otherBounds[0];
		compBounds[2] = bounds[2] >= otherBounds[3];
		compBounds[3] = bounds[3] <= otherBounds[2];
		compBounds[4] = bounds[0] == otherBounds[0];
		compBounds[5] = bounds[1] == otherBounds[1];
		compBounds[6] = bounds[2] == otherBounds[2];
		compBounds[7] = bounds[3] == otherBounds[3];
		*/

		boolean[] compBounds = new boolean[8];
		compBounds[0] = bounds[0] >= otherBounds[0];
		compBounds[1] = bounds[1] <= otherBounds[1];
		compBounds[2] = bounds[2] <= otherBounds[2];
		compBounds[3] = bounds[3] >= otherBounds[3];
		compBounds[4] = bounds[0] <= otherBounds[0];
		compBounds[5] = bounds[1] >= otherBounds[1];
		compBounds[6] = bounds[2] >= otherBounds[2];
		compBounds[7] = bounds[3] <= otherBounds[3];

		if (compBounds[0] && compBounds[1]
			&& compBounds[2] && compBounds[3]) {
			collided = true;
		} else if (compBounds[4] && compBounds[1]
			&& compBounds[2] && compBounds[3]) {
			collided = true;
		} else if (compBounds[0] && compBounds[5]
			&& compBounds[2] && compBounds[3]) {
			collided = true;
		} else if (compBounds[0] && compBounds[1]
			&& compBounds[6] && compBounds[3]) {
			collided = true;
		} else if (compBounds[0] && compBounds[1]
			&& compBounds[2] && compBounds[7]) {
			collided = false;
		}
		
		/*
		sop("\nHas collided: " + collided
			+ "\nh1.xMin <= h2.xMax: " + compBounds[0]
			+ "\nh1.xMax <= h2.xMin: " + compBounds[1]
			+ "\nh1.yMin <= h2.yMax: " + compBounds[2]
			+ "\nh1.yMax >= h2.yMin: " + compBounds[3]);
		*/

		return collided;
	}


	public void oldCollided() {
		/*
		 * Compare each x and y bounds.
		 * Collision occurs when certain conditions are met.
		 * bounds[0] = xMin; // Left
		 * bounds[1] = xMax; // Right
		 * bounds[2] = yMin; // Top
		 * bounds[3] = yMax; // Bottom
		 * compBounds[0] = this.xMin ≤ other.xMax;
		 * 		   this.Left ≤ other.Right;
		 * compBounds[1] = this.xMax ≤ other.xMin;
		 * 		   this.Right ≤ other.Left;
		 * compBounds[2] = this.yMin ≥ other.yMax;
		 * 		   this.Top ≥ other.Bottom;
		 * compBounds[3] = this.yMax ≤ other.yMin;
		 * 		   this.Bottom ≤ other.Top;
		 * compBounds[4] = this.xMin == other.xMin;
		 * 		   this.Left == other.Left;
		 * compBounds[5] = this.xMax == other.xMax;
		 * 		   this.Left == other.Left;
		/*
		boolean[] compBounds = new boolean[8];
		compBounds[0] = bounds[0] <= otherBounds[1];
		compBounds[1] = bounds[1] <= otherBounds[0];
		compBounds[2] = bounds[2] >= otherBounds[3];
		compBounds[3] = bounds[3] <= otherBounds[2];
		compBounds[4] = bounds[0] == otherBounds[0];
		compBounds[5] = bounds[1] == otherBounds[1];
		compBounds[6] = bounds[2] == otherBounds[2];
		compBounds[7] = bounds[3] == otherBounds[3];
		*/

	
		/*
		 * Collision Conditions:
		 * Let the h1 be the current hitbox, and h2 be the other hitbox.
		 * Assume h1 and h2 are moving towards each other.
		 * Let T be the top bound, B be the bottom bound,
		 *    L be the left bound, and R be right bound of the hitbox.
		 *
		 * 1. When hitboxes completely pass each other,
		 *    If h1.L > h2.R & h1.R < h2.L,
		 *    then no collision.
		 *
		 * 2. Before hitboxes completely pass each other,
		 *    top or bottom bound of each hitboxes converge.
		 *    If h1.L ≤ h2.R & h1.R ≤ h2.L,
		 *    and h1.T ≥ h2.B / h1.B ≤ h2.T
		 *    then collision.
		 *
		 * 3. Before hitboxes completely pass each other,
		 *    top or bottom bound of each hitboxes do not converge.
		 *    If h1.L ≤ h2.R & h1.R ≥ h2.L,
		 *    and h1.T < h2.B / h1.B > h2.T,
		 *    then no collision.
		 *
		 * 4. Before hitboxes pass each other vertically,
		 *    top or bottom bound of each hitboxes converge.
		 *    If h1.L ≤ h2.R & h1.R > h2.L,
		 *    and h1.T ≥ h2.B / h1.B ≤ h2.T,
		 *    then collision.
		 *
		 * 5. Before hitboxes pass each other vertically,
		 *    top or bottom bound of each hitboxes do not converge.
		 *    If h1.L ≤ h2.R & h1.R > h2.L,
		 *    and h1.T < h2.B / h1.B > h2.T,
		 *    then no collision.
		 *
		 * 6. While hitboxes share the same vertical space,
		 *    and have not completely passed each other,
		 *    top or bottom bound of each hitboxes converge.
		 *    If h1.L < h2.R & h1.R < h2.L
		 *    and h1.L == h2.L & h1.R == h2.R,
		 *    and h1.T ≥ h2.B / h1.B ≤ h2.T,
		 *    then collision.
		 *
		 * 7. While hitboxes share the same vertical space,
		 *    and have not completely passed each other,
		 *    top or bottom bound of each hitboxes do not converge.
		 *    If h1.L < h2.R & h1.R < h2.L
		 *    and h1.L == h2.L & h1.R == h2.R,
		 *    and h1.T < h2.B / h1.B > h2.T,
		 *    then no collision.
		 *
		 * 8. Any other position can not have collisions.
		 */
		/*
		if (compBounds[0] == false && compBounds[1] == false) {
			collided = false; // Condition 1	
			other.setCollided(false);
		} else if (compBounds[0] == true && compBounds[1] == true) {
			if ((compBounds[2] == true || compBounds[3] == true)
			&& (compBounds[6] == true || compBounds[7] == true)) {
				collided = true; // Condition 2
				other.setCollided(true);
			} else {
				collided = false; // Condition 3
				other.setCollided(false);
			}
		} else if (compBounds[0] == true && compBounds[1] == false) {
			if ((compBounds[2] == true || compBounds[3] == true)
			&& (compBounds[6] == true || compBounds[7] == true)) {
				collided = true; // Condition 4
				other.setCollided(true);
			} else {
				collided = false; // Condition 5
				other.setCollided(false);
			}
		} else if (compBounds[4] == true && compBounds[5] == true) {
			if ((compBounds[2] == true || compBounds[3] == true)
			&& (compBounds[6] == true || compBounds[7] == true)) {
				collided = true; // Condition 6 
				other.setCollided(true);
			} else {
				collided = false; // Condition 7
				other.setCollided(false);
			}
		} else {
			collided = false; // Condition 8
			other.setCollided(false);
		}
		*/
	}

	public static void sop(Object o) {
		System.out.println(o);
	}
}
