//package app.newenergyschool.game;
//
//import acm.graphics.GLabel;
//import acm.graphics.GObject;
//import acm.graphics.GOval;
//import acm.graphics.GRect;
//import acm.util.RandomGenerator;
//import com.shpp.cs.a.graphics.WindowProgram;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.util.ArrayList;
//
//public class Assignment4Part1 extends WindowProgram {
//    /**
//     * Width and height of application window in pixels
//     */
//    public static final int APPLICATION_WIDTH = 400;
//    public static final int APPLICATION_HEIGHT = 600;
//
//    /**
//     * Dimensions of the paddle
//     */
//    private static final int PADDLE_WIDTH = 60;
//    private static final int PADDLE_HEIGHT = 10;
//
//    /**
//     * Offset of the paddle up from the bottom
//     */
//    private static final int PADDLE_Y_OFFSET = 30;
//
//    /**
//     * Number of bricks per row
//     */
//    private static final int NBRICKS_PER_ROW = 10;
//
//    /**
//     * Number of rows of bricks
//     */
//    private static final int NBRICK_ROWS = 10;
//
//    /**
//     * Separation between bricks
//     */
//    private static final int BRICK_SEP = 4;
//
//    /**
//     * It's a bad idea to calculate brick width from APPLICATION_WIDTH
//     */
//    private static final int BRICK_WIDTH = 75;
////            (APPLICATION_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
//
//    /**
//     * Height of a brick
//     */
//    private static final int BRICK_HEIGHT = 8;
//
//    /**
//     * Radius of the ball in pixels
//     */
//    private static final int BALL_RADIUS = 10;
//
//    /**
//     * Offset of the top brick row from the top
//     */
//    private static final int BRICK_Y_OFFSET = 70;
//
//    /**
//     * Number of turns
//     */
//    private static final int NTURNS = 3;
//    /**
//     * Strength of the gravity
//     */
//    private static final double GRAVITY = 0.0025;
//    /**
//     * Speed of the ball
//     */
//    private final double BALL_SPEED = 0.5;
//    /**
//     * This variable controls the random bouncing behavior of the ball.
//     * When set to 'true', the ball will be randomly bounced off the racket.
//     * When set to 'false', the ball will follow a predefined bouncing pattern.
//     */
//    private final boolean RANDOM_GENERATOR = true;
//    /**
//     * Width of the racket
//     */
//    private int racketSize = BRICK_WIDTH;
//    /**
//     * The object racket
//     */
//    private GRect racket;
//    /**
//     * The object ball
//     */
//    private GOval ball;
//    /**
//     * This ArrayList stores the objects of bricks in the game
//     */
//    private final ArrayList<GRect> gRectsList = new ArrayList<>();
//    /**
//     * This variable is used to enable or disable the bonus speed-up feature, which increases the speed.
//     */
//    private boolean bonusSpeedUp = false;
//    /**
//     * This variable is used to enable or disable the bonus speed-down feature, which decrease the speed.
//     */
//    private boolean bonusSpeedDown = false;
//
//    public void run() {
//        playGame();
//    }
//
//    /**
//     * This method shows the user the text explaining what they must do for the program to start.
//     * Also, the method draws a grid of bricks, creates a racket, and adds mouse listeners. After that, the game will start.
//     */
//    public void playGame() {
//
//        addLabelResult("Press mouse to start");
//        drawGridOfBricks(NBRICK_ROWS);
//        racket = createRectangle(getWidth() / 2.0 - BRICK_WIDTH / 2.0, getHeight() - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLACK);
//        addMouseListeners();
//        startGame();
//    }
//
//    /**
//     * This method updates the position of the racket based on the mouse drag event.
//     * It ensures that the racket stays within the horizontal bounds of the game window.
//     */
//    @Override
//    public void mouseDragged(MouseEvent e) {
//
//        if (racket != null) {
//            // Update the X position of the racket
//            double newX = e.getX() - racket.getWidth() / 2.0;
//            double newY = getHeight() - PADDLE_Y_OFFSET;
//            // Restrict the X position to stay within the bounds of the game window
//            double minX = 0;
//            double maxX = getWidth() - racket.getWidth();
//            if (newX < minX) {
//                newX = minX;
//            } else if (newX > maxX) {
//                newX = maxX;
//            }
//            // Set the new location of the racket
//            racket.setLocation(newX, newY);
//        }
//    }
//
//    /**
//     * Creates a rectangle (brick) at the given coordinates and adds it to the canvas.
//     */
//    public GRect createRectangle(double x, double y, int size, int brickHeight, Color color) {
//        GRect gRect = new GRect(x, y, size, brickHeight);
//        gRectsList.add(gRect);
//        gRect.setFilled(true);
//        gRect.setColor(color);
//        gRect.setFillColor(color);
//        add(gRect);
//        return gRect;
//    }
//
//    /**
//     * Draws a row of bricks with the specified width, y-coordinate, and color.
//     *
//     * @param numWight The number of bricks in the row.
//     * @param y        The y-coordinate of the row.
//     * @param color    The color of the bricks.
//     */
//    public void drawRowOfBricks(int numWight, double y, Color color) {
//        int brickWight = findBrickSize(NBRICKS_PER_ROW);
//        double x = findX(numWight, brickWight);
//        double stepXPlus = x + BRICK_SEP;
//        double stepXMinus = x - BRICK_SEP;
//        for (int i = 0; i < numWight; i++) {
//            if (i == 0) {
//                createRectangle(x, y, brickWight, BRICK_HEIGHT, color);
//            } else if (i % 2 == 0) {
//                createRectangle(stepXPlus + brickWight, y, brickWight, BRICK_HEIGHT, color);
//                stepXPlus = stepXPlus + brickWight + BRICK_SEP;
//            } else {
//                createRectangle(stepXMinus - brickWight, y, brickWight, BRICK_HEIGHT, color);
//                stepXMinus = stepXMinus - brickWight - BRICK_SEP;
//            }
//            pause(5);
//        }
//    }
//
//    /**
//     * Finds the x-coordinate for placing the bricks based on the given number.
//     */
//    public double findX(int num, double brickWight) {
//        if (num % 2 == 0) {
//            return (getWidth()) / 2.0;
//        } else {
//            return getWidth() / 2.0 - brickWight / 2.0;
//        }
//    }
//
//    /**
//     * Calculates the size of each brick given the number of bricks in a row.
//     *
//     * @param num The number of bricks in a row.
//     * @return The size of each brick.
//     */
//    public int findBrickSize(int num) {
//        return (getWidth() - BRICK_SEP * NBRICKS_PER_ROW + 1) / num;
//    }
//
//    /**
//     * This method draw grid of the brick.
//     * Every two lines of the bricks have new color. If the number of color is exhausted, the program reuses the colors.
//     * The colors used are stored in an array.
//     *
//     * @param numWidth The numbers of lines in the grid.
//     */
//    public void drawGridOfBricks(int numWidth) {
//        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN}; // array of colors
//        double stepYMinus = BRICK_Y_OFFSET + BRICK_SEP;
//        int counter = 0;
//        int colorCounter = colors.length - 1;
//        Color color = colors[colorCounter];
//        while (numWidth != 0) {
//            if (counter % 2 == 0) {
//                color = colors[colorCounter];
//                colorCounter--;
//            }
//            counter++;
//            drawRowOfBricks(NBRICKS_PER_ROW, stepYMinus, color);
//            stepYMinus = stepYMinus + BRICK_HEIGHT + BRICK_SEP;
//            numWidth--;
//            if (colorCounter < 0) {
//                colorCounter = colors.length - 1;
//            }
//        }
//    }
//
//    /**
//     * This method creates a ball and adds it to the monitor.
//     *
//     * @return The crated Ball in GOlav object.
//     */
//    public GOval createBall() {
//        double x = getWidth() / 2.0;
//        double y = getHeight() / 2.0;
//        GOval gOval = new GOval(x, y, BALL_RADIUS, BALL_RADIUS);
//        gOval.setFilled(true);
//        gOval.setColor(Color.BLUE);
//        add(gOval);
//        return gOval;
//    }
//
//    /**
//     * In this method creates and runs the ball, starting the game.
//     * The constant NTURNS represents the number of lives. If lives are exhausted, the user is shown the "Game over" text.
//     * If the lives are not exhausted, but the ball is lost, the user is shown the text indication the remaining lives.
//     */
//    public void startGame() {
//        int life = NTURNS;
//        while (life >= 1) {
//            ball = createBall();
//            if (runBall(BALL_SPEED, randomGeneration(BALL_SPEED), ball)) {
//                addLabelResult("YOU WIN");
//                break;
//            } else {
//                life--;
//                if (life == 0) {
//                    addLabelResult("GAME OVER");
//                } else {
//                    addLabelResult("Oops, you have " + life + " lives left.");
//                }
//            }
//        }
//    }
//
//    /**
//     * This method adds text to the screen at the center.
//     *
//     * @param text The text to be added.
//     */
//    private void addLabelResult(String text) {
//        GLabel label = new GLabel(text);
//        label.setFont("Helvetica-24");
//        add(label, getWidth() / 2.0 - label.getWidth() / 2.0, getHeight() / 2.0);
//        waitForClick();
//        remove(label);
//    }
//
//    /**
//     * This method handles the movement of the ball.
//     * The cycle continues until all bricks are destroyed.
//     */
//    public boolean runBall(double vy, double vx, GOval ball) {
//        int count = 0;
//        int rectangleCont = gRectsList.size();
//        while (rectangleCont > 1) {
//            count++;
//            ball.move(vx, vy);
//            if (count % 20 == 0 && BALL_SPEED < BALL_SPEED * 3) { //The ball`s speed will be increased every 20 cicles
//                vy = speedUp(vy);
//                vx = speedUp(vx);
//                count = 0;
//            }
//            if (bonusSpeedUp && vy < BALL_SPEED * 3) { //The ball's speed will be increased when the user receives a bonus.
//                vy = speedUp(vy) * 1.2;
//                vx = speedUp(vx) * 1.2;
//                bonusSpeedUp = false;
//            }
//            if (bonusSpeedDown && vy > BALL_SPEED) { //The  ball`s speed will be reduced if user receives a bonus
//                vy = speedUp(vy) / 1.2;
//                vx = speedUp(vx) / 1.2;
//                bonusSpeedDown = false;
//            }
//            if (upperObstacle(ball)) { //The ball will change direction if it is in contact with the upper bound.
//                vy *= -1;
//                vx = changeDirectionBall(vx);
//            }
//            if (bottomObstacle(ball)) { //The ball will be removed and the game will stop (returning false) if the ball is in contact with the lower boundary.
//                remove(ball);
//                return false;
//            }
//            vx = sideObstacles(ball, vx); // The ball will change direction if it comes into contact with the side boundary.
//            if (obstacleRacket(ball)) { // The ball will change direction if it comes into contact with the rocket
//                vy *= -1;
//                ball.setLocation(ball.getX(), racket.getY() - ball.getHeight()); //This line of code is correct the ball position after contact with the racket
//                vx = changeDirectionBall(vx);
//            }
//            GObject collider = getCollidingObject(ball);
//            if (collider != null && collider != racket) { //This part of method checks if ball comes into contact with object expect racket.
//                if (gRectsList.contains(collider)) {
//                    remove(collider);
//                    gRectsList.remove(collider);
//                    rectangleCont--;
//                    vy *= -1;
//                    vx = changeDirectionBall(vx);
//                    if (randomGeneration(BALL_SPEED) > 0) {
//                        dropBonus(collider.getX(), collider.getY());
//                    }
//                }
//            }
//            pause(5);
//        }
//        return true;
//    }
//
//    /**
//     * This method change the direction of the ball.
//     *
//     * @param vc The current velocity component of the ball.
//     * @return The updated velocity component with the direction changed.
//     */
//    public double changeDirectionBall(double vc) {
//        if (RANDOM_GENERATOR) {
//            vc = randomGeneration(vc);
//        }
//        return vc;
//    }
//
//    /**
//     * This method creates a bonus object when a brick is broken and drops it at the specified position.
//     * The bonus object is represented by a small filled oval with random color.
//     * The bonus falls vertically downwards with a constant velocity.
//     *
//     * @param x The x-coordinate of the drop position.
//     * @param y The y-coordinate of the drop position.
//     */
//    public void dropBonus(double x, double y) {
//        double vy = 0;
//        double bonusSize = 10;
//        GOval gOval = new GOval(x, y, bonusSize, bonusSize);
//        gOval.setFilled(true);
//        if (randomGeneration(1.0) > 0 && randomGeneration(1.0) > 0) {
//            gOval.setColor(Color.RED);
//        } else if (randomGeneration(1.0) < 0 && randomGeneration(1.0) < 0) {
//            gOval.setColor(Color.GREEN);
//        } else if (randomGeneration(1.0) > 0 && randomGeneration(1.0) < 0) {
//            gOval.setColor(Color.magenta);
//        } else if (randomGeneration(1.0) < 0 && randomGeneration(1.0) > 0) {
//            gOval.setColor(Color.ORANGE);
//        }
//        add(gOval);
//        startTimer(vy, gOval);
//    }
//
//    /**
//     * This method starts dropping of the bonus.
//     *
//     * @param vy    The vertical velocity of the bonus object.
//     * @param gOval The bonus object to be dropped.
//     */
//    private void startTimer(double vy, GOval gOval) {
//        Timer timer = new Timer(40, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dropBonusStep(vy, gOval, (Timer) e.getSource());
//            }
//        });
//        timer.start();
//    }
//
//    /**
//     * This method handles the dropping of bonuses.
//     *
//     * @param vy     The vertical velocity of the bonus object.
//     * @param bonus  The bonus object being dropped.
//     * @param source The Timer object responsible for the animation.
//     */
//    private void dropBonusStep(double vy, GOval bonus, Timer source) {
//        vy += GRAVITY + BALL_SPEED * 3;
//        bonus.move(0, vy);
//        if (bonus.getY() >= getHeight()) {
//            remove(bonus);
//            source.stop();
//        }
//        if (obstacleRacket(bonus)) {
//            remove(bonus);
//            if (bonus.getColor().equals(Color.GREEN)) {
//                widthRacketUp();
//            } else if (bonus.getColor().equals(Color.RED)) {
//                widthRacketDown();
//            } else if (bonus.getColor().equals(Color.magenta)) {
//                bonusSpeedUp = true;
//            } else if (bonus.getColor().equals(Color.RED)) {
//                bonusSpeedDown = true;
//            }
//            source.stop();
//        }
//        if (ball.getBounds().intersects(bonus.getBounds())) {
//            remove(bonus);
//            source.stop();
//        }
//    }
//
//    /**
//     * This method returns object if the ball came contact into this object.
//     * It checks for collisions at the four corners of the ball's bounding box.
//     *
//     * @param ball The ball object to check for collisions.
//     * @return The object that the ball collides with, or null if there is no collision.
//     */
//    private GObject getCollidingObject(GOval ball) {
//        GObject obj = getElementAt(ball.getX(), ball.getY());
//        if (obj != null) {
//            return obj;
//        }
//        obj = getElementAt(ball.getX() + ball.getWidth(), ball.getY());
//        if (obj != null) {
//            return obj;
//        }
//        obj = getElementAt(ball.getX(), ball.getY() + ball.getHeight());
//        if (obj != null) {
//            return obj;
//        }
//        obj = getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
//        return obj;
//    }
//
//    /**
//     * This method upes the speed of the ball.
//     */
//    public double speedUp(double v) {
//        if (v > 0) {
//            return v + GRAVITY;
//        } else {
//            return v + (-GRAVITY);
//        }
//    }
//
//    /**
//     * This method upes the width of the racket.
//     */
//    public void widthRacketUp() {
//        if (racket.getWidth() < BRICK_WIDTH * 2) {
//            racket.setSize(racketSize + 10, racket.getHeight());
//            racketSize = (int) racket.getWidth();
//        }
//    }
//
//    /**
//     * This method downs the width of the racket.
//     */
//    public void widthRacketDown() {
//        if (racket.getWidth() > BRICK_WIDTH) {
//            racket.setSize(racketSize - 10, racket.getHeight());
//            racketSize = (int) racket.getWidth();
//        }
//    }
//
//    /**
//     * This method returns true if the ball crosses the bottom obstacle.
//     */
//    private boolean bottomObstacle(GOval ball) {
//        return ball.getY() > getHeight() - BRICK_HEIGHT;
//    }
//
//    /**
//     * This method returns true if the ball crosses the upper obstacle.
//     */
//    private boolean upperObstacle(GOval ball) {
//        return ball.getY() <= 0;
//    }
//
//    /**
//     * This method returns true if the ball comes contact into the racket.
//     */
//    private boolean obstacleRacket(GOval ball) {
//        return ball.getBounds().intersects(racket.getBounds());
//    }
//
//    /**
//     * This method returns reversed coordinate VX if the ball comes contact into the side obstacle.
//     */
//    public double sideObstacles(GOval ball, double vx) {
//        double vc = vx;
//        if (ball.getX() <= 0) {
//            vc *= -1;
//        } else if (ball.getX() + ball.getWidth() >= getWidth()) {
//            vc *= -1;
//        }
//        if (vx != vc) {
//            if (ball.getX() < BALL_RADIUS) {
//                ball.setLocation(0, ball.getY());
//            } else {
//                ball.setLocation(getWidth() - BALL_RADIUS, ball.getY());
//            }
//            return vc;
//        } else return vx;
//    }
//
//    /**
//     * This method generates a random double number within the specified limits.
//     *
//     * @param num The upper limit for the generated number.
//     * @return A random double number within the range [-num, num].
//     */
//    public double randomGeneration(Double num) {
//        RandomGenerator rGen = RandomGenerator.getInstance();
//        num = rGen.nextDouble(BALL_SPEED, BALL_SPEED * 2);
//        if (rGen.nextBoolean(0.5)) {
//            num = -num;
//        }
//        return num;
//    }
//
//}
