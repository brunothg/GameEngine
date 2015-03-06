package game.engine.time;

public class ClockTest {

	public static void main(String[] args) {

		final Timer timer = new Timer();

		final Clock clk = new Clock(2);
		clk.addClockListener(new ClockListener() {

			@Override
			public void tick(long frames, long coveredTime) {

				double fps = Math.round(1 / (double) TimeUtils.Seconds(timer
						.update()));

				System.out
						.printf("FPS --> %.2f Secs --> %.2f Frames --> %d(%.2f secs) \n",
								fps, TimeUtils.Seconds(coveredTime), frames,
								TimeUtils.Seconds(coveredTime));
			}
		});

		timer.reset();
		clk.start();

		new Thread(new Runnable() {

			@Override
			public void run() {

				boolean pause = true;
				while (true) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println("pause " + pause);
					clk.setPaused(pause);
					pause = !pause;

					if (!pause) {
						timer.update();
					}
				}
			}
		}).start();
	}
}
