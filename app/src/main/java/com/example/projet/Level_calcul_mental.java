package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Level_calcul_mental extends AppCompatActivity {

    Button easy,medium,hard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_calcul_mental);

        easy = findViewById(R.id.Easy);
        medium = findViewById(R.id.Medium);
        hard = findViewById(R.id.Hard);


        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calcul_mental.delay = 2200;
                startActivity(new Intent(Level_calcul_mental.this,Calcul_mental.class));
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calcul_mental.delay = 1700;
                startActivity(new Intent(Level_calcul_mental.this,Calcul_mental.class));
            }
        });


        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calcul_mental.delay = 1000;
                startActivity(new Intent(Level_calcul_mental.this,Calcul_mental.class));
            }
        });

    }
}