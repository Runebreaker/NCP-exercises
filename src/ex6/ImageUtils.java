package ex6;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageUtils {
    // CounterTesterAll
    public static void main(String[] args) {
        ImageUtils.usageExample(600, 300);
    }
    public static void usageExample(int width, int height) {
        BufferedImage image1 = makeRandomImage(width, height);
        BufferedImage image2 = makeRandomImage(width, height);
        BufferedImage image3 = merge(image1, image2, 0);
        save(image3, "./out/output2.bmp");
    }
    public static void save(BufferedImage image, String path) {
        try {
            File outputFile = new File(path);
            ImageIO.write(image, "bmp", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage makeRandomImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[][] red = new int[height][width];
        int[][] green = new int[height][width];
        int[][] blue = new int[height][width];
        var random = new Random();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                red[h][w] = random.nextInt(255);
                green[h][w] = random.nextInt(255);
                blue[h][w] = random.nextInt(255);
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = red[y][x];
                rgb = (rgb << 8) + green[y][x];
                rgb = (rgb << 8) + blue[y][x];
                image.setRGB(x, y, rgb);
            }
        }
        return image;
    }
    public static BufferedImage merge(BufferedImage img1, BufferedImage img2, int offset) {
        int width = img1.getWidth() + img2.getWidth() + offset;
        int height = Math.max(img1.getHeight(), img2.getHeight()); //+offset;
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
//        fill background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, width, height);
//        draw image
        g2.setColor(oldColor);
        g2.drawImage(img1, 0, 0, null);
        g2.drawImage(img2, img1.getWidth() + offset, 0, null);
        g2.dispose();
        return newImage;
    }
}
