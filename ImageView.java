package view;

/**
 * Represents a view for the ImageModel and the ImageController that displays a visual.
 * representation of text that prompts the user and guides them through the program
 */
public interface ImageView {

  /**
   * Displays a text message that prompts the user while running the program.
   *
   * @param message the message to be displayed
   */
  void renderMessage(String message);

}
