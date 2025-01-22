import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import view.GUI;
import view.ImageView;
import view.ImageViewImpl;

/**
 * Represents what happens when run the project.Runs the program. If ran with no input displays the
 * Gui. If ran with -text command then run text program. If ran with -file command run every
 * command in given file path.
 */
public class RunProject {

  /**
   * Runs the program. If ran with no input displays the Gui. If ran with -text command then run
   * text program. If ran with -file command run every command in given file path.
   *
   * @param args the input taken in
   * @throws IOException if cannot complete operation
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      GUI frame = new GUI();
    } else if (args[0].equals("-file")) {
      ImageModel model = new ImageModelImpl();
      ImageView view = new ImageViewImpl(model, System.out);
      Readable in = new StringReader("file " + args[1]);
      ImageController controller =
              new ImageControllerImpl(model, view, in);
      controller.completeOperation();
    } else {
      ImageModel model = new ImageModelImpl();
      ImageView view = new ImageViewImpl(model, System.out);
      ImageController controller =
              new ImageControllerImpl(model, view, new InputStreamReader(System.in));
      controller.completeOperation();
    }
  }
}
