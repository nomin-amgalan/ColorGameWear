package com.app.colorgame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;
import java.lang.reflect.Array;
import java.util.Arrays;


public class ColorGameActivity extends MainActivity{
    View[] squares = {};
    int[] defColor = {0,0,0};
    double coef = 0.2;
    View pickedView;
    int[][] colors = {
            {255,0,0},
            {0,255,0},
            {0,0,255},
            {128,128,0},
            {0,128,128},
            {128,0,128}
    };
    boolean gameWon = false;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        squares = new  View[] {
                findViewById(R.id.square1),
                findViewById(R.id.square2),
                findViewById(R.id.square3),
                findViewById(R.id.square4)};
        setUpSquares();
        newGame();
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    private void setUpSquares(){
        for (int i=0; i<4; i++) {
            ImageView img = (ImageView) squares[i];
            img.setColorFilter(Color.rgb(defColor[0], defColor[1], defColor[2]));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v==pickedView){
                        displayToast("Correct!");
                        continueGame();
                    }
                    else {
                        displayToast("Wrong!");
                        newGame();
                    }
                }
            });
        }
        displayToast("Set up squares");
    }

    private void newGame(){
        /*
        Choose random color
        Set up squares to that color
        Get random index
        Set that square to odd color
        (odd color = color*0.2 approx)
        Set picked co
         */
        gameWon = false;
        coef = 0.2;
        double c = Math.random()*6;
        int colorIndex = (int)c;
        double s = Math.random()*4;
        int squareIndex = (int) s;
        pickedView = squares[squareIndex];
        int[] color = colors[colorIndex];
        for (int i=0;i<4;i++) {
            ImageView img = (ImageView) squares[i];
            img.setColorFilter(Color.rgb(color[0], color[1], color[2]));
            if (i==squareIndex){
                int[] oddColor = {(int)(color[0]*coef), (int)(color[1]*coef), (int)(color[2]*coef)};
                img.setColorFilter(Color.rgb(oddColor[0], oddColor[1], oddColor[2]));
            }
        }
        TextView score = (TextView) findViewById(R.id.score);
        score.setText(String.valueOf(0));

    }

    private void continueGame(){
        /*
        Get new random color theme
        Update the coefficient
        change colors of the squares
        update score
         */
        double c = Math.random()*6;
        int colorIndex = (int)c;
        double s = Math.random()*4;
        int squareIndex = (int) s;
        int[] color  = colors[colorIndex];
        int[] newColor = {(int)(color[0]*coef), (int)(color[1]*coef), (int)(color[2]*coef)};
        //Log.d("newColor", Arrays.toString(newColor));
        //Log.d("Max", Integer.toString(findMax(newColor, 3)));
        if (findMax(newColor, 3)>255){

            displayToast("Game Won!");
            gameWon = true;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (color[0]-newColor[0]<0 || color[1]-newColor[1]<0 || color[2]-newColor[2]<0){
            displayToast("Game Won!");
            gameWon = true;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (gameWon == false){
            pickedView = squares[squareIndex];
            coef = coef*1.2;
            for (int i=0;i<4;i++) {
                ImageView img = (ImageView) squares[i];
                img.setColorFilter(Color.rgb(color[0], color[1], color[2]));
                if (i==squareIndex){
                    // this will be the odd color
                    int[] oddColor = {(int)(color[0]*coef), (int)(color[1]*coef), (int)(color[2]*coef)};
                    img.setColorFilter(Color.rgb(oddColor[0], oddColor[1], oddColor[2]));
                }
            }
            TextView score = (TextView) findViewById(R.id.score);
            int newScore = Integer.parseInt(score.getText().toString())+1;
            score.setText(Integer.toString(newScore));
        }
    }

    private int findMax(int[] arr, int length){
        int max = 0;
        for (int i=0;i<length;i++){
            if (max<arr[i]){
                max = arr[i];
            }
        }
        return max;
    }

}