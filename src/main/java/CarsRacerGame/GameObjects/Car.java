package CarsRacerGame.GameObjects;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



public class Car {
    private double Speed = 200;
    private double x;
    private static final double y = 400;
    private boolean isLeftKeyPressed = false;
    private boolean isRightKeyPressed = false;
    private Canvas canvas;
    private Image image = new Image(this.getClass().getResourceAsStream("/car_blue.png"));

    public Car(double x, Canvas canvas) {
        this.x = x;
        this.canvas = canvas;
    }

    public void update(double deltaTimeInSec) {
        double distanceToMove = Speed * deltaTimeInSec;
        if (isLeftKeyPressed && x > 0) {
            x = x -distanceToMove;
        }
        else if (isRightKeyPressed && x < 800) {
            x = x + distanceToMove;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }
}