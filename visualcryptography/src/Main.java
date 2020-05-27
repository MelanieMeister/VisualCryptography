import com.sun.glass.ui.Size;
import javafx.application.Application;
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

public class Main extends Application {

    private static final Size IMAGE_SIZE = new Size(437, 106);
    private static BufferedImage[] m_EncryptedImages;


    public static void main(String[] args) {

        Application.launch(args);

    }

    private static BufferedImage[] generate(String inputText) {
        if (inputText == null || Objects.equals(inputText, "")) return null;

        if (m_EncryptedImages != null)
        {
            for (int i = m_EncryptedImages.length - 1; i > 0; i--)
            {
                m_EncryptedImages[i].getGraphics().dispose();
            }
            Arrays.fill(m_EncryptedImages, null);
        }

        return generateImage(inputText);
    }

    private static BufferedImage[] generateImage(String inputText) {
        BufferedImage finalImage = new BufferedImage(IMAGE_SIZE.width, IMAGE_SIZE.height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage tempImage = new BufferedImage(IMAGE_SIZE.width / 2, IMAGE_SIZE.height, BufferedImage.TYPE_INT_ARGB);
        int GENERATE_IMAGE_COUNT = 2;
        BufferedImage[] image = new BufferedImage[GENERATE_IMAGE_COUNT];

        Random rand = new Random();
        Graphics2D g = finalImage.createGraphics ();
        Graphics2D gtemp = tempImage.createGraphics ();

        g.setColor(Color.black);
        Font font = new Font("Times New Roman", Font.BOLD, 50);
        g.setFont(font);
        TextLayout textLayout = new TextLayout(inputText, g.getFont(),
                g.getFontRenderContext());
        double textHeight = textLayout.getBounds().getHeight();
        double textWidth = textLayout.getBounds().getWidth();

        g.drawString(inputText, IMAGE_SIZE.width / 2 - (int) textWidth / 2, IMAGE_SIZE.height / 2 + (int) textHeight / 2);
        gtemp.drawImage(finalImage,0, 0, tempImage.getWidth(), tempImage.getHeight(), null);

        gtemp.drawImage(finalImage,0, 0, tempImage.getWidth(), tempImage.getHeight(), null);

        for (int i = 0; i < image.length; i++)
        {
            image[i] = new BufferedImage(IMAGE_SIZE.width, IMAGE_SIZE.height, BufferedImage.TYPE_INT_ARGB);
        }

        int index;
        int width = tempImage.getWidth();
        int height = tempImage.getHeight();

        for (int x = 0; x < width; x += 1)
        {
            for (int y = 0; y < height; y += 1)
            {
                int pixelColor = tempImage.getRGB(x, y);
                index = rand.nextInt(image.length);
                if (pixelColor == 0)
                {
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

    private static Image convertToFxImage(BufferedImage image) {
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

    @Override public void start(Stage stage) {
        BufferedImage[] bufferedImages = generate("Test");
/*


        try {
            // retrieve image
            File outpudtfile = new File("saved1.png");
            ImageIO.write(bufferedImages[0], "png", outpudtfile);

            File outpudtfile2 = new File("saved2.png");
            ImageIO.write(bufferedImages[1], "png", outpudtfile2);
        } catch (IOException e) {
            Exception d = e;
        }
*/

        ImageView iv1 = new ImageView();
        iv1.setImage(convertToFxImage(bufferedImages[0]));
        ImageView iv2 = new ImageView();
        iv2.setImage(convertToFxImage(bufferedImages[1]));
        Group root = new Group();
        Scene scene = new Scene(root);
        HBox box = new HBox();
        box.getChildren().add(iv1);
        box.getChildren().add(iv2);
        root.getChildren().add(box);
        stage.setTitle("ImageView");
        stage.setWidth(415);
        stage.setHeight(400);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }


}
