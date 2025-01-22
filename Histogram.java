package view;

import model.Pixel;

/**
 * Represents an image histogram. A histogram is a table of (value,frequency) entries. In this case
 * the values are 0-255 for each color, (red,green,blue,intensity).
 */
public class Histogram {
  private int[] red = new int[256];
  private int[] green = new int[256];
  private int[] blue = new int[256];
  private int[] intensity = new int[256];

  private Pixel[][] img;

  /**
   * Constructor takes in a pixel grid used to find the frequency of each value for each color.
   *
   * @param img the inputted pixel grid.
   */
  public Histogram(Pixel[][] img) {
    this.img = img;
  }

  /**
   * Sets all color array to 0.
   */
  public void setToZero() {
    for (int i = 0; i < 256; i++) {
      red[i] = 0;
      green[i] = 0;
      blue[i] = 0;
      intensity[i] = 0;
    }
  }

  /**
   * goes through the pixelGrid and adds 1 to all the values every time the value occurs in the
   * image.
   */
  public void setComponentHisto() {
    for (int i = 0; i < img.length; i++) {
      for (int j = 0; j < img[0].length; j++) {
        int x;
        x = img[i][j].getRed();
        red[x] += 1;
        x = img[i][j].getGreen();
        green[x] += 1;
        x = img[i][j].getBlue();
        blue[x] += 1;
        x = img[i][j].getIntensity();
        intensity[x] += 1;
      }
    }
  }

  /**
   * Gets the array representing frequencies in the red color path.
   *
   * @return Array representing frequencies in the red color path.
   */
  public int[] getRedComponent() {
    return red;
  }

  /**
   * Gets the array representing frequencies in the green color path.
   *
   * @return Array representing frequencies in the green color path.
   */
  public int[] getGreenComponent() {
    return green;
  }

  /**
   * Gets the array representing frequencies in the blue color path.
   *
   * @return Array representing frequencies in the blue color path.
   */
  public int[] getBlueComponent() {
    return blue;
  }

  /**
   * Gets the array representing frequencies in the intensity color path.
   *
   * @return Array representing frequencies in the intensity color path.
   */
  public int[] getIntensityComponent() {
    return intensity;
  }


}
