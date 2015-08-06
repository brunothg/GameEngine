package game.engine.xxx;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import game.engine.d3.Face;
import game.engine.d3.OBJModel;
import game.engine.d3.OBJModelParser;
import game.engine.d3.OBJModelParserTest;
import game.engine.d3.OBJObject;
import game.engine.d3.Vertex;
import game.engine.time.FPS;
import game.engine.time.TimeUtils;
import game.engine.time.Timer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;

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
				OBJModelParserTest.class.getResourceAsStream("/ape.obj"),
				StandardCharsets.UTF_8)).parse();

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
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-5, 5, -5, 5, 5, -5);
		glMatrixMode(GL_MODELVIEW);
	}

	private static double rotationX;
	private static double rotationY;

	private static boolean gameLoop(long delta, float f) throws Exception {

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			return false;

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

		// Clear The Screen And The Depth Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glPushMatrix();
		glRotated(rotationX, 1f, 0f, 0f);
		glRotated(rotationY, 0f, 1f, 0f);

		double grey = 0.7;
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
