package model;
import com.sun.glass.ui.Size;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class PresentationModel {
    private static BufferedImage[] encryptedImages;
    private StringProperty plainText = new SimpleStringProperty();
    private DoubleProperty screenWidth = new SimpleDoubleProperty(750);

    public  BufferedImage[] generate(Size size) {
        if (getPlainText() == null || Objects.equals(getPlainText(), "")) return null;

        if (encryptedImages != null)
        {
            for (int i = encryptedImages.length - 1; i > 0; i--)
            {
                encryptedImages[i].getGraphics().dispose();
            }
            Arrays.fill(encryptedImages, null);
        }

        return generateImage(getPlainText(), size);
    }

    /**
     * Generates an image with a text and encrypts this image.
     * @param inputText text to put on the image to decrypt
     * @param size heigth and width of the image
     * @return array containing the layers to decrypt the image
     */
    private static BufferedImage[] generateImage(String inputText, Size size) {

        BufferedImage finalImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage tempImage = new BufferedImage(size.width / 2, size.height, BufferedImage.TYPE_INT_ARGB);
        // Amount of layers to be generated
        int GENERATE_IMAGE_COUNT = 2;
        // Array to save the layers
        BufferedImage[] image = new BufferedImage[GENERATE_IMAGE_COUNT];

        Random rand = new Random();
        Graphics2D g = finalImage.createGraphics ();
        Graphics2D gtemp = tempImage.createGraphics ();

        // Create the string to draw on the image
        g.setColor(Color.black);
        Font font = new Font("Times New Roman", Font.BOLD, 50);
        g.setFont(font);
        // Align text in the middle / center of the image
        TextLayout textLayout = new TextLayout(inputText, g.getFont(),
                g.getFontRenderContext());
        double textHeight = textLayout.getBounds().getHeight();
        double textWidth = textLayout.getBounds().getWidth();

        // Draw the image
        g.drawString(inputText, size.width / 2 - (int) textWidth / 2, size.height / 2 + (int) textHeight / 2);
        gtemp.drawImage(finalImage,0, 0, tempImage.getWidth(), tempImage.getHeight(), null);

        gtemp.drawImage(finalImage,0, 0, tempImage.getWidth(), tempImage.getHeight(), null);

        // Create layers
        for (int i = 0; i < image.length; i++)
        {
            image[i] = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        }

        int index;
        int width = tempImage.getWidth();
        int height = tempImage.getHeight();

        // Foreach pixel in temp image
        for (int x = 0; x < width; x += 1)
        {
            for (int y = 0; y < height; y += 1)
            {
                // Get current pixel color
                int pixelColor = tempImage.getRGB(x, y);
                // Generate random number
                index = rand.nextInt(image.length);
                // If color of current pixel is white / empty
                if (pixelColor == 0)
                {
                    // Set sub pixels for original white pixel
                    for (BufferedImage anImage : image) {
                        if (index == 0) {
                            anImage.setRGB(x * 2, y, Color.black.getRGB());
                            anImage.setRGB(x * 2 + 1, y, new Color(0, 0, 0, 0).getRGB());
                        } else {
                            anImage.setRGB(x * 2, y, new Color(0, 0, 0, 0).getRGB());
                            anImage.setRGB(x * 2 + 1, y, Color.black.getRGB());
                        }
                    }
                }
                else
                {
                    // Set sub pixels for original black pixel
                    for (int i = 0; i < image.length; i++)
                    {
                        if ((index + i) % image.length == 0)
                        {
                            image[i].setRGB(x * 2, y, Color.black.getRGB());
                            image[i].setRGB(x * 2 + 1, y, new Color(0, 0, 0, 0).getRGB());
                        }
                        else
                        {
                            image[i].setRGB(x * 2, y, new Color(0, 0, 0, 0).getRGB());
                            image[i].setRGB(x * 2 + 1, y, Color.black.getRGB());
                        }
                    }
                }
            }
        }
        tempImage.getGraphics().dispose();
        finalImage.getGraphics().dispose();

        return image;
    }

    /**
     * Converts the BufferedImage to a Image.
     * @param image buffed image to be converted to image
     * @return converted image
     */
    public static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public double getScreenWidth() {
        return screenWidth.get();
    }

    public DoubleProperty screenWidthProperty() {
        return screenWidth;
    }

    public void setScreenWidth(double screenWidth) {
        this.screenWidth.set(screenWidth);
    }

    public String getPlainText() {
        return plainText.get();
    }

    public StringProperty plainTextProperty() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText.set(plainText);
    }
}
