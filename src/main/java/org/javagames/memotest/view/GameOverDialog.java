package org.javagames.memotest.view;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameOverDialog extends JDialog {

  public GameOverDialog (final JFrame mainFrame, Runnable onCloseAction) {
    super(mainFrame, "Game Over", true);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setBackground(Color.BLACK);
    setSize(300, 200);
    setLocation(mainFrame.getLocation().x + 150, mainFrame.getLocation().y + 150);
    JButton label = (JButton) add(new JButton("YOU WIN! - Click to close."));
    label.setForeground(Color.RED.darker());
    label.setBackground(Color.BLACK);
    label.addActionListener((e) -> {
      setVisible(false);
      onCloseAction.run();
    });
  }

}
