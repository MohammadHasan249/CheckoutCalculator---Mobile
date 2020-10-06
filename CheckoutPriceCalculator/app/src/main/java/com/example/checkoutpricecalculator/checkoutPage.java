package com.example.checkoutpricecalculator;

import android.os.Bundle;
import android.view.View;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class checkoutPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page);
    }

    public void taxClick(View view) {
        //taxNumber.setVisibility(View.VISIBLE);
        // tax is <100 and >=0, then set tax to that. if not, ignore
    }

    public void discountClick(View view) {
        //discountCode.setVisibility(View.VISIBLE);
        // if discountCode is correct, then accept it at set the discount code variable to that value save
        // if not, ignore
    }

    public void confirmCheckoutClick(@NonNull View view) {
        // write transaction to file (don't overwrite)
        // reset all quantity, tax, discount, and cart values
        // send some sort of alert
        save();
    }

    public void save(){
        try {
            FileWriter writer = new FileWriter("transactions.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write("Transaction saved.");
            bufferedWriter.newLine();

//            for (int i = 0; i < mainItems.size(); i++) {
//                if (mainItems.get(i).getQuantity() != 0){
//                    bufferedWriter.write(mainItems.get(i).getItemName() + ": " + mainItems.get(i).getItemQuantity());
//                    bufferedWriter.newLine();
//                }
//            }
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
