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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class checkoutPage extends AppCompatActivity {

    // All the references we will need

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

        // Get the cartItems sent from MainActivity !!
        Intent myIntent = getIntent();
        cartItems = myIntent.getParcelableArrayListExtra("cartItems");

        // make sure cartItems isn't NULL, otherwise it won't run
        assert cartItems != null;
        for (int i = 0; i < cartItems.size(); i++) {
            // add the item to the listItems view and update the base price
            MainItem item = cartItems.get(i);
            cartItemNames.add(item.getItemName());
            price += item.getPrice();
        }

        // set the updated base price and final price
        basePrice = findViewById(R.id.basePrice);
        basePrice.setText(String.format("Base Price: $%.2f", price));
        finalPrice.setText(String.format("Final Price: $%.2f", price));

        taxText = findViewById(R.id.taxText);

        // control the listItems using this adapter
        arrayAdapter = new ArrayAdapter<>(checkoutPage.this, android.R.layout.simple_list_item_1, cartItemNames);
        listItems.setAdapter(arrayAdapter);

        // let the user input a tax percentage
        taxNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    tax = Integer.parseInt(String.valueOf(taxNumber.getText()));
                    taxText.setText(String.format("Tax: %s%%", taxNumber.getText()));
                    taxNumber.setVisibility(View.GONE);
                    setFinalPrice();
                    return true;
                }
                return false;
            }
        });

        // let the user input a voucher code and see if it's valid. If it is, give the user a $100 discount
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
                    setFinalPrice();
                    return true;
                }
                return false;
            }
        });


    }

    // get final price based on tax and discount
    @SuppressLint("DefaultLocale")
    public void setFinalPrice(){
        final_price = price * (1 + (tax / 100)) - discount;

        if (final_price < 0){
            final_price = 0.0;
        }

        finalPrice.setText(String.format("Final Price: $%.2f", final_price));

    }

    // show the user where to input their tax percentage
    public void taxClick(View view) {
        taxNumber.setVisibility(View.VISIBLE);
    }

    // show the user where to input the voucher code
    public void discountClick(View view) {
        discountCode.setVisibility(View.VISIBLE);
    }


    // reset all the quantities of items back to 0 once they've finished shopping!
    public void confirmCheckoutClick(View view) {

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
            bufferedWriter.write("Base Price:" + price);
            bufferedWriter.newLine();
            bufferedWriter.write("Tax:" + tax);
            bufferedWriter.newLine();
            bufferedWriter.write("Final Price:" + final_price);
            bufferedWriter.newLine();
            bufferedWriter.write("End of transaction.");
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}