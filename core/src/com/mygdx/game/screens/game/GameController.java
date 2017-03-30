package com.mygdx.game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.configurations.DifficultyLevel;
import com.mygdx.game.configurations.GameConfig;
import com.mygdx.game.entities.Background;
import com.mygdx.game.entities.Obstacle;
import com.mygdx.game.entities.Player;

@Deprecated

public class GameController {

    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);

    private final AvoidObstacleGame game;
    private final AssetManager assetManager;

    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private Background background;
    private Pool<Obstacle> obstaclePool;
    private Sound hitSound;

    private float obstacleTimer;
    private int lives = GameConfig.LIVES_AT_START;
    private int score;

    private final float START_PLAYER_X = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_BOUNDS_DIMENSION) / 2;
    private final float START_PLAYER_Y = 1 - GameConfig.PLAYER_BOUNDS_DIMENSION / 2;

    private boolean isInterrupted;

    public GameController(AvoidObstacleGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        init();

    }

    private void init() {
        //create new player
        player = new Player();

        //position the player
        player.setPosition(START_PLAYER_X, START_PLAYER_Y);

//        create obstacle pool
        obstaclePool = Pools.get(Obstacle.class, 40); //(class, int)

        //create background
        background = new Background();
        background.setPosition(0, 0);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);

        hitSound = assetManager.get(AssetDescriptors.HIT_SOUND);
    }

    ;

    public void update(float delta) {
        if (isGameOver()) {
            log.debug("Game is Over!");
            return;
        }

        updatePlayer();
        updateObstacles(delta);
        if (isPlayerCollidingWithObstacle()) {
            lives--;

//      check if game is over, if not, it will restart.
            if (isGameOver()) {
                log.debug("Game is Over!");
                GameManager.INSTANCE.updateHighScore(score);

            } else {
                restart();
            }

        }
        updateScore();

    }

    private boolean isPlayerCollidingWithObstacle() {
        for (Obstacle obstacle : obstacles) {
            if (!obstacle.getOverlapped() && obstacle.isPlayerColliding(player)) {
                hitSound.play();
                return true; //check both conditions, if true then return true;
            }
        }
        return false; //default is false
    }

    private void updatePlayer() {
        float xSpeed = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        }

        player.setX(player.getX() + xSpeed);

        blockPlayerFromLeavingWorld();
    }

    private void blockPlayerFromLeavingWorld() {
//      or this
//      float playerX = MathUtils.clamp(player.getX(), player.getWidth()/2, GameConfig.WORLD_WIDTH - player.getWidth()/2);
        float playerX = player.getX();
        float playerHalfSize = GameConfig.PLAYER_BOUNDS_RADIUS;
        if (playerX < player.getWidth() / 2 - playerHalfSize) {
            playerX = player.getWidth() / 2 - playerHalfSize;
        } else if (playerX > GameConfig.WORLD_WIDTH - (player.getWidth())) {
            playerX = GameConfig.WORLD_WIDTH - (player.getWidth());
        }
        player.setPosition(playerX, player.getY()); //set player's position within the logic
    }


    private void updateObstacles(float delta) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }
        createObstacles(delta);
        removeUnwantedObstacles();
    }

    private void createObstacles(float delta) {
        obstacleTimer += delta;
        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            float obstacleHalfSize = GameConfig.OBSTACLE_BOUNDS_RADIUS;
            float minX = GameConfig.OBSTACLE_BOUNDS_RADIUS - obstacleHalfSize;
            float maxX = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_BOUNDS_RADIUS - obstacleHalfSize;
//            float obstacleX = MathUtils.random(0, GameConfig.WORLD_WIDTH);
            float obstacleX = MathUtils.random(minX, maxX);
            float obstacleY = GameConfig.WORLD_HEIGHT;

//            Obstacle obstacle = new Obstacle();
            Obstacle obstacle = obstaclePool.obtain(); //instead of creating new ones, we obtain from pool
            DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);
            obstacleTimer = 0f;
        }

    }

    private void removeUnwantedObstacles() {
        if (obstacles.size > 0) {  //Check array, if it's empty, first() might throw exception
            Obstacle first = obstacles.first(); //local field
            float removeObstaclesAtMinY = -GameConfig.OBSTACLE_BOUNDS_DIMENSION; //where in Y to remove obstacles
            if (first.getY() < removeObstaclesAtMinY) {
                obstacles.removeValue(first, true); //(value, identity)
                obstaclePool.free(first); //free first obstacles

            }
        }
    }

    private void updateScore() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getY() < player.getY() && !obstacle.getCollected()) {
                score++;
                obstacle.setCollected(); //flag
            }
        }
    }

    private void restart() {
        obstaclePool.freeAll(obstacles); //put objects in the pool
        obstacles.clear();               //remove objects in the pool
        player.setPosition(START_PLAYER_X, START_PLAYER_Y); //position player back into the middle
    }

    public boolean isGameOver() {
        return lives <= 0; //requires both operators
    }

    public int getLives() {
        return lives;
    }


    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public Background getBackground() {
        return background;
    }

}
