package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.ImageModel;
import model.ImageUtil;
import model.Pixel;
import view.ImageView;

/**
 * Represents our controller for the Image Processor. Allows the user to interact with the program.
 */
public class ImageControllerImpl implements ImageController {
  private final ImageModel model;
  private final ImageView view;
  private final Readable read;

  /**
   * Constructs the controller by taking in a model, view, and a readable object.
   *
   * @param model the model to operate on
   * @param view  the view which contains visual messages to display for the user
   * @param read  the readable object to take in user input
   */
  public ImageControllerImpl(ImageModel model, ImageView view, Readable read) {
    if (model == null || view == null || read == null) {
      throw new IllegalArgumentException("null");
    } else {
      //image model
      this.model = model;
      //view
      this.view = view;
      this.read = read;
    }
  }

  /**
   * Saves a file to a specified location on the user's device.
   *
   * @param fileName what the file will be called when it is saved
   * @param nameKey  the key within the hashmap to identify the file
   * @throws IOException if the user tries to save a file that hasn't been loaded or does not exist
   */
  public void saveImage(String fileName, String nameKey) throws IOException {
    //format fileName to get rid of extra period if enter absolute path
    System.out.println(fileName + "fileName");
    if (fileName.contains(".")) {
      ImageUtil.writeFile(model.getTemp().get(nameKey), fileName);
      view.renderMessage("Save successful! Other\n");
    } else {
      ImageUtil.writePPM(model.getTemp().get(nameKey), fileName);
      view.renderMessage("Save successful! PPM\n");
    }

  }

  /**
   * add color Grid based on fileName and String name to loadImage.
   */
  public void loadImage(String filePath, String name) {
    Pixel[][] newPixelGrid;
    if (!filePath.contains(".ppm")) {
      try {
        newPixelGrid = ImageUtil.readOther(filePath);
      } catch (IOException e) {
        view.renderMessage("can't read file\n");
        return;
      }
    } else {
      newPixelGrid = ImageUtil.readPPM(filePath);
    }

    model.getTemp().put(name, newPixelGrid);
    view.renderMessage("Load Successful!\n");
  }

  /**
   * Allows the user to interact with the program and run any commands that they wish.
   *
   * @throws IllegalArgumentException if there are no inputs entered
   * @throws IOException              if any inputs are invalid
   */
  @Override
  public void completeOperation() throws IllegalArgumentException, IOException {
    //read
    boolean quit = false;
    view.renderMessage("Welcome!\n");
    boolean readFile = false;
    Scanner scan = new Scanner(read);


    String next;
    while (!quit) {

      //if the scanner does not have a next line and we are reading from a file
      //set scan to read input normally
      if (readFile && !scan.hasNextLine()) {
        scan = new Scanner(read);
      }
      List<String> myArray = new ArrayList<String>();      //enter command
      view.renderMessage("Please enter a command\n" + "Enter q or quit to quit \n");
      try {
        //take in entire line
        next = scan.nextLine();
        //separate by space
        myArray = Arrays.asList(next.split(" "));
        //if there is q or quit quit quit
        for (int i = 0; i < myArray.size(); i++) {
          if (myArray.get(i).equalsIgnoreCase("q")
                  || myArray.get(i).equalsIgnoreCase("quit")) {
            quit = true;
            return;
          }
        }
      } catch (IllegalArgumentException e) {
        view.renderMessage("No more inputs\n");
      }
      //based on first word
      String operation = myArray.get(0).toLowerCase();
      switch (operation) {
        case "brighten":
        case "darken":
          modifyImageLight(myArray, operation);
          break;
        case "horizontal-flip":
        case "vertical-flip":
          modifyImageComponent(myArray, operation, false);
          break;
        case "red-component":
        case "blue-component":
        case "green-component":
        case "luma-component":
        case "intensity-component":
        case "value-component":
          modifyImageComponent(myArray, operation, true);
          break;
        case "blur":
        case "sharpen":
          modifyImageFilter(myArray, operation);
          break;
        case "file":
          //if file is entered set read file to true
          readFile = true;
          //turn file into br
          //f is the file path
          File f = new File(myArray.get(1));
          try {
            //set scanner to take in file
            scan = new Scanner(f);
          } catch (IOException e) {
            view.renderMessage("Not a valid file path");
          }
          break;
        case "grayscale":
        case "sepia":
          modifyImageColor(myArray, operation);
          break;
        //modifyImageSepia(myArray, operation);
        case "load":
          //if they say load change the model we are working on to
          //load inputted filePath then new Name
          try {
            loadImage(myArray.get(1), myArray.get(2));
          } catch (ArrayIndexOutOfBoundsException e) {
            view.renderMessage("Please enter a valid name\n");
          }

          break;
        case "save":
          try {
            saveImage(myArray.get(1), myArray.get(myArray.size() - 1));
          } catch (NullPointerException e) {
            view.renderMessage("Please enter a valid file to save\n");
          }
          break;
        default:

          view.renderMessage("Please enter a valid command\n");
          break;
      }

    }
  }

