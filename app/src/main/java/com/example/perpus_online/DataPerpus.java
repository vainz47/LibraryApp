package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DataPerpus extends AppCompatActivity {

    CardView dataBuku, dataNovel, dataJurnal, dataSkripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_perpus);

        dataBuku = (CardView) findViewById(R.id.book);
        dataNovel = (CardView) findViewById(R.id.novel);
        dataJurnal = (CardView) findViewById(R.id.jurnal);
        dataSkripsi = (CardView) findViewById(R.id.skripsi);

        dataBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataPerpus.this, MenuBuku.class);
                startActivity(intent);
            }
        });
        dataNovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataPerpus.this, ManajemenNovel.class);
                startActivity(intent);
            }
        });
    }
}