package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {
    private MapsActivity Map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        TextView distance = (TextView)findViewById(R.id.distance);
        TextView kcal = (TextView)findViewById(R.id.kcal);
        TextView LSPEED = (TextView)findViewById(R.id.Last_Speed);

        String dis = Map.Vd + "km";
        String KCAL = "200kcal";//ㅎㅎ....
        String LSP = Map.LS + "km/h";

        distance.setText(dis);
        kcal.setText(KCAL);
        LSPEED.setText(LSP);


    }
}
