package com.cryptog.exercise6roulettegame;

import android.content.ClipData;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnLongClickListener, OnDragListener {

    private ImageView imageViewDrag;
    private int currentNumber;
    private Button btnStart;
    private ImageView imageViewRed;
    private ImageView imageViewBlack;
    private int color;
    private int typeGame;
    private int score = 0;
    private int[] textViewIds = new int[] { R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView5,R.id.textView6, R.id.textView7,R.id.textView8,R.id.textView9
    };
    private TextView textViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    imageViewDrag = findViewById(R.id.imageViewDrag);
    imageViewDrag.setOnLongClickListener(this);
    imageViewBlack = findViewById(R.id.imageViewBlack);
    imageViewRed = findViewById(R.id.imageViewRed);
    imageViewBlack.setOnDragListener(this);
    imageViewRed.setOnDragListener(this);

    textViewScore = findViewById(R.id.textViewScore);

        for (int id : textViewIds) {
            findViewById(id).setOnDragListener(this);
        }
        btnStart = findViewById(R.id.btnRoll);

        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int numberRandom = random.nextInt(9) + 1;
                if(typeGame == 1){
                    if(currentNumber == numberRandom){
                        score = score + 100;
                    } else{
                        score = score - 10;
                    }
                }else{
                  if(color == ((ColorDrawable)findViewById(textViewIds[numberRandom - 1]).getBackground()).getColor()){
                      score = score + 10;
                  }else{
                      score = score - 10;
                  }
                }
                textViewScore.setText(String.format("Score : %d",score));
            }
        });

    }

    @Override
    public boolean onLongClick(View view) {
        view.startDragAndDrop(ClipData.newPlainText("",""), new DragShadowBuilder(view), null ,0);
        for (int id : textViewIds) {
           TextView textView =  findViewById(id);
           textView.setTextSize(20);
        }
        return true;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        if(dragEvent.getAction() == DragEvent.ACTION_DROP){
            if(view.getId() != imageViewRed.getId() && view.getId() != imageViewBlack.getId()) {
                currentNumber = Integer.valueOf(String.valueOf(view.getContentDescription()));
                TextView textView = (TextView) view;
                textView.setTextSize(50);
                typeGame = 1;
            }else{
                typeGame = 0;
                color = ((ColorDrawable)view.getBackground()).getColor();
            }
        }
        return true;
    }
}
