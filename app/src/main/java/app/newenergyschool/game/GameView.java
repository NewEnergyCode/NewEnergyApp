package app.newenergyschool.game;

//import static android.support.v4.media.session.MediaControllerCompatApi21.TransportControls.pause;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import app.newenergyschool.R;
import app.newenergyschool.ativity.MainActivity;

import java.util.Random;


public class GameView extends View {

    Context context;
    float ballX, ballY;
    Velocity velocity = new Velocity(5, 9);
    Handler handler;
    final long UPDATE_MILLIS = 1;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    Paint brickPaint = new Paint();
    float TEXT_SIZE = 120;
    float paddleX, paddleY;
    float oldX, oldPaddleX;
    int points = 0;
    int life = 3;
    Bitmap ball, paddle;
    int dWidth, dHeight;
    int ballWidth, ballHeight;
    MediaPlayer ping, destroy, miss;
    Random random;
    final int NEW_BRICKS_PER_ROW = 8;
    final int NUMBERS_OF_COLUMN = 5;
    Brick[] bricks = new Brick[NEW_BRICKS_PER_ROW * NUMBERS_OF_COLUMN];
    boolean gameOver = false;
    int numBricks = 0;
    int brokenBricks = 0;

    public GameView(Context context) {
        super(context);
        this.context = context;
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball_blue);
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.pngegg);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        ping = MediaPlayer.create(context, R.raw.ping);
        destroy = MediaPlayer.create(context, R.raw.destroy);
        miss = MediaPlayer.create(context, R.raw.miss);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        healthPaint.setColor(Color.GREEN);
        brickPaint.setColor(Color.rgb(0, 188, 212));
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        random = new Random();
        ballX = random.nextInt(dWidth - 50);
        ballY = dHeight / 3;
        paddleY = (dHeight * 4) / 5;
        paddleX = dWidth / 2 - paddle.getWidth() / 2;
        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();
