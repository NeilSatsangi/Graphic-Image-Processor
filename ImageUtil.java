package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method
 * as required.
 */
public class ImageUtil {
  /**
   * Read an image file in the PPM format and print the colors.
   * turns it into a 2d array of colors.
   *
   * @param filename the file that is operated on
   * @return 2d array of colors representing the image file
   */
  public static Pixel[][] readPPM(String filename) {

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return new Pixel[0][0];
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;
    token = sc.next();

    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    //System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    //System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    //System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);
    Pixel[][] pixelGrid = new Pixel[width][height];


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel pixel = new Pixel(r, g, b);
        pixelGrid[j][i] = pixel;
      }
    }

    return pixelGrid;
  }


  /**
   * Writes PPM text files that uses the content of color grid to make PPM file
   * and save it to src/Images.
   *
   * @param grid the color grid that gets converted to a PPM file.
   * @param name the name of the file we create
   * @throws IOException if file not found
   */
  public static void writePPM(Pixel[][] grid, String name) throws IOException {
    StringBuilder content = new StringBuilder();

    FileOutputStream writer;

    if (name.contains("/")) {
      writer = new FileOutputStream(name + ".ppm");
    } else {
      writer = new FileOutputStream("src/res/" + name + ".ppm");
    }


    content.append("P3" + "\n");
    content.append(grid.length + " " + grid[0].length + "\n");
    content.append(255 + "\n");
    //height
    for (int i = 0; i < grid[0].length; i++) {
      //width
      for (int j = 0; j < grid.length; j++) {
        content.append(grid[j][i].getRed() + "\n");
        content.append(grid[j][i].getGreen() + "\n");
        content.append(grid[j][i].getBlue() + "\n");
      }
    }

    byte[] b = content.toString().getBytes();
    writer.write(b);
    writer.close();
  }


  /**
   * reads a png/jpg/.jpeg file and creates a pixel 2D array
   *
   * @param filename the name of the file that we are reading in
   * @return a 2D array of pixels
   * @throws IOException if file is not found
   */
  public static Pixel[][] readOther(String filename) throws IOException {
    //Reading the image
    File file = new File(filename);

    BufferedImage img = ImageIO.read(file);
    Pixel[][] pixelGrid = new Pixel[img.getWidth()][img.getHeight()];
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        //Retrieving contents of a pixel
        int pixel = img.getRGB(x, y);
        //Creating a Color object from pixel value
        Color color = new Color(pixel, true);
        //Retrieving the R G B values
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        Pixel pix = new Pixel(red, green, blue);
        pixelGrid[x][y] = pix;
        //System.out.println("cor"+x +" "+ y +" "+ red+" " + green+" " +blue);

      }
    }
    System.out.println("RGB values at each pixel are stored in the specified file");
    return pixelGrid;
  }

  /**
   * Writes an image file and saves it as a type (.jpg, .jpeg, .png, etc)
   *
   * @param grid the color grid to be converted into an image
   * @param name of the file that we want to save it under
   * @throws IOException if file is not found
   */
  public static void writeFile(Pixel[][] grid, String name) throws IOException {
    int width = grid.length;
    int height = grid[0].length;
    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    int index = 0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        Pixel color = grid[j][i];

        Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
        newImage.setRGB(j, i, newColor.getRGB());
      }
    }
    File output;

    if (name.contains("/")) {
      output = new File(name);
    } else {
      output = new File("src/res/" + name);
    }

    System.out.println(name + "name");

    int indexPeriod = name.indexOf('.');
    String type = name.substring(indexPeriod + 1);
    ImageIO.write(newImage, type, output);
  }


  /**
   * Takes in a Pixel Grid and makes a buffered image using it.
   * @param grid the grid of pixels that will be turned into a buffered image
   * @return a BufferedImage of the PixelGrid
   * @throws IOException if file path doesn't exist
   */
  public static BufferedImage writeImage(Pixel[][] grid) throws IOException {
    int width = grid.length;
    int height = grid[0].length;
    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    int index = 0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        Pixel color = grid[j][i];

        Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
        newImage.setRGB(j, i, newColor.getRGB());
      }
    }
    return newImage;
  }


  /**
   * Demo main method to observe capability to read files
   * given method.
   *
   * @param args takes in input
   */
  public static void main(String[] args) throws IOException {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "src/res/blurry.jpg";
    }

    ImageUtil.readOther(filename);
    //ImageUtil.writeFile();
    System.out.println("test");
    //System.out.println(ImageUtil.readPPM(filename)[1021][767].toString());
  }


}

