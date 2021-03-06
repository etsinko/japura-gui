package org.japura.gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

/**
 * Copyright (C) 2014-2015 Carlos Eduardo Leite de Andrade
 * <P>
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <P>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <P>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <A
 * HREF="www.gnu.org/licenses/">www.gnu.org/licenses/</A>
 * <P>
 * For more information, contact: <A HREF="www.japura.org">www.japura.org</A>
 * <P>
 * 
 * @author Carlos Eduardo Leite de Andrade
 */
public class TitleBar extends PaintedPanel {

  private static final long serialVersionUID = 4L;

  private static Object defaultTitleBackground = new Gradient(
    Gradient.TOP_TO_BOTTOM, new Color(160, 190, 255), new Color(240, 240, 255));

  private Insets titleMargin = new Insets(3, 3, 3, 3);
  private JLabel titleLabel;
  private Object titleBackground;
  private int iconWidth;
  private int gapBetweenIconAndTitle = 5;
  private int gapBetweenTitleAndComponents = 15;
  private int gapBetweenComponents = 5;
  private JComponent[] titleComponents;
  private JPanel iconBase;

  protected TitleBar(Icon icon, String title, JComponent[] titleComponents) {
    if (title == null || title.length() == 0) {
      title = " ";
    }
    if (defaultTitleBackground instanceof Gradient) {
      setTitleBackground((Gradient) defaultTitleBackground);
    }
    else if (defaultTitleBackground instanceof Color) {
      setTitleBackground((Color) defaultTitleBackground);
    }

    this.titleComponents = titleComponents;
    if (this.titleComponents == null) {
      this.titleComponents = new JComponent[0];
    }

    if (icon != null) {
      this.iconWidth = icon.getIconWidth();
      this.iconBase = new JPanel();
      this.iconBase.setMinimumSize(new Dimension(icon.getIconWidth(), 1));
      this.iconBase.setPreferredSize(new Dimension(icon.getIconWidth(), 1));
      this.iconBase.setOpaque(false);
    }

    getTitleLabel().setText(title);

    this.titleMargin = new Insets(5, 5, 5, 5);

    rebuild();
  }

  public void rebuild() {
    removeAll();

    Insets margin = getTitleMargin();

    StringBuilder param2 = new StringBuilder();
    param2.append(margin.left);
    param2.append("[]");
    if (this.iconWidth > 0) {
      param2.append(this.gapBetweenIconAndTitle);
      param2.append("[]");
    }

    if (this.titleComponents.length > 0) {
      param2.append(this.gapBetweenTitleAndComponents);
      param2.append(":push");
    }
    for (int i = 0; i < this.titleComponents.length; i++) {
      param2.append("[]");
      if (i < this.titleComponents.length - 1) {
        param2.append(this.gapBetweenComponents);
      }
    }
    param2.append(margin.right);

    StringBuilder param3 = new StringBuilder();
    param3.append(margin.top);
    param3.append("[align bottom]");
    param3.append(margin.bottom);

    setLayout(new MigLayout("", param2.toString(), param3.toString()));

    if (this.iconWidth > 0) {
      add(getIconBase());
    }
    add(getTitleLabel());
    for (JComponent comp : this.titleComponents) {
      add(comp);
    }

    revalidate();
  }

  protected JPanel getIconBase() {
    return iconBase;
  }

  /**
   * Defines the title gaps
   * 
   * @param gapBetweenIconAndTitle gap between the icon and title
   * @param gapBetweenTitleAndComponents gap between the title and title
   *        components
   * @param gapBetweenComponents gap between title components
   */
  public void setTitleGaps(int gapBetweenIconAndTitle,
    int gapBetweenTitleAndComponents, int gapBetweenComponents) {
    gapBetweenIconAndTitle = Math.max(gapBetweenIconAndTitle, 0);
    gapBetweenTitleAndComponents = Math.max(gapBetweenTitleAndComponents, 0);
    gapBetweenComponents = Math.max(gapBetweenComponents, 0);
    rebuild();
  }

  public int getGapBetweenIconAndTitle() {
    return gapBetweenIconAndTitle;
  }

  public int getGapBetweenTitleAndTitleComponents() {
    return gapBetweenTitleAndComponents;
  }

  public int getGapBetweenTitleComponents() {
    return gapBetweenComponents;
  }

  protected JLabel getTitleLabel() {
    if (titleLabel == null) {
      titleLabel = new JLabel();
    }
    return titleLabel;
  }

  @Override
  public Font getFont() {
    return getTitleLabel().getFont();
  }

  @Override
  public void setFont(Font font) {
    getTitleLabel().setFont(font);
  }

  public void setTitle(String title) {
    getTitleLabel().setText(title);
  }

  public String getTitle() {
    return getTitleLabel().getText();
  }

  public void setTitleForeground(Color color) {
    getTitleLabel().setForeground(color);
  }

  public Color getTitleForeground() {
    return getTitleLabel().getForeground();
  }

  public void setTitleBackground(Color color) {
    if (color != null) {
      this.titleBackground = color;
      removeBackgrounds();
      addBackground(color);
    }
  }

  public void setTitleBackground(Gradient gradient) {
    if (gradient != null && gradient.getDirection() != null
      && gradient.getFirstColor() != null && gradient.getSecondColor() != null) {
      this.titleBackground = gradient;
      removeBackgrounds();
      addBackground(gradient);
    }
  }

  public Object getTitleBackground() {
    return titleBackground;
  }

  public boolean hasTitleBackgroundGradient() {
    if (titleBackground instanceof Gradient) {
      return true;
    }
    return false;
  }

  public Insets getTitleMargin() {
    return (Insets) titleMargin.clone();
  }

  public void setTitleMargin(Insets titleMargin) {
    if (titleMargin == null) {
      this.titleMargin = new Insets(0, 0, 0, 0);
    }
    else {
      int top = Math.max(titleMargin.top, 0);
      int left = Math.max(titleMargin.left, 0);
      int bottom = Math.max(titleMargin.bottom, 0);
      int right = Math.max(titleMargin.right, 0);
      this.titleMargin = new Insets(top, left, bottom, right);
    }
    rebuild();
  }

  public static Object getDefaultTitleBackground() {
    return defaultTitleBackground;
  }

  public static void setDefaultTitleBackground(Color color) {
    if (color != null) {
      TitleBar.defaultTitleBackground = color;
    }
  }

  public static void setDefaultTitleBackground(Gradient gradient) {
    if (gradient != null && gradient.getDirection() != null
      && gradient.getFirstColor() != null && gradient.getSecondColor() != null) {
      TitleBar.defaultTitleBackground = gradient;
    }
  }
}
