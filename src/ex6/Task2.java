package ex6;

import java.awt.image.BufferedImage;
import java.util.concurrent.FutureTask;

import static ex6.ImageUtils.save;

public class Task2 {
    public static void main(String[] args) {
        // A few resolutions to generate images for
        Resolution[] resolutions = {
                new Resolution(800, 600),
                new Resolution(1280, 720),
                new Resolution(1920, 1080),
                new Resolution(2560, 1440),
                new Resolution(3840, 2160),
                new Resolution(5120, 2880)
        };

        FutureTask<BufferedImage>[] tasks = new FutureTask[resolutions.length];

        // generate the tasks
        for (int i = 0; i < resolutions.length; i++) {
            tasks[i] = generateImage(resolutions[i]);
        }

        // start the tasks
        for (FutureTask<BufferedImage> task : tasks) {
            new Thread(task).start();
        }

        // save the images
        for (int i = 0; i < resolutions.length; i++) {
            try {
                save(tasks[i].get(), "./out/output" + i + ".bmp");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static FutureTask<BufferedImage> generateImage(Resolution resolution) {
        return new FutureTask<>(() -> {
            System.out.println("Generating image with resolution: " + resolution.getWidth() + "x" + resolution.getHeight());
            StopWatch stopWatch = new StopWatch();
            BufferedImage image = ImageUtils.makeRandomImage(resolution.getWidth(), resolution.getHeight());
            System.out.println("Image with resolution: " + resolution.getWidth() + "x" + resolution.getHeight() + " generated in " + stopWatch.time() + " ms");
            return image;
        });
    }
}

class Resolution {
    private int width;
    private int height;

    public Resolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
