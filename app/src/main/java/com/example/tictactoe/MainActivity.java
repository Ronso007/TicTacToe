package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;

    // Player representation
    // 0 - X
    // 1 - O
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // State meanings:
    //    0 - X
    //    1 - O
    //    2 - Null
    // put all win positions in a 2D array
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;
    ImageView status;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status  = findViewById(R.id.status);
        reset = findViewById(R.id.resetButton);
    }

    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        // game reset function will be called
        // if someone wins or the boxes are full
        if (!gameActive) {
            return;
        }

        // if the tapped image is empty
        if (gameState[tappedImage] == 2) {
            // increase the counter
            // after every tap
            counter++;

            // check if its the last box
            if (counter == 9) {
                // reset the game
                gameActive = false;
            }

            // mark this position
            gameState[tappedImage] = activePlayer;

            // this will give a motion
            // effect to the image
            img.setTranslationY(-1000f);

            // change the active player
            // from 0 to 1 or 1 to 0
            if (activePlayer == 0) {
                // set the image of x
                img.setImageResource(R.drawable.x);
                activePlayer = 1;

                // change the status
                status.setImageResource(R.drawable.oplay);
                Log.d("Debug:", "O's Turn - Tap to play");
            } else {
                // set the image of o
                img.setImageResource(R.drawable.o);
                activePlayer = 0;

                // change the status
                status.setImageResource(R.drawable.xplay);
                Log.d("Debug:", "X's Turn - Tap to play");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }

        int flag = 0;
        // Check if any player has won
        for (int i = 0; i < winPositions.length; i++) {
            int[] winPosition = winPositions[i];
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                flag = 1;

                // Somebody has won! - Find out who!
                String winnerStr;

                // game reset function be called
                gameActive = false;
                if (gameState[winPosition[0]] == 0) {
                    status.setImageResource(R.drawable.xwin);
                    winnerStr = "X has won";
                } else {
                    status.setImageResource(R.drawable.owin);
                    winnerStr = "O has won";
                }
                Context c = getApplicationContext();
                int id = c.getResources().getIdentifier("drawable/"+"mark" + i, null, c.getPackageName());

                ((ImageView)findViewById(R.id.winLine)).setImageResource(id);
                reset.setVisibility(View.VISIBLE);
                // Update the status bar for winner announcement
                Log.d("Debug:", winnerStr + ". Line Index:" + i);
            }
        }
        Log.d("Counter:", String.valueOf(counter));
        // set the status if the match draw
        if (counter == 9 && flag == 0) {
           // status.setText("Match Draw");
            Log.d("Debug:", "Match Draw");
            status.setImageResource(R.drawable.nowin);
            reset.setVisibility(View.VISIBLE);
        }

        Log.d("Test", String.valueOf(tappedImage));
    }

    // reset the game
    public void gameReset(View view) {
        counter = 0;
        gameActive = true;
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        // remove all the images from the boxes inside the grid
        ((ImageView) findViewById(R.id.topLeft)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.topCenter)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.topRight)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.middleLeft)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.middleCenter)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.middleRight)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.bottomLeft)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.bottomCenter)).setImageResource(R.drawable.empty);
        ((ImageView) findViewById(R.id.bottomRight)).setImageResource(R.drawable.empty);
        status.setImageResource(R.drawable.xplay);
        ((ImageView) findViewById(R.id.winLine)).setImageResource(R.drawable.empty);
        reset.setVisibility(View.INVISIBLE);
    }
}