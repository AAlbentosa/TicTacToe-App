package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.internal.FlowLayout;

public class MainActivity extends AppCompatActivity {

    private ImageView[] casillasOcupadas= new ImageView[9];
    private int index=0;
    private boolean type = true;
    private Button playAgain;
    int red, yellow, winimg, fichaGanadora;
    ImageView[][] combinacionesGanadoras;
    ImageView win, fichaG;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red=R.drawable.red;
        yellow=R.drawable.yellow;

        ImageView casillas[] = new ImageView[9];


        win =(ImageView) findViewById(R.id.winner);
        winimg=R.drawable.winner;
        win.setY(win.getY() + (-1500));
        win.setImageResource(winimg);

        fichaG =(ImageView) findViewById(R.id.fichag);
        fichaG.setY(fichaG.getY() + (-1500));

        casillas[0] = (ImageView) findViewById(R.id.ficha1);
        casillas[1] = (ImageView) findViewById(R.id.ficha2);
        casillas[2] = (ImageView) findViewById(R.id.ficha3);
        casillas[3] = (ImageView) findViewById(R.id.ficha4);
        casillas[4] = (ImageView) findViewById(R.id.ficha5);
        casillas[5] = (ImageView) findViewById(R.id.ficha6);
        casillas[6] = (ImageView) findViewById(R.id.ficha7);
        casillas[7] = (ImageView) findViewById(R.id.ficha8);
        casillas[8] = (ImageView) findViewById(R.id.ficha9);

        int[][][] posicionesGanadoras=new int[9][4][3];

        combinacionesGanadoras= new ImageView[][]{
                {casillas[0], casillas[1], casillas[2]},
                {casillas[3], casillas[4], casillas[5]},
                {casillas[6], casillas[7], casillas[8]},
                {casillas[0], casillas[3], casillas[6]},
                {casillas[1], casillas[4], casillas[7]},
                {casillas[2], casillas[5], casillas[8]},
                {casillas[0], casillas[4], casillas[8]},
                {casillas[2], casillas[4], casillas[6]}
        };


         
    }

    public void colocarFicha(View v){

        ImageView ficha = (ImageView) v;
        if(posicionLibre(ficha)) {
            int y = 1500;
            ficha.setY(ficha.getY() + (-y));
            if (type) {
                ficha.setImageResource(red);
                ficha.setTag(R.drawable.red);
                fichaGanadora=red;
                type = !type;
            } else {
                ficha.setImageResource(yellow);
                ficha.setTag(R.drawable.yellow);
                fichaGanadora=yellow;
                type = true;
            }

            ficha.animate().translationYBy(y).rotation(3600).setDuration(2000);
            casillasOcupadas[index]=ficha;
            index++;
            checkWinner(fichaGanadora);
            if(index==9)
                showPlayAgainButton();
        }
    }

    private boolean posicionLibre(ImageView ficha) {
        for(ImageView f: casillasOcupadas) {
            if(f!=null) {
                if (f.equals(ficha)) {
                    return false;
                }
            }
        }
            return true;
    }

    private void checkWinner(int ficha){

        for(int x=0; x<combinacionesGanadoras.length; x++){

            if(combinacionesGanadoras[x][0].getTag()!=null) {
                if (combinacionesGanadoras[x][0].getTag().equals(combinacionesGanadoras[x][1].getTag()) && combinacionesGanadoras[x][1].getTag().equals(combinacionesGanadoras[x][2].getTag())) {
                    MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.win);
                    ring.start();

                    ConstraintLayout fl = (ConstraintLayout) findViewById(R.id.fl);
                    fl.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
                    win.animate().translationYBy(1300).rotation(3600).setDuration(2000);

                    fichaG.setImageResource(ficha);
                    fichaG.animate().translationYBy(1300).rotation(3600).setDuration(2000);

                    showPlayAgainButton();
                }
            }
        }
    }

    private void showPlayAgainButton() {
        playAgain= (Button) findViewById(R.id.playAgain);
        playAgain.setVisibility(View.VISIBLE);
        playAgain.animate().rotation(360).setDuration(1000);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
    }

    private void restart(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}