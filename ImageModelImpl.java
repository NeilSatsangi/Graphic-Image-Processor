package model;

import java.util.HashMap;

/**
 * Represents an operations performed on image(s). Stores multiple images and manipulates them
 * with various functions. Implements ImageModel.
 */
public class ImageModelImpl implements ImageModel {
  private final HashMap<String, Pixel[][]> temporary;
  private final float[][] blur;
  private final float[][] sharp;
  private final float[][] grayscale;
  private final float[][] sepia;

  /**
   * Empty ImageModelImpl constructor. Initialized the hashmap that all the images get stored in.
   */
  public ImageModelImpl() {
    this.temporary = new HashMap<>();
    blur = new float[3][3];
    sharp = new float[5][5];
    grayscale = new float[3][3];
    sepia = new float[3][3];
    this.initializeBlur();
    this.initializeSharp();
    this.initializeGrayscale();
    this.initializeSepia();

  }

  private void initializeBlur() {
    for (int i = 0; i < blur.length; i++) {
      for (int j = 0; j < blur[0].length; j++) {
        if (i == 1 && j == 1) {
          blur[i][j] = .25f;
        } else if (i != 1 && j != 1) {
          blur[i][j] = .0625f;
        } else {
          blur[i][j] = .125f;
        }
      }
    }
  }

  private void initializeSharp() {
    for (int i = 0; i < sharp.length; i++) {
      for (int j = 0; j < sharp[0].length; j++) {
        if (i == 0 || j == 0 || i == sharp.length - 1 || j == sharp.length - 1) {
          sharp[i][j] = -.125f;
        } else if ((Math.abs(i - 2) == 1 || Math.abs(j - 2) == 1)) {
          sharp[i][j] = .25f;
        }
      }
    }
    sharp[2][2] = 1f;
  }

  private void initializeGrayscale() {
    for (int i = 0; i < grayscale.length; i++) {
      for (int j = 0; j < grayscale[0].length; j++) {
        if (j == 0) {
          grayscale[i][j] = .2126f;
        } else if (j == 1) {
          grayscale[i][j] = .7152f;
        } else {
          grayscale[i][j] = .0722f;
        }
      }
    }
  }

  private void initializeSepia() {
    sepia[0][0] = .393f;
    sepia[0][1] = .769f;
    sepia[0][2] = .189f;
    sepia[1][0] = .349f;
    sepia[1][1] = .686f;
    sepia[1][2] = .168f;
    sepia[2][0] = .272f;
    sepia[2][1] = .534f;
    sepia[2][2] = .131f;
  }

  /**
   * Getter method for the hashmap within the class.
   *
   * @return a hashmap of Strings to 2D Arrays
   */
  @Override
  public HashMap<String, Pixel[][]> getTemp() {
    return this.temporary;
  }

