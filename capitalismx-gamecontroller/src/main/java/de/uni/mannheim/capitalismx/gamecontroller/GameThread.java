package de.uni.mannheim.capitalismx.gamecontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	public static GameThread getInstance() {
		if (instance == null) {
			instance = new GameThread();
		}
		return instance;
	}

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

	public void terminate() {
		this.running = false;
		GameThread.instance = null;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	public void pause() {
		this.pause = true;
	}

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
