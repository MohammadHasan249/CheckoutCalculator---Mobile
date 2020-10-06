package com.example.checkoutpricecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    private Button taxButton = (Button) findViewById(R.id.addTax);
    private EditText taxNumber;
    private EditText discountCode;
    private RecyclerView recyclerView;

    ArrayList<MainItem> mainItems;
    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taxNumber = (EditText) findViewById(R.id.taxNumber);
        discountCode = (EditText) findViewById(R.id.discountCode);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Integer[] itemPics = {R.drawable.realmadridjersey, R.drawable.macbookair,
                R.drawable.samsungwatch, R.drawable.nikehoodie, R.drawable.ps5console,
                R.drawable.boots};

        String[] itemNames = {"Real Madrid Jersey (Men's Football Kit)",
                "Apple Macbook Air 19-inch 2020 Model",
                "Samsung S3 Gear Frontier Smartwatch",
                "Nike Tech Fleece - Tracksuit Hoodie",
                "PS5 - Digital Edition (Pre-Order)", "Nike CR7 Mercurial Superfly Football Boots"};

        double[] itemPrices = {146.99, 2824.99, 777.29, 167.99, 399.99, 209.99};
//        String[] itemPrices = {String.valueOf(146.99), String.valueOf(2824.99), String.valueOf(777.29), String.valueOf(167.99), String.valueOf(399.99), String.valueOf(209.99)};
        int[] itemQuantities = {0, 0, 0, 0, 0, 0};

        mainItems = new ArrayList<>();
        for (int i = 0; i < itemPics.length; i++) {
            MainItem item = new MainItem(itemPics[i], itemNames[i], itemPrices[i]);
            mainItems.add(item);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                MainActivity.this, LinearLayoutManager.HORIZONTAL, false
        );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdapter = new MainAdapter(MainActivity.this, mainItems);
        recyclerView.setAdapter(mainAdapter);
    }

    public void taxClick(View view) {
        taxNumber.setVisibility(View.VISIBLE);
    }

    public void discountClick(View view) {
        discountCode.setVisibility(View.VISIBLE);
    }
}