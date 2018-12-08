/**
 * Aircraft created by flying object.
 * @author Alvin Nguyen
 * @author Spencer Enriquez
 */
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public abstract class Aircraft implements Icon {

	private int x;
	private int y;
	private int w;
	private int h;
	private int angle;
	private BufferedImage img;
	Hitbox zone;
	
	public Aircraft(int x, int y, String imgFile) {
		this.x = x;
		this.y = y;
		try {
			img = ImageIO.read(new File(imgFile));
			w = img.getWidth();
			h = img.getHeight();
			zone = new Hitbox(this);
			angle = (w == h) ? 90 : 0;
		} catch (IOException io) {
			System.out.println(io);
		}
	}
	
	public Hitbox getHitbox() {
		return zone;
	}

	public void setLocation(int x, int y) {
		//sop("\nBefore\n" + zone.printBounds());
		this.x += x;
		this.y += y;
		this.zone.resetBounds();
		//sop("\nAfter\n" + zone.printBounds());
	}	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.zone.resetBounds();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.zone.resetBounds();
	}

	public int getIconWidth() {  
		return w;
	}  

	public int getIconHeight() {  
		return h;
	}  

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		if (w == h) g2.rotate(Math.toRadians(angle), w / 2, h / 2);
		g2.drawImage(img, x, y, null);
	}

	/**
	 * Return aircraft type.
	 * @return aircraft
	 */
	public abstract String toString();

	public static void sop(Object o) {
		System.out.println(o);
	}
}
