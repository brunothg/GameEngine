package game.engine.xxx;

import game.engine.time.FPS;
import game.engine.time.TimeUtils;
import game.engine.time.Timer;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class D3WindowTest {

	public static void main(String[] args) {

		try {
			_main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _main(String[] args) throws Exception {

		// Display.setDisplayMode(new DisplayMode(800, 600));
		Display.setFullscreen(true);

		Display.setResizable(true);
		Display.setVSyncEnabled(true);
		Display.setTitle("3d Test");
		Display.create();

		initGL();

		Timer timer = new Timer();
		timer.reset();

		FPS fps = new FPS();
		fps.reset();

		boolean running = true;
		while (running && !Display.isCloseRequested()) {

			long delta = timer.update();

			running = gameLoop(delta, fps.getFps());

			fps.update(delta);
			Display.update();
			Display.sync(50);
		}

		Display.setFullscreen(false);
		Display.destroy();
	}

	private static void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	private static float x;
	private static float y;
	private static float rotation;
	private static boolean vsync;

	private static boolean gameLoop(long delta, float f) throws Exception {

		delta = (long) TimeUtils.Milliseconds(delta);

		// rotate quad
		rotation += 0.15f * delta;
		rotation = rotation % 360;

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			return false;
		if (Keyboard.isKeyDown(Keyboard.KEY_V))
			Display.setVSyncEnabled((vsync = !vsync));
		if (Keyboard.isKeyDown(Keyboard.KEY_F))
			Display.setFullscreen(!Display.isFullscreen());

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			x -= 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			x += 0.35f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			y -= 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			y += 0.35f * delta;

		// keep quad on the screen
		if (x < 0)
			x = 0;
		if (x > 800)
			x = 800;
		if (y < 0)
			y = 0;
		if (y > 600)
			y = 600;

		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		Color col = new Color(Color.HSBtoRGB(rotation / 360f, 1f, 0.5f));
		// R,G,B,A Set The Color To Blue One Time Only
		GL11.glColor3f(col.getRed() / 255f, col.getGreen() / 255f,
				col.getBlue() / 255f);

		// draw quad
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-x, -y, 0);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x - 50, y - 50);
		GL11.glVertex2f(x + 50, y - 50);
		GL11.glVertex2f(x + 50, y + 50);
		GL11.glVertex2f(x - 50, y + 50);
		GL11.glEnd();
		GL11.glPopMatrix();

		Display.setTitle(String.format("FPS: %.2f", f));

		return true;
	}
}
