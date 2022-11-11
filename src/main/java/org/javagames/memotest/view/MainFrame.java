package org.javagames.memotest.view;

import org.javagames.memotest.core.Game;
import java.awt.GridLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

  public static void main(String[] args) {
    new MainFrame().setVisible(true);
  }

  public MainFrame() {
    super("Memo test");
    setLocation(200, 200);
    setSize(600, 800);
    setLayout(new GridLayout(Game.ROWS, Game.COLS, 4, 4));
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    new Game(this).start();
  }

}