  /**
   * Performs a component modification to the image model.
   *
   * @param name      the name of the image to be modified
   * @param operation the name of the operation to be performed
   */
  @Override
  public void abstractComponent(String name, String operation, String oldName) {
    //Color[][] colorGrid = image.getGrid();
    Pixel[][] pixelGrid = temporary.get(oldName);
    int width = pixelGrid.length;
    int height = pixelGrid[0].length;
    Pixel[][] newGrid = new Pixel[width][height];

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Pixel current = pixelGrid[i][j];
        Pixel newPixel = new Pixel(0, 0, 0);
        switch (operation) {
          case "red-component":
            newPixel = new Pixel(current.getRed(), current.getRed(), current.getRed());
            break;
          case "green-component":
            newPixel = new Pixel(current.getGreen(), current.getGreen(), current.getGreen());
            break;
          case "blue-component":
            newPixel = new Pixel(current.getBlue(), current.getBlue(), current.getBlue());
            break;
          case "value-component":
            newPixel = new Pixel(current.getValue(), current.getValue(), current.getValue());
            break;
          case "intensity-component":
            newPixel = new Pixel(current.getIntensity(),
                    current.getIntensity(), current.getIntensity());
            break;
          case "luma-component":
            newPixel = new Pixel(current.getLuma(), current.getLuma(), current.getLuma());
            break;
          default:
            System.out.print("hit default");
            break;
        }
        newGrid[i][j] = newPixel;
      }
    }
    temporary.put(name, newGrid);
  }

  /**
   * Performs either a vertical or horizontal flipping operation.
   *
   * @param name      the name of the file to be operated on
   * @param operation the name of the operation to be performed
   */
  @Override                 //new name //operation      //oldName
  public void abstractFlip(String name, String operation, String oldName) {

    Pixel[][] pixelGrid = temporary.get(oldName);
    int width = pixelGrid.length;
    int height = pixelGrid[0].length;
    Pixel[][] newGrid = new Pixel[width][height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        //Color current = colorGrid[i][j];
        Pixel newPixel = new Pixel(0, 0, 0);
        if (operation.equals("vertical-flip")) {
          newPixel = pixelGrid[i][height - 1 - j];
        }
        if (operation.equals("horizontal-flip")) {
          newPixel = pixelGrid[width - 1 - i][j];
        }
        newGrid[i][j] = newPixel;
      }
    }
    temporary.put(name, newGrid);
  }

  /**
   * Either brightens or darkens an image.
   *
   * @param lightVal  the value by which to either brighten or darken the image
   * @param name      the name of the image to be either brightened or darkened
   * @param operation the operation that tells us whether to brighten or darken the image
   */
  @Override
  public void abstractLight(int lightVal, String name, String operation, String oldName) {
    Pixel[][] pixelGrid = temporary.get(oldName);
    int width = pixelGrid.length;
    int height = pixelGrid[0].length;
    Pixel[][] newGrid = new Pixel[width][height];

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int newRed;
        int newGreen;
        int newBlue;
        Pixel current = pixelGrid[i][j];
        Pixel newPixel = new Pixel(0, 0, 0);
        if (operation.equals("brighten")) {
          if (current.getRed() + lightVal >= 255) {
            newRed = 255;
          } else {
            newRed = current.getRed() + lightVal;
          }
          if (current.getGreen() + lightVal >= 255) {
            newGreen = 255;
          } else {
            newGreen = current.getGreen() + lightVal;
          }
          if (current.getBlue() + lightVal >= 255) {
            newBlue = 255;
          } else {
            newBlue = current.getBlue() + lightVal;
          }
          newPixel = new Pixel(newRed
                  , newGreen
                  , newBlue);
        }
        if (operation.equals("darken")) {
          if (current.getRed() - lightVal <= 0) {
            newRed = 0;
          } else {
            newRed = current.getRed() - lightVal;
          }
          if (current.getGreen() - lightVal <= 0) {
            newGreen = 0;
          } else {
            newGreen = current.getGreen() - lightVal;
          }
          if (current.getBlue() - lightVal <= 0) {
            newBlue = 0;
          } else {
            newBlue = current.getBlue() - lightVal;
          }
          newPixel = new Pixel(newRed
                  , newGreen
                  , newBlue);
        }
        newGrid[i][j] = newPixel;
      }
    }
    temporary.put(name, newGrid);
    System.out.println("s");
  }

  /**
   * Either blurs or sharpens an image.
   *
   * @param name      the new name that the image should be referred to as
   * @param operation the operation that should be performed
   * @param oldName   the current of the image that is being operated on
   */
  @Override
  public void abstractFilter(String name, String operation, String oldName) {
    Pixel[][] pixelGrid = temporary.get(oldName);
    int width = pixelGrid.length;
    int height = pixelGrid[0].length;
    Pixel[][] newGrid = new Pixel[width][height];
    float[][] kernel;
    if (operation.equals("blur")) {
      kernel = blur;
    } else {
      kernel = sharp;
    }
    int value = kernel.length / 2;

    float red = 0f;
    float green = 0f;
    float blue = 0f;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        for (int x = value; x >= -value; x--) {
          for (int y = value; y >= -value; y--) {
            int pixelGridX = i - x;
            int pixelGridY = j - y;
            int kernelX = value - x;
            int kernelY = value - y;
            try {
              red += (pixelGrid[pixelGridX][pixelGridY].getRed() * kernel[kernelY][kernelX]);
              green += (pixelGrid[pixelGridX][pixelGridY].getGreen() * kernel[kernelY][kernelX]);
              blue += (pixelGrid[pixelGridX][pixelGridY].getBlue() * kernel[kernelY][kernelX]);
            } catch (ArrayIndexOutOfBoundsException e) {
              System.out.print("");
            }
          }
        }
        if (red > 255) {
          red = 255f;
        }
        if (green > 255) {
          green = 255f;
        }
        if (blue > 255) {
          blue = 255f;
        }
        if (red < 0) {
          red = 0f;
        }
        if (green < 0) {
          green = 0f;
        }
        if (blue < 0) {
          blue = 0f;
        }
        Pixel newPixel = new Pixel((int) red, (int) green, (int) blue);
        newGrid[i][j] = newPixel;
        red = 0f;
        green = 0f;
        blue = 0f;
      }
    }
    temporary.put(name, newGrid);
  }

  /**
   * Converts an image to either grayscale or sepia.
   *
   * @param name      the new name that the image should be referred to as
   * @param operation the operation that should be performed
   * @param oldName   the current of the image that is being operated on
   */
  @Override
  public void abstractColor(String name, String operation, String oldName) {
    Pixel[][] pixelGrid = temporary.get(oldName);
    int width = pixelGrid.length;
    int height = pixelGrid[0].length;
    Pixel[][] newGrid = new Pixel[width][height];
    float red = 0f;
    float green = 0f;
    float blue = 0f;
    float[] colors = new float[3];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        colors[0] = pixelGrid[i][j].getRed();
        colors[1] = pixelGrid[i][j].getGreen();
        colors[2] = pixelGrid[i][j].getBlue();
        for (int x = 0; x < colors.length; x++) {
          if (operation.equals("grayscale")) {
            try {
              red += grayscale[0][x] * colors[x];
              green += grayscale[1][x] * colors[x];
              blue += grayscale[2][x] * colors[x];
            } catch (ArrayIndexOutOfBoundsException e) {
              System.out.print("");
            }
          } else {
            try {
              red += sepia[0][x] * colors[x];
              green += sepia[1][x] * colors[x];
              blue += sepia[2][x] * colors[x];
            } catch (ArrayIndexOutOfBoundsException e) {
              System.out.print("");
            }
          }
        }
        if (red > 255) {
          red = 255f;
        }
        if (green > 255) {
          green = 255f;
        }
        if (blue > 255) {
          blue = 255f;
        }
        if (red < 0) {
          red = 0f;
        }
        if (green < 0) {
          green = 0f;
        }
        if (blue < 0) {
          blue = 0f;
        }
        Pixel newPixel = new Pixel((int) red, (int) green, (int) blue);
        newGrid[i][j] = newPixel;
        red = 0f;
        green = 0f;
        blue = 0f;
        colors[0] = 0f;
        colors[1] = 0f;
        colors[2] = 0f;
      }
    }
    temporary.put(name, newGrid);
  }


}






