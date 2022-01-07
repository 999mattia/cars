package CarsRacerGame.Views;

import CarsRacerGame.GameObjects.Car;
import CarsRacerGame.GameObjects.Coin;
import CarsRacerGame.GameObjects.Obstacle;
import CarsRacerGame.common.BaseScene;
import CarsRacerGame.common.Navigator;
import CarsRacerGame.common.enums.SceneType;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameScene extends BaseScene {
    private Navigator navigator;
    private AnimationTimer animationTimer;
    private long lastTimeInNanoSec;
    private static Group root = new Group();
    private Canvas canvas = new Canvas(800, 600);
    private GraphicsContext gc;
    private Image background = new Image(this.getClass().getResourceAsStream("/RadiatorSpringsBackground.png"));
    private Car car = new Car(376, canvas);
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private List<Coin> coins = new ArrayList<Coin>();
    private int Score = 0;


    public GameScene(Navigator navigator) {
        super(root);

        this.navigator = navigator;

        gc = canvas.getGraphicsContext2D();

        root.getChildren().addAll(canvas);

        lastTimeInNanoSec = System.nanoTime();
    }

    private void setupScene() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTimeInNanoSec) {


                long deltaInNanoSec = currentTimeInNanoSec - lastTimeInNanoSec;
                double deltaInSec = deltaInNanoSec / 1000000000d; //oder: 1e9;

                lastTimeInNanoSec = currentTimeInNanoSec;

                update(deltaInSec);
                paint();
            }
        };
        animationTimer.start();

        this.setOnKeyPressed((e) -> onKeyPressed(e));
        this.setOnKeyReleased((e) -> onKeyReleased(e));
    }

    public void update(double deltaInSec) {
        car.update(deltaInSec);

        spawnObstacles();
        spawnCoins();

        for (Obstacle obstacle : obstacles) {
            obstacle.update(deltaInSec);
            if (obstacle.collidesWith(car)) {
                Score = 0;
                navigator.navigateTo(SceneType.START);
            }
        }

        for (Coin coin : coins) {
            coin.update(deltaInSec);
            if (coin.collidesWith(car)) {
                Score = Score + 5;
            }
        }
    }

    public void paint() {
        gc.drawImage(background, 0, 0);

        car.draw(gc);

        for (Obstacle obstacle : obstacles) {
            obstacle.draw(gc);
        }

        for (Coin coin : coins) {
            coin.draw(gc);
        }
    }

    public void spawnObstacles() {
        Random random = new Random();

        int randInt = random.nextInt(700)+1;
        int randY = random.nextInt(500)-500;
        double dRandY = Double.valueOf(randY);

        if (randInt < 10) {
            int randLane = random.nextInt(4)+1;
            if (randLane == 1) {
                obstacles.add(new Obstacle(223, dRandY, canvas));
            }
            else if (randLane == 2) {
                obstacles.add(new Obstacle(350, dRandY, canvas));
            }
            else if (randLane == 3) {
                obstacles.add(new Obstacle(450, dRandY, canvas));
            }
            else if (randLane == 4) {
                obstacles.add(new Obstacle(550, dRandY, canvas));
            }
        }
    }

    public void spawnCoins() {
        Random random = new Random();

        int randInt = random.nextInt(3000)+1;
        int randY = random.nextInt(500)-500;
        double dRandY = Double.valueOf(randY);

        if (randInt < 10) {
            int randLane = random.nextInt(4)+1;
            if (randLane == 1) {
                coins.add(new Coin(250, dRandY, canvas));
            }
            else if (randLane == 2) {
                coins.add(new Coin(350, dRandY, canvas));
            }
            else if (randLane == 3) {
                coins.add(new Coin(450, dRandY, canvas));
            }
            else if (randLane == 4) {
                coins.add(new Coin(550, dRandY, canvas));
            }
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT) {
            car.setLeftKeyPressed(true);

        }
        else if (keyEvent.getCode() == KeyCode.RIGHT) {
            car.setRightKeyPressed(true);
        }
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT) {
            car.setLeftKeyPressed(false);
        }
        else if (keyEvent.getCode() == KeyCode.RIGHT) {
            car.setRightKeyPressed(false);
        }
    }

    @Override
    public void onEnter() {
        setupScene();
    }

    @Override
    public void onExit() {
        animationTimer.stop();
    }
}
