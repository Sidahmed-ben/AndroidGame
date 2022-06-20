package com.example.projet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Calcul_mental extends AppCompatActivity {
    public static  int delay ; // 1000 milliseconds == 1 second
    private Button valider ;
    private EditText editText;
    TextView tv_chrono , tv_gauche, tv_operation,tv_droite,score;
    private String[] operation = {"+","X","-"};
    private int levelpm ,levelx,score_ =0 ,chronostart  ;
    AlertDialog.Builder builder ;
    Handler handler ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul_mental);

        // Button
        valider = findViewById(R.id.Valider_reponse);
        valider.setOnClickListener(this::onClick);
        // champs de saisie
        tv_chrono  = findViewById(R.id.chronometer);
        tv_chrono.setText(String.valueOf(7));
        tv_gauche = findViewById(R.id.tv_gauche);
        tv_droite = findViewById(R.id.tv_droite);
        tv_operation = findViewById(R.id.tv_operation);
        //editText
        editText = findViewById(R.id.editTextNumber);
        score = findViewById(R.id.score);
        init();
        start_chrono();
        create_alerte();

    }

    public void init(){
        levelpm = 10 ;
        levelx = 3 ;
        chronostart = 9 ;
        score_ = 0;
        score.setText("score : "+score_);
    }

    public void create_equation(){
        Random random = new Random();
        int r = random.nextInt(3);
        int r1,r2;
        tv_operation.setText(operation[r]);

        switch(operation[r]){
            case "X":
                levelx+=1;
                r = random.nextInt(levelx);
                tv_gauche.setText(String.valueOf(r));
                r = random.nextInt(levelx);
                tv_droite.setText(String.valueOf(r));
               break;
            case "+":
                levelpm+=1;
                r = random.nextInt(levelpm);
                tv_gauche.setText(String.valueOf(r));
                r = random.nextInt(levelpm);
                tv_droite.setText(String.valueOf(r));
                break;
            case "-":
                do{
                    levelpm+=1;
                    r1 = random.nextInt(levelpm);
                    tv_gauche.setText(String.valueOf(r1));
                    r2 = random.nextInt(levelpm);
                    tv_droite.setText(String.valueOf(r2));
                }while(r1<r2);
                break;
            default:
                break;
        }

    }

    public void start_chrono(){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                tv_chrono.setText(String.valueOf(chronostart--));
                if(chronostart == -1){
                    builder.setTitle("Oups").setMessage(" Votre Score est : "+score_+" !! Voulez vous rejouer ? ");
                    builder.show();
                    return;
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        String str = String.valueOf(score_);
        if(correct_result()){
            create_equation();
            Toast.makeText(Calcul_mental.this," Reponse Juste !",
                    Toast.LENGTH_SHORT).show();
            chronostart = 9;
            tv_chrono.setText(String.valueOf(chronostart));
            score_++;
            score.setText("score : "+score_);

        }else{
            Toast.makeText(Calcul_mental.this," Reponse Fausse !",
                    Toast.LENGTH_SHORT).show();
        }
        editText.setText("");
    }

    public void create_alerte(){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                init();
                start_chrono();
                create_equation();
            }
        });

        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                startActivity(new Intent(Calcul_mental.this,MainActivity.class));
            }
        });
    }

    public boolean correct_result(){
        int result = calaculate_result();
        String chaine = editText.getText().toString();
        String vide = "";
        if(chaine.equals(vide)){
            return false;
        }
        int chaine_int =  Integer.parseInt(chaine);
        if(result == chaine_int)
            return true;
        else
            return false;
    }


    public int calaculate_result(){
        int resultat = 0;
        String g = tv_gauche.getText().toString();
        String d = tv_droite.getText().toString();

        int gi = Integer.parseInt(g);
        int di = Integer.parseInt(d);

        switch(tv_operation.getText().toString()){
            case "+" :
                    resultat = gi+di;
                    break;
            case "-" :
                    resultat = gi-di;
                    break;
            case "X" :
                    resultat = gi*di;
            default:
                    break;
        }
        return resultat;
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        super.onBackPressed();
    }
}