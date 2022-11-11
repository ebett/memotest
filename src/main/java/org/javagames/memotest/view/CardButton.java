package org.javagames.memotest.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class CardButton extends JButton {

  private ImageIcon icon;
  private final ImageIcon backGroundIcon;
  private boolean pressed = false;

  public CardButton(final ImageIcon backGroundIcon) {
    this.backGroundIcon = backGroundIcon;
    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.orange.darker(), Color.gray));
    addActionListener(e -> {
      if (!pressed) {
        pressed = true;
        repaint();
      }
    });
  }

  public void rollback() {
    pressed = false;
    repaint();
  }

  public boolean isPressed() {
    return pressed;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (pressed && icon == null) return;
    Image image = (pressed ? icon : backGroundIcon).getImage();
    g.drawImage(image, 5, 5, getWidth() - 10, getHeight() - 10, this);
  }

  public boolean sameIcon(CardButton button) {
    return icon != null && icon.equals(button.icon);
  }

  public void setIcon(ImageIcon img) {
    this.icon = img;
    rollback();
  }
}
