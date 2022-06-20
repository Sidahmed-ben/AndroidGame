package com.example.projet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Puzzle extends AppCompatActivity {

    private int emptyX = 2;
    private int emptyY = 2;
    final private int ligne = 3,colonne = 3 ;
    private RelativeLayout rel_layout;
    private Button[][] buttons;
    private Button return_puzzle;
    private Integer[] integer = {1,2,3,4,5,6,7,8}; /* c'est un tableau contenant des chiffres aléatoirs entre 0 et 15*/
    private HashMap<Button,Integer> hashMap ;
    private int[] bgImg = {R.drawable.image1,R.drawable.image2 , R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6,R.drawable.image7,R.drawable.image8} ;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        return_puzzle = findViewById(R.id.PuzzleReturn);
        Load_image();
        Random_image();
        LoadRandomImage();
        create_alerte();
        return_puzzle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Puzzle.this,MainActivity.class));
            }
        });

    }

    private void Load_image(){
        hashMap  = new HashMap<Button,Integer>();
        rel_layout = findViewById(R.id.rel_layout);
        buttons = new Button[3][3];
        for(int i = 0; i< rel_layout.getChildCount()-1; i++){
            buttons[i/ligne][i%colonne] = (Button)rel_layout.getChildAt(i);
            buttons[i/ligne][i%colonne].setBackgroundResource(bgImg[i]);
            hashMap.put(buttons[i/ligne][i%colonne],bgImg[i]);
        }
        int last = rel_layout.getChildCount()-1;
        buttons[last/ligne][last%colonne] = (Button) rel_layout.getChildAt(last);
    }


    private void Random_image(){
        Collections.shuffle(Arrays.asList(integer));
        if(!isSolvable())
            Random_image();
    }

    private boolean isSolvable(){ /* return true lorsque la partie est solvable */
        int countInversions = 0;
        for(int i = 0 ; i< 8 ; i++){
            for(int j = 0 ; j< i ; j++){
                if(integer[j] > integer[i])
                    countInversions++;
            }
        }
        return countInversions%2 == 0;
    }

    private void LoadRandomImage(){
        emptyX = 2;
        emptyY = 2;
        for(int i = 0 ;i< rel_layout.getChildCount()-1 ; i++){
            buttons[i/ligne][i%colonne].setText(String.valueOf(integer[i]));
            buttons[i/ligne][i%colonne].setBackgroundResource(bgImg[integer[i]-1]);
        }
        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this, R.color.colorFreeButton));
    }

    public void buttonClick(View v){
        Button button = (Button) v ;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';
        if((Math.abs(emptyX-x) == 1 && emptyY == y) || (Math.abs(emptyY-y) == 1 && emptyX == x)  ){
            buttons[emptyX][emptyY].setText(button.getText().toString());
            String s  = buttons[emptyX][emptyY].getText().toString();
            int indice = Integer.parseInt(s);
            indice--;
            buttons[emptyX][emptyY].setBackgroundResource(hashMap.get(buttons[indice/ligne][indice%colonne]));
            button.setText("");
            button.setBackgroundColor(Color.TRANSPARENT);
            emptyX = x;
            emptyY = y;
            checkWin();
        }
    }

    private void checkWin() {
        if (emptyX == 2 && emptyY == 2) {
            for (int i = 0; i < rel_layout.getChildCount() - 1; i++) {
                if (!buttons[i / ligne][i % colonne].getText().toString().equals(String.valueOf(i + 1))) {
                    return;
                }
            }
            Toast.makeText(this, " You Win !!!!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < rel_layout.getChildCount(); i++) {
                buttons[i/ligne][i%colonne].setClickable(false);
            }
            builder.setTitle("Bravo ! ").setMessage(" Vous avez gagné !! Voulez vous rejouer ? ");
            builder.show();
        }
    }


    public void create_alerte(){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Load_image();
                Random_image();
                LoadRandomImage();
                for (int i = 0; i < rel_layout.getChildCount(); i++) {
                    buttons[i/ligne][i%colonne].setClickable(true);
                }
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                startActivity(new Intent(Puzzle.this,MainActivity.class));
            }
        });
    }

}