/*
 * TimeClock is responsible for keeping the time of the current game
 * @author Spencer Enriquez
 */
public class TimeClock{
	
	private int secondsCounter = 0;
	private String timerFormat;
	
	/*
	 * Create TimeClock object starting at String of '0:00'
	 */
	public TimeClock() {
		timerFormat = "0:00";
		secondsCounter = 0;
	}
	
	
	public void addSecond() {
		secondsCounter++;
	}
	
	
	/*
	 * Sets timerFormat to the current time in the "0:00" format
	 * Uses secondsCounter to find values
	 */
	public String changeTime() {
		String seconds = "" + secondsCounter % 60;
		if (seconds.length() < 2) 
			seconds = "0" + seconds;
		timerFormat = "Game Time: " + secondsCounter / 60 + ":" + seconds + " | ";
		return timerFormat;
	}
	
	
	public String getTimeFormat() {
		return timerFormat;
	}
	
	public int getSeconds() {
		return secondsCounter;
	}
	
	/*
	 * Reset timer by instantiating new ZonedDateTime and repainting.
	 */
	public void reset() {
		secondsCounter = 0;
	}
	
	
	/* Tester Frame:
	 * JFrame frame = new JFrame();
      TimeClock time = new TimeClock();
      
      Drone drone = new Drone(100,100, "drone.png");
      
      Scoreboard scores = new Scoreboard();
      JLabel scoreLabel = new JLabel(scores.setScore());
      
      String text = time.getTimeFormat();
      JLabel timeLabel= new JLabel(text);
      Timer update = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			time.addSecond();
			scores.checkScore(time, drone);
			scoreLabel.setText(scores.setScore());
			scoreLabel.repaint();
			if (time.getSeconds() >= 90)
				time.reset();
			timeLabel.setText(time.changeTime());
			timeLabel.repaint();
		}
      });
      update.start();
      
      frame.setLayout(new FlowLayout());
      frame.add(scoreLabel);
      frame.add(timeLabel);
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
	 */
}
