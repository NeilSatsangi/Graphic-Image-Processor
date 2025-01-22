package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFileChooser;



import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import model.ImageUtil;
import model.Pixel;


/**
 * Represents a graphical user interface of an image Proccessor. Displays the image that is being
 * operated on, changes applied to the image, and color histograms of the image. Allows users to
 * load and save files into any directory on a computer.
 */
public class GUI extends JFrame implements ActionListener {
  private ImageModel model;
  private ImageController controller;
  private File f;
  private ImageView view;
  private JLabel picLabel;
  private JLabel histLabel;
  private JPanel mainPanel;



  /**
   * Constructor for the view.GUI. Sets up the layout for the gui by placing all buttons and panes
   * on the frame. Sets up all action command and action listeners
   */
  public GUI() {
    //creates and initializes our JFrame
    super();
    model = new ImageModelImpl();
    view = new ImageViewImpl(model, System.out);
    controller = new ImageControllerImpl(model, view, new StringReader(""));
    setTitle("Image Processor");
    setSize(700, 700);

    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new GridLayout(4, 1));
    add(mainPanel);


    //Buttons
    addButtons();


    //Image Panel
    picLabel = new JLabel(new ImageIcon());
    JScrollPane imageScrollPanel = new JScrollPane(picLabel);


    //Histogram Panel
    ImageIcon imgHist = new ImageIcon();
    histLabel = new JLabel(imgHist);
    JScrollPane histPanel = new JScrollPane(histLabel);


    mainPanel.add(imageScrollPanel);


    mainPanel.add(histPanel);

    //Quit Button
    JButton quit = new JButton("Quit");
    quit.setActionCommand("Quit");
    quit.addActionListener(this);
    mainPanel.add(quit);

    setVisible(true);
  }

  private void addButtons() {
    //buttons
    JPanel buttons = new JPanel(new GridLayout(8, 2));
    JButton load = new JButton("Load image");
    load.setActionCommand("load button");
    load.addActionListener(this);
    buttons.add(load);


    JButton save = new JButton("Save image");
    save.setActionCommand("save button");
    save.addActionListener(this);
    buttons.add(save);

    JButton redButton = new JButton("Red Component");
    redButton.setActionCommand("red-component");
    redButton.addActionListener(this);
    buttons.add(redButton);

    JButton greenButton = new JButton("Green Component");
    greenButton.setActionCommand("green-component");
    greenButton.addActionListener(this);
    buttons.add(greenButton);

    JButton blueButton = new JButton("Blue Component");
    blueButton.setActionCommand("blue-component");
    blueButton.addActionListener(this);
    buttons.add(blueButton);

    JButton value = new JButton("Value Component");
    value.setActionCommand("value-component");
    value.addActionListener(this);
    buttons.add(value);

    JButton luma = new JButton("Luma Component");
    luma.setActionCommand("luma-component");
    luma.addActionListener(this);
    buttons.add(luma);

    JButton intensity = new JButton("Intensity Component");
    intensity.setActionCommand("intensity-component");
    intensity.addActionListener(this);
    buttons.add(intensity);

    JButton horizontal = new JButton("Horizontal Flip");
    horizontal.setActionCommand("horizontal-flip");
    horizontal.addActionListener(this);
    buttons.add(horizontal);

    JButton vertical = new JButton("Vertical Flip");
    vertical.setActionCommand("vertical-flip");
    vertical.addActionListener(this);
    buttons.add(vertical);

    JButton brighten = new JButton("Brighten");
    brighten.setActionCommand("brighten");
    brighten.addActionListener(this);
    buttons.add(brighten);

    JButton darken = new JButton("Darken");
    darken.setActionCommand("darken");
    darken.addActionListener(this);
    buttons.add(darken);

    JButton sharpen = new JButton("Sharpen");
    sharpen.setActionCommand("sharpen");
    sharpen.addActionListener(this);
    buttons.add(sharpen);

    JButton blur = new JButton("Blur");
    blur.setActionCommand("blur");
    blur.addActionListener(this);
    buttons.add(blur);

    JButton sepia = new JButton("Sepia");
    sepia.setActionCommand("sepia");
    sepia.addActionListener(this);
    buttons.add(sepia);

    JButton grayscale = new JButton("Grayscale");
    grayscale.setActionCommand("grayscale");
    grayscale.addActionListener(this);
    buttons.add(grayscale);

    mainPanel.add(buttons);

  }


  /**
   * Does an action when specific actionEvent is triggered. This method changes the image based
   * on the specific Action Event inputted. iE if the redButton is clicked the red-component
   * grayscale would be applied to the image.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String event = e.getActionCommand();
    System.out.println(event);
    switch (event) {
      case "load button": {
        loadButton();
      }
      break;
      case "save button": {
        saveButton();
      }
      break;


      case "red-component":
      case "blue-component":
      case "green-component":
      case "luma-component":
      case "value-component":
      case "intensity-component":
      case "vertical-flip":
      case "horizontal-flip":
      case "sepia":
      case "grayscale":
      case "blur":
      case "sharpen":
        componentButton(event);
        break;
      case "brighten":
      case "darken":
        componentButton(event + " 20");
        break;
      case "Quit":
        this.dispose();
        break;
      default:
        System.out.println("default");
        break;
    }
  }

  private void loadButton() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images", "jpg", "gif", "ppm", "png", "bpm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(GUI.this);


    if (retvalue == JFileChooser.APPROVE_OPTION) {
      try {
        f = fchooser.getSelectedFile();
        String path = f.getAbsolutePath();
        BufferedImage myPicture;
        if (path.contains(".ppm")) {
          Pixel[][] grid = ImageUtil.readPPM(path);
          myPicture = ImageUtil.writeImage(grid);
        } else {
          myPicture = ImageIO.read(new File(path));
        }
        ImageIcon img = new ImageIcon(myPicture);
        picLabel.setIcon(img);
        controller.loadImage(path, "name");
        setHist(model.getTemp().get("name"));
        System.out.println("yay");
      } catch (IOException | NullPointerException p) {
        System.out.print("hi");
      }
    }
  }

  private void saveButton() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String path = f.getAbsolutePath();
      System.out.println(path);
      if (path.contains(".")) {
        int indexPeriod = path.indexOf('.');
        path = path.substring(0, indexPeriod - 1) + path.substring(indexPeriod + 1);
      }
      System.out.println(path);
      try {
        controller.saveImage(path, "name");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

  }

  private void componentButton(String event) {
    Readable in = new StringReader(event + " name name  \n  q");
    controller = new ImageControllerImpl(model, view, in);
    try {
      controller.completeOperation();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    try {
      BufferedImage newImage = ImageUtil.writeImage(model.getTemp().get("name"));
      ImageIcon newIcon = new ImageIcon(newImage);
      picLabel.setIcon(newIcon);
      setHist(model.getTemp().get("name"));
    } catch (IOException | NullPointerException ex) {
      view.renderMessage("no image loaded");
    }
  }

  private void setHist(Pixel[][] pix) {
    Histogram hist = new Histogram(pix);
    hist.setComponentHisto();
    HistogramView newHist = new HistogramView(hist);
    BufferedImage newImage = newHist.newPanel();
    ImageIcon histIcon = new ImageIcon(newImage);
    histLabel.setIcon(histIcon);
    hist.setToZero();

  }


}
