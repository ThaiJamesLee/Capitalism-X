package de.uni.mannheim.capitalismx.gamecontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game thread that calls the nextDay method of the game controller.
 * It has a speed that can be specified which in turn defines after how many seconds the thread calls the nextDay method.
 *
 * @author dzhao
 */
public class GameThread extends Thread {

	/**
	 * Enum that defines the possible speed settings, the player can choose from and
	 * the seconds the given setting takes per day.
	 */
	public enum Speed {

		SLOW(5), MEDIUM(3), FAST(1);

		private final int secondsPerDay;

		private Speed(int secondsPerDay) {
			this.secondsPerDay = secondsPerDay;
		}

		public int getSecondsPerDay() {
			return secondsPerDay;
		}
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(GameThread.class);

	private static GameThread instance;
	private Speed speed;
	private boolean pause;
	private boolean running;

	/**
	 * Gets the singleton instance of the thread.
	 *
	 * @return the singleton instance
	 */
	public static GameThread getInstance() {
		if (instance == null) {
			instance = new GameThread();
		}
		return instance;
	}

	/**
	 * Constructor of the game thread which sets the speed to slow.
	 */
	private GameThread() {
		this.speed = Speed.SLOW;
		this.running = true;
	}

	@Override
	public synchronized void run() {
		while (running) {
			if (!pause) {
				GameController.getInstance().nextDay();
			}
			try {
				wait(this.speed.getSecondsPerDay() * 1000);
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage(), e);
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Terminates the thread and sets the instance to null.
	 */
	public void terminate() {
		this.running = false;
		GameThread.instance = null;
	}

	/**
	 * Sets the speed of the game thread.
	 *
	 * @param speed
	 */
	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	/**
	 * Sets the boolean flag pause to true which in turn prevents the game thread from calling nextDay until it is set to false.
	 */
	public void pause() {
		this.pause = true;
	}

	/**
	 * Sets the boolean flag pause to false which in turn enables the gamt thread to call the nextDay method.
	 */
	public void unpause() {
		this.pause = false;
	}

	public boolean isPause() {
		return pause;
	}
	
	public Speed getSpeed() {
		return this.speed;
	}

}
