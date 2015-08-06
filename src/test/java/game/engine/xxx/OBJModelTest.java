package game.engine.xxx;

import static org.lwjgl.opengl.GL11.*;
import game.engine.d3.Face;
import game.engine.d3.OBJModel;
import game.engine.d3.OBJModelParser;
import game.engine.d3.OBJObject;
import game.engine.d3.Vertex;
import game.engine.time.FPS;
import game.engine.time.TimeUtils;
import game.engine.time.Timer;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class OBJModelTest {

	private static OBJModel model;

	public static void main(String[] args) {

		try {
			_main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _main(String[] args) throws Exception {

		model = new OBJModelParser(new InputStreamReader(
				OBJModelTest.class.getResourceAsStream("/ape.obj"),
				StandardCharsets.UTF_8)).parse();

		Display.setDisplayMode(new DisplayMode(800, 600));
		// Display.setFullscreen(true);

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
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		int size = calculateSize();

		glOrtho(-size, size, -size, size, size, -size);
		glMatrixMode(GL_MODELVIEW);
	}

	private static int calculateSize() {

		int size = 0;

		List<OBJObject> objects = model.getObjects();
		for (OBJObject obj : objects) {
			List<Face> faces = obj.getFaces();
			for (Face face : faces) {
				Vertex[] vertices = face.getVertices();
				for (Vertex vertex : vertices) {
					size = Math.max(size, (int) vertex.getX() + 1);
					size = Math.max(size, (int) vertex.getY() + 1);
					size = Math.max(size, (int) vertex.getZ() + 1);
				}
			}
		}

		return (int) (size * 1.4) + 1;
	}

	private static double rotationX;
	private static double rotationY;
	private static boolean blink = false;

	private static void keyboard(long delta) {

		while (Keyboard.next()) {
			if (!Keyboard.getEventKeyState()
					&& Keyboard.getEventKey() == Keyboard.KEY_B) {
				blink = !blink;
			}
		}

		delta = (long) TimeUtils.Milliseconds(delta);
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			rotationY += 0.15f * delta;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			rotationY -= 0.15f * delta;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			rotationX += 0.15f * delta;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			rotationX -= 0.15f * delta;
		}
	}

	private static boolean gameLoop(long delta, float f) throws Exception {

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			return false;
		keyboard(delta);

		// Clear The Screen And The Depth Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glPushMatrix();
		glRotated(rotationX, 1f, 0f, 0f);
		glRotated(rotationY, 0f, 1f, 0f);

		double grey = 0.8;
		glColor3d(grey, grey, grey);

		List<OBJObject> objects = model.getObjects();
		for (OBJObject obj : objects) {
			List<Face> faces = obj.getFaces();
			for (Face face : faces) {
				Vertex[] vertices = face.getVertices();
				if (vertices.length == 3) {
					glBegin(GL_TRIANGLES);
				} else if (vertices.length == 4) {
					glBegin(GL_QUADS);
				} else {
					glBegin(GL_POLYGON);
				}
				if (blink) {
					grey = Math.max(0.1, Math.random());
					glColor3d(grey, grey, grey);
				}

				for (Vertex vert : vertices) {
					glVertex4d(vert.getX(), vert.getY(), vert.getZ(),
							vert.getWeight());
				}

				glEnd();
			}
		}
		glPopMatrix();

		Display.setTitle(String.format("FPS: %.2f", f));
		return true;
	}

}
