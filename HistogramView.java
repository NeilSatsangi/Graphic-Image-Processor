package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Creates a histrogram using data from the histogram class.
 * The histogram is 1020 values long, there are 4 colors and 255 values for each color.
 * Shows the frequency of each value.
 */
public class HistogramView extends JFrame {

  Histogram histo;

  /**
   * Constructor takes in a histogram which has the frequency for the colors.
   *
   * @param histo a histogram that gets the frequency for the 4 colors.
   */
  public HistogramView(Histogram histo) {
    this.histo = histo;
  }

  /**
   * Creates a BufferedImage which is a view for the histogram.
   *
   * @return a Buffered Image which is an image of the histogram.
   */
  public BufferedImage newPanel() {
    int[] red = histo.getRedComponent();
    int[] blue = histo.getBlueComponent();
    int[] green = histo.getGreenComponent();
    int[] intensity = histo.getIntensityComponent();
    BufferedImage img = new BufferedImage(1100, 500, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = img.createGraphics();
    int position = 0;
    for (int i = 0; i < red.length; i++) {
      //red
      g.setColor(Color.RED);
      g.fillRect(position, 0, 2, red[i] / 10);
      position += 2;
      g.setColor(Color.GREEN);
      g.fillRect(position, 0, 2, green[i] / 10);
      position += 2;
      g.setColor(Color.blue);
      g.fillRect(position, 0, 2, blue[i] / 10);
      position += 2;
      g.setColor(Color.lightGray);
      g.fillRect(position, 0, 2, intensity[i] / 10);
      position += 2;
    }
    g.dispose();
    return img;
  }


}
