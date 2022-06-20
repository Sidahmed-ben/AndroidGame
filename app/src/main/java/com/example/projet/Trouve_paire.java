package com.example.projet;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Trouve_paire extends AppCompatActivity {

    ImageView iv_11,iv_12,iv_13,iv_14,iv_21,iv_22,iv_23,iv_24,iv_31,iv_32,iv_33,iv_34;
    ImageView[] imageViews = { iv_11,iv_12,iv_13,iv_14,iv_21,iv_22,iv_23,iv_24,iv_31,iv_32,iv_33,iv_34};

    int first_click = 1 , last_tag = 0;
    int[] iv_tab = {R.id.iv_11,R.id.iv_12,R.id.iv_13,R.id.iv_14,R.id.iv_21,R.id.iv_22,R.id.iv_23,R.id.iv_24,R.id.iv_31,R.id.iv_32,R.id.iv_33,R.id.iv_34};
    int[] Image_array = {R.drawable.fruit1,R.drawable.fruit2,R.drawable.fruit3,R.drawable.fruit4,R.drawable.fruit5,R.drawable.fruit6};
    Integer [] Dispaly_position = {0,1,2,3,4,5,0,1,2,3,4,5};
    AlertDialog.Builder builder ;
    Button retour ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouve_paire);
        retour = findViewById(R.id.Retour_trouve_paire);

        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Trouve_paire.this,MainActivity.class));
            }
        });
        
        init();
        Collections.shuffle(Arrays.asList(Dispaly_position));
        LoadImages();
        create_alerte();
    }

    void init(){
        for(int i = 0 ; i< 12; i++){
            imageViews[i] = (ImageView) findViewById(iv_tab[i]);
            imageViews[i].setOnClickListener(this::onClick);
        }
    }

    private void LoadImages(){
        for(int i = 0; i< 12; i++){
            imageViews[i].setImageResource(R.drawable.back_card);
        }
    }

    public void onClick(View view){
        int tag = Integer.parseInt((String) view.getTag());
        action(tag);
    }


    private void action(int tag){
        if(first_click == 1){
            imageViews[tag].setImageResource(Image_array[Dispaly_position[tag]]);
            last_tag = tag;
            first_click = 0;
        }else if(first_click == 0){
            set_image_disable();
            first_click = 1;
            imageViews[tag].setImageResource(Image_array[Dispaly_position[tag]]);
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    check_equal(tag);
                }
                @SuppressLint("WrongConstant")
                private void check_equal(int tag){
                    if(Dispaly_position[last_tag] == Dispaly_position[tag] && last_tag!=tag){
                        imageViews[last_tag].setVisibility(View.INVISIBLE);
                        imageViews[tag].setVisibility(View.INVISIBLE);
                        }else{
                        imageViews[last_tag].setImageResource(R.drawable.back_card);
                        imageViews[tag].setImageResource(R.drawable.back_card);
                    }
                    set_image_enable();
                    if(checkWin()){
                        builder.setTitle("Bravo ! ").setMessage(" Vous avez gagné !! Voulez vous rejouer ? ");
                        builder.show();
                    }
                }
            },1000);
        }
    }


    private boolean checkWin(){
        for(int i = 0 ;i< 12 ; i++){
            if(imageViews[i].getVisibility() != View.INVISIBLE)
                return false;
        }
        return true;
    }

    private void set_image_disable(){
        for(int i = 0 ;i< 12 ; i++){
            imageViews[i].setEnabled(false);
        }
    }

    private void set_image_enable(){
        for(int i = 0 ;i< 12 ; i++){
            imageViews[i].setEnabled(true);
        }
    }

    public void create_alerte(){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Collections.shuffle(Arrays.asList(Dispaly_position));
                LoadImages();
                for (int i = 0; i < 12; i++) {
                    imageViews[i].setVisibility(View.VISIBLE);
                }
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                startActivity(new Intent(Trouve_paire.this,MainActivity.class));
            }
        });
    }
}
