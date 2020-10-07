package com.example.checkoutpricecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.Toast;

public class checkoutPage extends AppCompatActivity {

    EditText taxNumber;
    EditText discountCode;
    EditText full_Price;
    EditText final_Price;
    ListView listItems;
    ArrayList<MainItem> cartItems;
    int tax;
    int discount;
    double full_price;
    double final_price;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page);

        taxNumber = (EditText) findViewById(R.id.taxNumber);
        discountCode = (EditText) findViewById(R.id.discountCode);
        listItems = findViewById(R.id.listItems);

        Intent myIntent = getIntent();
        cartItems = myIntent.getParcelableArrayListExtra("cartItems");

        for (int i = 0; i < cartItems.size(); i++) {
            // add it to the listItems text
        }
    }

    public void updateFinalPrice(TextView text){
        // get full price, tax, and discounts from their texts or calculations
        double full_price = 0.0;
        tax = 3;
        discount = 3;
        final_price = full_price * (1 - (tax / 100)) - discount;

        if (tax >= 0 && tax < 100 && final_price < 0){
            text.setText(String.format("Final Price: %s", final_price));
        }
    }

    public void taxClick(View view) {
        taxNumber.setVisibility(View.VISIBLE);
    }



    public void discountClick(View view) {
        discountCode.setVisibility(View.VISIBLE);
    }

    public double getBasePrice() {
        double price = 0.0;
        for (int i = 0; i < cartItems.size(); i++) {
            MainItem item = cartItems.get(i);
            price += item.getPrice();
        }
        return price;
    }

    // might want to change args for this to save final price properly
    // , TextView final_price
    public void confirmCheckoutClick(View view) {

        ConfirmCheckoutDialog confirmCheckoutDialog = new ConfirmCheckoutDialog();
        confirmCheckoutDialog.show(getSupportFragmentManager(), "confirm checkout dialog");

        save(cartItems);
        for (int i = 0; i < cartItems.size(); i++) {
            cartItems.get(i).reset();
        }
    }

    public void save(ArrayList<MainItem> mainItems){
        try {
            FileWriter writer = new FileWriter("transactions.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write("Transaction saved.");
            bufferedWriter.newLine();

            for (int i = 0; i < mainItems.size(); i++) {
                if (mainItems.get(i).getQuantity() != 0){
                    bufferedWriter.write(mainItems.get(i).getItemName() + ": " + mainItems.get(i).getItemQuantity());
                    bufferedWriter.newLine();
                }
            }
            // get full price, tax, and final prices from texts or calculations
            bufferedWriter.write("Full Price:" + ""); // Place full price here
            bufferedWriter.newLine();
            bufferedWriter.write("Tax:" + ""); // Place tax here
            bufferedWriter.newLine();
            bufferedWriter.write("Final Price:" + ""); // Place full price here
            bufferedWriter.newLine();
            bufferedWriter.write("End of transaction.");
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}