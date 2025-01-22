package view;

import java.io.IOException;

import model.ImageModel;

/**
 * Implements the ImageView interface and provides all functionality to display and render any
 * messages necessary.
 */
public class ImageViewImpl implements ImageView {
  private ImageModel model;
  private Appendable ap;

  /**
   * Constructor for the ImageViewImpl that sets the Appendable to System. Out by default.
   *
   * @param model the model that the view takes in
   * @throws IllegalArgumentException if the model is null
   */
  public ImageViewImpl(ImageModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    } else {
      this.model = model;
    }
  }

  /**
   * Second constructor for the ImageViewImpl takes in a model and an appendable object.
   *
   * @param model the model for the view to take in
   * @param ap    the appendable object for the view to take in
   * @throws IllegalArgumentException if either the model or the appendable is null
   */
  public ImageViewImpl(ImageModel model, Appendable ap)
          throws IllegalArgumentException {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Model is null");
    } else {
      this.model = model;
      this.ap = ap;
    }
  }


  /**
   * Renders a message and displays it to users of the program.
   *
   * @param message the message to be displayed
   */
  @Override
  public void renderMessage(String message) {
    try {
      ap.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("cannot render message");
    }
  }
}
