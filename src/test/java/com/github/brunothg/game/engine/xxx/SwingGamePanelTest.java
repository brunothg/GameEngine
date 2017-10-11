package com.github.brunothg.game.engine.xxx;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.brunothg.game.engine.d2.frame.SwingGamePanel;
import com.github.brunothg.game.engine.d2.scene.FPSScene;
import com.github.brunothg.game.engine.d2.scene.LoadingScene;

public class SwingGamePanelTest {

	public static void main(String[] args) {

		final SwingGamePanel gamePanel = new SwingGamePanel();
		gamePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		gamePanel.setScene(new FPSScene(new LoadingScene(true)));

		JFrame disp = new JFrame("SwingGamePanel Test");
		disp.setLayout(new BorderLayout());
		disp.add(gamePanel, BorderLayout.CENTER);

		final JSlider jSlider = new JSlider(1, 200);
		jSlider.setValue((int) gamePanel.getFramesPerSecond());
		jSlider.setBorder(BorderFactory.createTitledBorder("FPS - " + jSlider.getValue()));
		jSlider.setPaintLabels(true);
		jSlider.setPaintTicks(true);
		jSlider.setPaintTrack(true);
		jSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				gamePanel.setFramesPerSecond(jSlider.getValue());
				jSlider.setBorder(BorderFactory.createTitledBorder("FPS - " + jSlider.getValue()));
			}
		});
		disp.add(jSlider, BorderLayout.SOUTH);

		disp.setSize(800, 600);
		disp.setLocationRelativeTo(null);
		disp.setVisible(true);
	}

}
