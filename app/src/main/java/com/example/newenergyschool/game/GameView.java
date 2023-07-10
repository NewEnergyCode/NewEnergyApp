package com.example.newenergyschool.game;

import android.annotation.SuppressLint;
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

import com.example.newenergyschool.R;
import com.example.newenergyschool.ativity.MainActivity;

import java.util.Random;


public class GameView extends View {

    Context context;
    float ballX, ballY;
    Velocity velocity = new Velocity(25, 32);
    Handler handler;
    final long UPDATE_MILLIS = 30;
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
    Brick[] bricks = new Brick[30];
    final int NEW_BRICKS_PER_ROW = 10;
    final int NUMBERS_OF_COLUMN = 3;
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
        createBricks();
    }

    public int findBrickSize(int num) {
        return (dWidth - num) / num;
    }

    public void createBricks() {
        int brickWidth = findBrickSize(NEW_BRICKS_PER_ROW);
        int brickHeight = brickWidth / 2;

        for (int column = 0; column < NEW_BRICKS_PER_ROW; column++) {
            for (int row = 0; row < NUMBERS_OF_COLUMN; row++) {
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ballX += velocity.getX();
        ballY += velocity.getY();
        if ((ballX >= dWidth - ball.getWidth()) || ballX <= 0) {
            velocity.setX(velocity.getX() * -1);
        }
        if (ballY <= 0) {
            velocity.setY(velocity.getY() * -1);
        }
        if (ballY > paddleY + paddle.getHeight()) {
            ballX = 1 + random.nextInt(dWidth - ball.getWidth() - 1);
            ballY = dHeight / 3;
            if (miss != null) {
                miss.start();
            }
            velocity.setX(xVelocity());
            velocity.setY(32);
            life--;
            if (life == 0) {
                gameOver = true;
//                launchGameOver();
            }
        }
        if (((ballX + ball.getWidth()) >= paddleX)
                && (ballX <= paddleX + paddle.getWidth())
                && (ballY + ball.getHeight() >= paddleY)
                && (ballY + ball.getHeight() <= paddleY + paddle.getHeight())) {
            if (ping != null) {
                ping.start();
            }
            velocity.setX(velocity.getX() + 1);
            velocity.setY((velocity.getY() + 1) * -1);
        }
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(ball, 100, 100, false);
        Bitmap pd = Bitmap.createScaledBitmap(paddle, 400, 150, false);
        canvas.drawBitmap(ball, ballX, ballY, null);
        canvas.drawBitmap(pd, paddleX, paddleY, null);
        for (int i = 0; i < numBricks; i++) {
            System.out.println("bricks[i].column: "+bricks[i].column);
            if (bricks[i].isVisible()) {
                canvas.drawRect(bricks[i].column * bricks[i].width + 1,
                        bricks[i].row * bricks[i].height + 1,
                        bricks[i].column * bricks[i].width + bricks[i].width - 1,
                        bricks[i].row * bricks[i].height + bricks[i].height - 1, brickPaint);
            }
        }
        canvas.drawText("" + points, 20, TEXT_SIZE, textPaint);
        if (life == 2) {
            healthPaint.setColor(Color.YELLOW);
        } else if (life == 1) {
            healthPaint.setColor(Color.RED);
        }
        canvas.drawRect(dWidth - 200, 30, dWidth - 200 + 60 * life, 80, healthPaint);
        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].isVisible()) {
                if (ballX + ballWidth >= bricks[i].column * bricks[i].width
                        && ballX <= bricks[i].column * bricks[i].width + bricks[i].width
                        && ballY <= bricks[i].row * bricks[i].height + bricks[i].height
                        && ballY <= bricks[i].row * bricks[i].height) {
                    if (destroy != null) {
                        destroy.start();
                    }
                    velocity.setY((velocity.getY() + 1) * (-1));
                    bricks[i].setVisible();
                    points += 10;
                    brokenBricks++;
                    if (brokenBricks == NEW_BRICKS_PER_ROW * NUMBERS_OF_COLUMN) {
//                        launchGameOver();
                    }
                }
            }
        }

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
//        Intent intent = new Intent(context, MainActivity.class);
//        context.startActivity(intent);
        ((Activity) context).finish();
    }

    private int xVelocity() {
        int[] values = {-35, -30, -25, 25, 30, 35};
        int index = random.nextInt(6);
        return values[index];
    }
}
