package com.azhar.catering.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.catering.R;
import com.azhar.catering.history.HistoryOrderActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ModelCategories> modelCategoriesList = new ArrayList<>();
    List<ModelTrending> modelTrendingList = new ArrayList<>();
    CategoriesAdapter categoriesAdapter;
    TrendingAdapter trendingAdapter;
    ModelCategories modelCategories;
    ModelTrending modelTrending;
    RecyclerView rvCategories, rvTrending;
    CardView cvHistory;
    CardView cvGMaps;
    TextView tvWelcomeMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStatusbar();
        setInitLayout();
        setCategories();
        setTrending();

        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage); // TextView untuk menampilkan pesan

        // Ambil nama pengguna dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");

        // Tampilkan pesan selamat datang dengan nama pengguna
        tvWelcomeMessage.setText("Hallo, " + username);
    }



    private void setInitLayout() {
        //Button Maps
        cvGMaps = findViewById(R.id.cvGMaps);
        cvGMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GMaps.class);
                startActivity(intent);
            }
        });

        //Button History
        cvHistory = findViewById(R.id.cvHistory);
        cvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryOrderActivity.class);
                startActivity(intent);
            }
        });

        rvCategories = findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new GridLayoutManager(this, 3));
        rvCategories.setHasFixedSize(true);

        rvTrending = findViewById(R.id.rvTrending);
        rvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrending.setHasFixedSize(true);
    }

    // List Kategori
    private void setCategories() {
        modelCategories = new ModelCategories(R.drawable.ic_complete, "Paket Lengakp");
        modelCategoriesList.add(modelCategories);
        modelCategories = new ModelCategories(R.drawable.ic_saving, "Paket Hemat");
        modelCategoriesList.add(modelCategories);
        modelCategories = new ModelCategories(R.drawable.ic_healthy, "Paket Sehat");
        modelCategoriesList.add(modelCategories);
        modelCategories = new ModelCategories(R.drawable.ic_fast, "Cepat Saji");
        modelCategoriesList.add(modelCategories);

        categoriesAdapter = new CategoriesAdapter(this, modelCategoriesList);
        rvCategories.setAdapter(categoriesAdapter);
    }

    // List Trending
    private void setTrending() {
        modelTrending = new ModelTrending(R.drawable.complete_1,"Paket Hemat", "2.200 disukai");
        modelTrendingList.add(modelTrending);
        modelTrending = new ModelTrending(R.drawable.complete_2,"Paket Lengkap", "1.220 disukai");
        modelTrendingList.add(modelTrending);
        modelTrending = new ModelTrending(R.drawable.complete_3,"Paket Sehat", "345 disukai");
        modelTrendingList.add(modelTrending);
        modelTrending = new ModelTrending(R.drawable.complete_4,"Cepat Saji", "590 disukai");
        modelTrendingList.add(modelTrending);

        trendingAdapter = new TrendingAdapter(this, modelTrendingList);
        rvTrending.setAdapter(trendingAdapter);
    }

    public void setStatusbar() {
        if (Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(@NonNull Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

}