//        createBricks();
        drawGridOfBricks();
    }

    public int findBrickSize(int num) {
        return (dWidth - ((num + 1) * 10)) / num;
    }

    public void createBricks() {
        int brickWidth = findBrickSize(NEW_BRICKS_PER_ROW);
        int brickHeight = brickWidth / 2;

        for (int column = 0; column < NEW_BRICKS_PER_ROW; column++) {
            for (int row = 0; row < NUMBERS_OF_COLUMN; row++) {
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight, new Color());
                numBricks++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ballX += velocity.getX();
        ballY += velocity.getY();

        sideObstacles();

        if (ballY <= 0) {
            velocity.setY(velocity.getY() * -1);
        }

        ifBallIsDrop();
        touchPaddle();

//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(ball, 100, 100, false);
        Bitmap pd = Bitmap.createScaledBitmap(paddle, 400, 150, false);
        canvas.drawBitmap(ball, ballX, ballY, null);
        canvas.drawBitmap(pd, paddleX, paddleY, null);

        drawRectangleUseCanvas(canvas);
        drawTextUseCanvas(canvas);
        canvas.drawRect(dWidth - 200, 30, dWidth - 200 + 60 * life, 80, healthPaint);
        checkCollision();
        if (brokenBricks == numBricks) {
            gameOver = true;
        }
        if (!gameOver) {
            handler.postDelayed(runnable, UPDATE_MILLIS);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= paddleY) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
                oldPaddleX = paddleX;
            }
            if (action == MotionEvent.ACTION_MOVE) {
                float shift = oldX - touchX;
                float newPaddleX = oldPaddleX - shift;
                if (newPaddleX <= 0) {
                    paddleX = 0;
                } else if (newPaddleX >= dWidth - paddle.getWidth()) {
                    paddleX = dWidth - paddle.getWidth();
                } else {
                    paddleX = newPaddleX;
                }
            }
        }
        return true;
    }


    private void launchGameOver() {
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(context, GameView.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    private int xVelocity() {
        int[] values = {-8, -6, -4, 4, 6, 8};
        int index = random.nextInt(6);
        return values[index];
    }

    public void sideObstacles() {
        if ((ballX >= dWidth - ball.getWidth()) || ballX <= 0) {
            velocity.setX(velocity.getX() * -1);
        }
        if (ballX < 0) {
            ballX = 1;
        } else if (ballX + ballWidth >= dWidth) {
            ballX = dWidth - ballWidth - 1;
        }

    }

    public void ifBallIsDrop() {
        if (ballY > paddleY + paddle.getHeight()) {
            ballX = 1 + random.nextInt(dWidth - ball.getWidth() - 1);
            ballY = dHeight / 3;
            if (miss != null) {
                miss.start();
            }
            velocity.setX(xVelocity());
            velocity.setY(xVelocity());
            life--;
            if (life == 0) {
                gameOver = true;
                launchGameOver();
            }
        }
    }

    public void touchPaddle() {
        if (((ballX + ball.getWidth()) >= paddleX)
                && (ballX <= paddleX + paddle.getWidth())
                && (ballY + ball.getHeight() >= paddleY)
                && (ballY + ball.getHeight() <= paddleY + paddle.getHeight())) {
            if (ping != null) {
                ping.start();
            }
            velocity.setX(velocity.getX() + 1);
            velocity.setY((velocity.getY() + 1) * -1);
            if (ballY > paddleY + paddle.getHeight()) {
                ballY = paddleY - paddleY / 2;
            }
        }
    }

    public void createRectangle(int x1, int y1, int x2, int y2, Color color) {
        bricks[numBricks] = new Brick(x1, y1, x2, y2, color);
        numBricks++;
    }

    public void drawRowOfBricks(int brickWight, int brickHeight, int y, Color color) {
        int stepXPlus = 10;
        for (int i = 0; i < NEW_BRICKS_PER_ROW; i++) {
            createRectangle(stepXPlus, y, stepXPlus + brickWight, y - brickHeight, color);
            stepXPlus = stepXPlus + brickWight + 10;
        }
    }

    public void drawGridOfBricks() {
        int nB = NUMBERS_OF_COLUMN;
        int brickWidth = findBrickSize(NEW_BRICKS_PER_ROW);
        int brickHeight = (int) (brickWidth * 0.75);
        Color[] colors = {Color.valueOf(Color.RED), Color.valueOf(Color.BLUE), Color.valueOf(Color.YELLOW), Color.valueOf(Color.GREEN), Color.valueOf(Color.CYAN)}; // array of colors
        int stepYMinus = brickHeight * 2;
        int counter = 0;
        int colorCounter = colors.length - 1;
        Color color = colors[colorCounter];
        while (nB != 0) {
            if (counter % 2 == 0) {
                color = colors[colorCounter];
                colorCounter--;
            }
            counter++;
            drawRowOfBricks(brickWidth, brickHeight, stepYMinus, color);
            stepYMinus = stepYMinus + brickHeight + 10;
            nB--;
            if (colorCounter < 0) {
                colorCounter = colors.length - 1;
            }
        }
    }

    public int[] findBallRadians() {
        int ballRadius = ballWidth / 2; // Радиус мяча
        int numPoints = 12; // Количество крайних точек
        int[] ballRadians = new int[numPoints * 2];
        int angleStep = 360 / numPoints;
        int count = 0;
        for (int angle = 0; angle < 360; angle += angleStep) {
            // Вычисляем горизонтальную координату точки на границе окружности
            int x = (int) (ballX + ballRadius * Math.cos(Math.toRadians(angle)));
            ballRadians[count] = x + ballRadius;
            count++;
            // Вычисляем вертикальную координату точки на границе окружности
            int y = (int) (ballY + ballRadius * Math.sin(Math.toRadians(angle)));
            ballRadians[count] = y + ballRadius;
            count++;
        }
        return ballRadians;
    }

    public void checkCollision() {
        int[] ballRadians = findBallRadians();

        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].isVisible()) {
                int x1 = bricks[i].row;
                int y1 = bricks[i].column;
                int x2 = bricks[i].width;
                int y2 = bricks[i].height;
//                if (ballX + ballWidth >= x1
//                        && ballY - ballWidth <= y2
//                        && ballX + ballWidth <= x2
//                        && ballY - ballWidth <= y2) {
//                    if (destroy != null) {
//                        destroy.start();
//                    }
//                    velocity.setY((velocity.getY() + 1) * (-1));
//                    bricks[i].setVisible();
//                    points += 10;
//                    brokenBricks++;
//                    if (brokenBricks == NEW_BRICKS_PER_ROW * NUMBERS_OF_COLUMN) {
////                        launchGameOver();
//                    }
//                }
                for (int s = 0; s < ballRadians.length; s += 2) {
                    int xx = ballRadians[s];
                    int yy = ballRadians[s + 1];
                    if (xx >= x1
                            && yy <= y1
                            && xx <= x2
                            && yy >= y2) {
                        velocity.setY((velocity.getY() + 1) * (-1));
                        if (destroy != null) {
                            destroy.start();
                        }
                        velocity.setX((int) randomGeneration(velocity.getX()));
                        bricks[i].setVisible();
                        points += 10;
                        brokenBricks++;
                        if (brokenBricks == NEW_BRICKS_PER_ROW * NUMBERS_OF_COLUMN) {
                        }
                        break;
                    }
                }
            }
        }
    }


    public double randomGeneration(double num) {
        Random random1 = new Random();
        if (random1.nextBoolean()) {
            num = -num;
        }
        return num;
    }

    public void drawRectangleUseCanvas(Canvas canvas) {
        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].isVisible()) {
                int x1 = bricks[i].row;
                int y1 = bricks[i].column;
                int x2 = bricks[i].width;
                int y2 = bricks[i].height;
                canvas.drawRect(x1, y1, x2, y2, brickPaint);
            }
        }
    }

    private void drawTextUseCanvas(Canvas canvas) {
        canvas.drawText("" + points, 20, TEXT_SIZE, textPaint);
        if (life == 2) {
            healthPaint.setColor(Color.YELLOW);
        } else if (life == 1) {
            healthPaint.setColor(Color.RED);
        }
    }
}
