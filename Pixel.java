package model;

/**
 * Represents a Color object that is composed of red, green, and blue.
 */
public class Pixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a Color object by taking in 3 parameters.
   *
   * @param red   red value in rgb
   * @param green green value in rgb
   * @param blue  blue value in rgb
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * returns the int representing the red value.
   *
   * @return the int representing the red value
   */
  public int getRed() {
    int r = this.red;
    return r;
  }

  /**
   * returns the int representing the green value.
   *
   * @return the int representing the green value
   */
  public int getGreen() {
    int g = this.green;
    return g;
  }

  /**
   * returns the int representing the blue value.
   *
   * @return the int representing the blue value
   */
  public int getBlue() {
    int b = this.blue;
    return b;
  }

  /**
   * returns the max value of the 3 colors.
   *
   * @return the max value of the 3 colors
   */
  public int getValue() {
    int max = red;
    if (max < blue) {
      max = blue;
    }
    if (max < green) {
      max = green;
    }
    return max;
  }

  /**
   * returns a value representing the intensity.
   *
   * @return a value representing the intensity
   */
  public int getIntensity() {
    int sum = red + green + blue;
    return sum / 3;
  }

  /**
   * Returns the luma value of the color.
   *
   * @return the luma value of the color
   */
  public int getLuma() {
    //.2126r + .7152g + .0722b
    int r = (int) (.2126 * this.red);
    int g = (int) (.7152 * this.green);
    int b = (int) (.0722 * this.blue);
    return r + g + b;
  }


}