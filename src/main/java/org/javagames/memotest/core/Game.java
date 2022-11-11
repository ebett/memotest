package org.javagames.memotest.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.javagames.memotest.view.CardButton;
import org.javagames.memotest.view.GameOverDialog;

public class Game implements ActionListener {

  public static final int COLS = 4;
  public static final int ROWS = 4;
  private static final int NUM_CARDS =  ROWS * COLS;
  private static final int MAX_CARDS =  NUM_CARDS / 2;

  private CardButton button1;

  private int hits;

  private final ImageLoader imageLoader = new ImageLoader();

  private final JFrame mainFrame;

  private JDialog winDialog;

  private final List<CardButton> cardButtons = new ArrayList<>(NUM_CARDS);

  private List<ImageIcon> cardIcons;

  public Game(final JFrame mainFrame) {
    this.mainFrame = mainFrame;
    init();
  }

  private void init() {
    final ImageIcon bg = imageLoader.loadIcon("/images/bg.png");
    cardIcons = imageLoader.loadIcons();

    for (int i = 0; i < NUM_CARDS; i++) {
      CardButton cardButton = new CardButton(bg);
      cardButton.addActionListener(this);
      mainFrame.add(cardButton);
      cardButtons.add(cardButton);
    }
  }

  public void start() {
    button1 = null;
    hits = 0;

    Collections.shuffle(cardIcons);

    List<ImageIcon> imageIcons = cardIcons.subList(0, MAX_CARDS);

    int c = 0;
    Collections.shuffle(imageIcons);
    for (int i = 0; i < MAX_CARDS; i++, c++) {
      cardButtons.get(c).setIcon(imageIcons.get(i));
    }

    Collections.shuffle(imageIcons);
    for (int i = 0; i < MAX_CARDS; i++, c++) {
      cardButtons.get(c).setIcon(imageIcons.get(i));
    }
    mainFrame.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() instanceof CardButton) {
      final CardButton source = (CardButton) e.getSource();
      if (source.isPressed()) {
        return;
      }

      if (button1 == null) {
        button1 = source;
      } else {
        if (!button1.sameIcon(source)) {
          DelayedAction.delay(1500, new UnPressButtonsAnimation(button1, source));
        } else {
          hits++;
          if (hits == MAX_CARDS) {
            showGameOverDialog();
          }
        }
        button1 = null;
      }
    }
  }

  private void showGameOverDialog() {
    if (winDialog == null) {
      winDialog = new GameOverDialog(mainFrame, () -> start());
    }

    DelayedAction.delay(100, () -> winDialog.setVisible(true));
  }

  private static final class UnPressButtonsAnimation implements Runnable {
    private final CardButton[] buttons;

    private UnPressButtonsAnimation(final CardButton... buttons) {
      this.buttons = buttons;
    }

    @Override
    public void run() {
      for (CardButton btn: buttons) {
        btn.rollback();
      }
    }
  }
}
