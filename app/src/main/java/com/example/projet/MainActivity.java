package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button Puzzle_button ;
    private Button Trouve_paire ;
    private Button Calcul_mental;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Puzzle_button = findViewById(R.id.Puzzle_button);

        Puzzle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Puzzle.class));
            }
        });

        Trouve_paire = findViewById(R.id.Trouvez_la_paire);

        Trouve_paire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Trouve_paire.class));
            }
        });

        Calcul_mental = findViewById(R.id.Calcul_mental);

        Calcul_mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Level_calcul_mental.class));
            }
        });
    }
}