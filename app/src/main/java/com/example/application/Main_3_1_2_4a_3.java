package com.example.application;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//<<
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.text.NumberFormat;
//>>
public class Main_3_1_2_4a_3 extends AppCompatActivity {
    private  TextView textView13,textView14,textView15,textView16;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_1_2_4a_3);
        textView13=(TextView)findViewById(R.id.textView13);
        textView14=(TextView)findViewById(R.id.textView14);
        textView15=(TextView)findViewById(R.id.textView15);
        textView16=(TextView)findViewById(R.id.textView16);
        show();
    }

    private void show() {
        NumberFormat nf = NumberFormat.getInstance();
        Bundle bundle = getIntent().getExtras();
        String dat = bundle.getString("dat");
        int mon = bundle.getInt("mon");
        String hint = bundle.getString("hin");
        String spi1 = bundle.getString("spi1");
        String spi2 = bundle.getString("spi2");
        String not = bundle.getString("not");
        String text1 = spi1;
        String text2=hint +spi2;
        String text3=""+mon;
        String text4=not;
        textView13.setText(text1);
        textView14.setText(text2);
        textView15.setText(text3);
        textView16.setText(text4);
    }

}


