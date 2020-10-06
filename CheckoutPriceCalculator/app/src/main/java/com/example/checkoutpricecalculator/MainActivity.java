package com.example.checkoutpricecalculator;

import androidx.appcompat.app.AppCompatActivity;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taxNumber = (EditText) findViewById(R.id.taxNumber);
        discountCode = (EditText) findViewById(R.id.discountCode);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Integer[] items = {R.drawable.realmadridjersey, R.drawable.macbookair,
                R.drawable.samsungwatch, R.drawable.nikehoodie, R.drawable.ps5console,
                R.drawable.boots};

        String[] itemNames = {"Real Madrid Jersey (Men's Football Kit)",
                "Apple Macbook Air 19-inch 2020 Model",
                "Samsung S3 Gear Frontier Smartwatch",
                "Nike Tech Fleece - Tracksuit Hoodie",
                "PS5 - Digital Edition (Pre-Order)", "Nike CR7 Mercurial Superfly Football Boots"};
    }

    public void taxClick(View view) {
        taxNumber.setVisibility(View.VISIBLE);
    }

    public void discountClick(View view) {
        discountCode.setVisibility(View.VISIBLE);
    }
}