  /**
   * Method that either modifies a component of the image or flips the image on its x-axis or
   * y-axis.
   *
   * @param input an array of strings representing the line of user input entered through a keyboard
   * @param op    a string representing the operation to be performed
   * @param bool  a boolean value representing whether the image should have a component modified or
   *              if it should be flipped
   */
  private void modifyImageComponent(List<String> input, String op, boolean bool) {
    List<String> inputList = input.subList(1, input.size());
    String nickName = inputList.get(inputList.size() - 1);
    String picName = inputList.get(0);
    //this is initialized only so we can manipulate image Model
    //this manipulates the colorModel
    if (bool) {
      try {
        model.abstractComponent(nickName, op, picName);
        view.renderMessage("Operation Successful!\n");
      } catch (NullPointerException n) {
        view.renderMessage("Cannot find specified file identifier\n");
      }
    } else {
      try {
        model.abstractFlip(nickName, op, picName);
        view.renderMessage("Operation Successful!\n");
      } catch (NullPointerException n) {
        view.renderMessage("Cannot find specified file identifier\n");
      }
    }
    //get the manipulated colorGrid

  }

  /**
   * Modifies the brightness of the image by either increasing or decreasing it.
   *
   * @param input an array of strings representing the line of user input entered through a keyboard
   * @param op    a string representing the operation to be performed (either brighten or darken)
   */
  private void modifyImageLight(List<String> input, String op) {
    List<String> inputList = input.subList(1, input.size());
    try {
      int value = Integer.parseInt(inputList.get(0));
      String nickName = inputList.get(inputList.size() - 1);
      String picName = inputList.get(inputList.size() - 2);
      //this is initialized only so we can manipulate image Model
      //this manipulates the colorModel
      try {
        model.abstractLight(value, nickName, op, picName);
        view.renderMessage("Operation Successful!\n");
      } catch (NullPointerException n) {
        view.renderMessage("Cannot find specified file identifier\n");
      }
    } catch (NumberFormatException e) {
      view.renderMessage("Please enter a valid number to brighten/darken the image by\n");
    }

  }

  /**
   * Modifies the image based on a filter that is applied.
   *
   * @param input an array of strings representing the line of user input entered through a keyboard
   * @param op    a string representing the operation to be performed
   */
  private void modifyImageFilter(List<String> input, String op) {
    List<String> inputList = input.subList(1, input.size());
    String nickName = inputList.get(inputList.size() - 1);
    String picName = inputList.get(0);
    //this is initialized only so we can manipulate image Model
    //this manipulates the colorModel
    try {
      model.abstractFilter(nickName, op, picName);
      view.renderMessage("Operation Successful!\n");
    } catch (NullPointerException n) {
      view.renderMessage("Cannot find specified file identifier\n");
    }
  }

  /**
   * Modifies the image based on a filter that is applied.
   *
   * @param input an array of strings representing the line of user input entered through a keyboard
   * @param op    a string representing the operation to be performed
   */
  private void modifyImageColor(List<String> input, String op) {
    List<String> inputList = input.subList(1, input.size());
    String nickName = inputList.get(inputList.size() - 1);
    String picName = inputList.get(0);
    try {
      model.abstractColor(nickName, op, picName);
      view.renderMessage("Operation Successful!\n");
    } catch (NullPointerException n) {
      view.renderMessage("Cannot find specified file identifier\n");
    }
  }


}