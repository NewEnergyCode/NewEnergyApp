package app.newenergyschool.ativity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import app.newenergyschool.R;
import app.newenergyschool.game.GameView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    public void startGame(View view) {
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }
}