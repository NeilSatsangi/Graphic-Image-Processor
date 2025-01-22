package model;

import java.util.HashMap;

/**
 * Represents a set of operations that can be applied to an image. Stores a set of Images in a
 * hashmap of String to Color Grids.
 */
public interface ImageModel {
  /**
   * Returns the hashmap that stores a set of strings and corresponding color grids.
   *
   * @return the hashmap that stores a set of strings and corresponding color grids
   */
  HashMap<String, Pixel[][]> getTemp();

  /**
   * Performs various different operations that modify a component of the Image that is passed.
   *
   * @param name      the name that the new image should be saved under
   * @param operation the operation that needs to be performed
   * @param oldName   the name of the current image that is being operated on
   */
  void abstractComponent(String name, String operation, String oldName);

  /**
   * Performs a flip operation that either flips an image vertically or horizontally.
   *
   * @param name      the new name that the image will be referred to as
   * @param operation the operation that should be performed
   * @param oldName   the name of the current image that is being operated on
   */
  void abstractFlip(String name, String operation, String oldName);

  /**
   * Performs a brightening or darkening operation that modified the light of the image.
   *
   * @param lightVal  the value by which to either brighten or darken the image
   * @param name      the new name that the image should be referred to as
   * @param operation the operation that should be performed
   * @param oldName   the current of the image that is being operated on
   */
  void abstractLight(int lightVal, String name, String operation, String oldName);

  /**
   * Performs a filter operation on an image that blurs or sharpens it.
   *
   * @param name      the new name that the image should be referred to as
   * @param operation the operation that should be performed
   * @param oldName   the current of the image that is being operated on
   */
  void abstractFilter(String name, String operation, String oldName);

  /**
   * Performs a color changing operation on an image sepia or grayscale it.
   *
   * @param name      the new name that the image should be referred to as
   * @param operation the operation that should be performed
   * @param oldName   the current of the image that is being operated on
   */
  void abstractColor(String name, String operation, String oldName);
}