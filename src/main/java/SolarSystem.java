import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

public class SolarSystem extends Application {

    //Change this to resize the canvas!
    private static double CANVAS_WIDTH = 1024;
    private static double CANVAS_HEIGHT = 1024;
    private static double EARTH_SPEED = 0.3;
    private static double VENUS_SPEED = EARTH_SPEED * 1.62527814864263;
    private static double JUPITER_SPEED = EARTH_SPEED / 11.8592552026287;
    private static double ALIEN_SPEED = 0.5;
    private static double MOON_SPEED = EARTH_SPEED * 8;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Solar system");

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        final int width = gd.getDisplayMode().getWidth();
        final int height = gd.getDisplayMode().getHeight() - 70;

        Group root = new Group();
        Scene theScene = new Scene(root, width, height);

        Canvas canvas = new Canvas(width, height);
        theStage.setScene(theScene);
        root.getChildren().add(canvas);

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        CANVAS_HEIGHT = gc.getCanvas().getHeight();
        CANVAS_WIDTH = gc.getCanvas().getWidth();

        //prepare images
        final Image space = new Image("Solar/space.png", width, height, true, false);
        final Image sun = new Image("Solar/sun.png");
        final Image earth = new Image("Solar/Earth.png");
        final Image venus = new Image("Solar/Venus.png");
        final Image jupiter = new Image("Solar/Jupiter.png");
        final Image alien = new Image("Solar/ufo_0.png");
        final Image moon = new Image("Solar/moon.png");

        //drawn alien
        final AnimatedImage ufo = new AnimatedImage();
        Image[] imageArray = new Image[6];
        for (int i = 0; i < 6; i++)
            imageArray[i] = new Image("Solar/ufo_" + i + ".png");
        ufo.frames = imageArray;
        ufo.duration = 0.100;

        //center of canvas
        final double xCenter = CANVAS_WIDTH / 2;
        final double yCenter = CANVAS_HEIGHT / 2;

        //earth's orbit radius
        final double radius = ((CANVAS_HEIGHT + CANVAS_WIDTH) / 2) / 4;
        final double venusRadius = ((CANVAS_HEIGHT + CANVAS_WIDTH) / 2) / 8;  // /5.54074074074074;
        final double jupiterRadius = radius * 1.7;
        final double radiusAlien = ((CANVAS_HEIGHT + CANVAS_WIDTH) / 2) / 3;
        final double moonOrbitRadius = ((CANVAS_HEIGHT + CANVAS_WIDTH) / 2) / 15;

        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

                //background image clears canvas
                gc.drawImage(space, 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

                //draw sun in the center of canvas
                gc.drawImage(sun, xCenter - 64, yCenter - 64);

                //compute Earth position and draw it
                double xEarth = xCenter + radius * Math.cos(t * EARTH_SPEED);
                double yEarth = yCenter + radius * Math.sin(t * EARTH_SPEED);
                gc.drawImage(earth, xEarth - 45, yEarth - 45, 90, 90);

                //compute Venus position and draw it
                double xVenus = xCenter + venusRadius * Math.cos(t * VENUS_SPEED);
                double yVenus = yCenter + venusRadius * Math.sin(t * VENUS_SPEED);
                gc.drawImage(venus, xVenus- 40, yVenus - 40, 80, 80);

                //compute Jupiter position and draw it
                double xJupiter = xCenter + jupiterRadius * Math.cos(t * JUPITER_SPEED);
                double yJupiter = yCenter + jupiterRadius * Math.sin(t * JUPITER_SPEED);
                gc.drawImage(jupiter, xJupiter - 95, yJupiter - 75, 190, 150);

                //compute moon position (based on earth's) and draw it
                double xMoon = xEarth + moonOrbitRadius * Math.cos(t * MOON_SPEED);
                double yMoon = yEarth + moonOrbitRadius * Math.sin(t * MOON_SPEED);
                gc.drawImage(moon, xMoon - 15, yMoon - 15, 30, 30);


                //compute alien position and draw it
                double xSecondPlanet = xCenter - radiusAlien * Math.cos(t * ALIEN_SPEED);
                double ySecondPlanet = yCenter + radiusAlien * Math.sin(t * MOON_SPEED / 7);
                gc.drawImage(alien, xSecondPlanet - 15, ySecondPlanet - 15, 30, 30);

                gc.drawImage(ufo.getFrame(t), CANVAS_WIDTH - 75, 25);
            }
        }.start();

        theStage.show();

    }
}