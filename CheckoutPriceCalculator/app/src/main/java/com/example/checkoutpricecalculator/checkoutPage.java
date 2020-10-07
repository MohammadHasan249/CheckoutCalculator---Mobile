package com.example.checkoutpricecalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    ListView listItems;
    ArrayList<MainItem> cartItems;
    double tax = 0.0;
    double discount;
    double price = 0.0;
    double final_price = 0.0;
    ArrayList<String> cartItemNames;
    ArrayAdapter<String> arrayAdapter;
    TextView basePrice;
    TextView taxText;
    TextView finalPrice;
    String[] vouchers = {"HELLOPROFS&TAS", "PLSGIVEUS100PERCENT", "SMILEYFACE", "HAVEANICEDAY"};

    @SuppressLint("DefaultLocale")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page);

        taxNumber = findViewById(R.id.taxNumber);
        discountCode = findViewById(R.id.discountCode);
        listItems = findViewById(R.id.listItems);
        finalPrice = findViewById(R.id.finalPrice);

        cartItemNames = new ArrayList<>();

        Intent myIntent = getIntent();
        cartItems = myIntent.getParcelableArrayListExtra("cartItems");

        assert cartItems != null;
        for (int i = 0; i < cartItems.size(); i++) {
            // add it to the listItems text
            MainItem item = cartItems.get(i);
            cartItemNames.add(item.getItemName() + " x" + item.getItemQuantity());
            price += item.getPrice();
        }


        basePrice = findViewById(R.id.basePrice);
        basePrice.setText(String.format("Base Price: $%.2f", price));
        finalPrice.setText(String.format("Final Price: $%.2f", price));

        taxText = findViewById(R.id.taxText);

        arrayAdapter = new ArrayAdapter<>(checkoutPage.this, android.R.layout.simple_list_item_1, cartItemNames);
        listItems.setAdapter(arrayAdapter);

        taxNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    tax = Integer.parseInt(String.valueOf(taxNumber.getText()));
                    taxText.setText(String.format("Tax: %s%%", taxNumber.getText()));
                    taxNumber.setVisibility(View.GONE);
//                    setFinalPrice();
                    return true;
                }
                return false;
            }
        });

        discountCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String code = String.valueOf(discountCode.getText());
                    List<String> list = Arrays.asList(vouchers);
                    if (list.contains(code)) {
                        discount = 100.0;
                    }
                    else {
                        discount = 0.0;
                    }
                    discountCode.setVisibility(View.GONE);
//                    setFinalPrice();
                    return true;
                }
                return false;
            }
        });


    }

    public void setFinalPrice(){
        // get full price, tax, and discounts from their texts or calculations
        final_price = price * (1 - (tax / 100)) - discount;

        if (final_price < 0){
            final_price = 0.0;
        }

        finalPrice.setText(String.format("Final Price: %s", final_price));

    }

    public void taxClick(View view) {
        taxNumber.setVisibility(View.VISIBLE);
    }

    public void discountClick(View view) {
        discountCode.setVisibility(View.VISIBLE);
    }


    // might want to change args for this to save final price properly
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
            bufferedWriter.write("Base Price:" + price); // Place full price here
            bufferedWriter.newLine();
            bufferedWriter.write("Tax:" + tax); // Place tax here
            bufferedWriter.newLine();
            bufferedWriter.write("Final Price:" + final_price); // Place full price here
            bufferedWriter.newLine();
            bufferedWriter.write("End of transaction.");
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}