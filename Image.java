package model;

/**
 * represents an Image. An Image is made of a 2d array of Colors. Includes the width and height of
 * the grid.
 */
public class Image {
  private final Pixel[][] pixelGrid;
  private final int width;
  private final int height;

  /**
   * Constructor for Image class, assigns a colorGrid
   * and derives width and height from inputted colorGrid.
   *
   * @param pixelGrid takes in a 2d array of Colors
   */
  public Image(Pixel[][] pixelGrid) {
    this.pixelGrid = pixelGrid;
    this.width = pixelGrid.length;
    this.height = pixelGrid[0].length;
  }

  /**
   * returns the colorGrid attribute so can be used in other classes.
   *
   * @return the colorGrid
   */
  public Pixel[][] getGrid() {
    return this.pixelGrid;
  }

  /**
   * returns the width attribute so can be used in other classes.
   *
   * @return the width
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * returns the height attribute so can be used in other classes.
   *
   * @return the height
   */
  public int getHeight() {
    return this.height;
  }
}
