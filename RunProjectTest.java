import java.io.IOException;
import java.io.InputStreamReader;

import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import view.ImageView;
import view.ImageViewImpl;


/**
 * run the text program here.
 */
public class RunProjectTest {

  /**
   * Main method to run the program.
   *
   * @param args command line inputs
   * @throws IOException if inputs are missing/invalid
   */
  public static void main(String[] args) throws IOException {
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl(model, System.out);
    ImageController controller =
            new ImageControllerImpl(model, view, new InputStreamReader(System.in));
    controller.completeOperation();
  }

}
