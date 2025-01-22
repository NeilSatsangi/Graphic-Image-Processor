package controller;

import java.io.IOException;

/**
 * Represents a Controller for the Image Modifier Package that allows the user to interact with the
 * model.
 */
public interface ImageController {

  /**
   * Saves a file to a specified location on the user's device.
   *
   * @param fileName what the file will be called when it is saved
   * @param nameKey  the key within the hashmap to identify the file
   * @throws IOException if the user tries to save a file that hasn't been loaded or does not exist
   */
  void saveImage(String fileName, String nameKey) throws IOException;

  /**
   * add color Grid based on fileName and String name to loadImage.
   */
  void loadImage(String filePath, String name);

  /**
   * Allows the user to interact with the program by specifying commands that indicate which
   * operation they would like to perform on a specified image.
   *
   * @throws IOException if any inputs are invalid
   */
  void completeOperation() throws IOException;


}
