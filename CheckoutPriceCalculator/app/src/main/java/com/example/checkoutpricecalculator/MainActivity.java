package com.example.checkoutpricecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // All the references we will need
    RecyclerView recyclerView;
    ArrayList<MainItem> mainItems;
    MainAdapter mainAdapter;
    ArrayList<MainItem> cartItems = new ArrayList<>();
    ArrayList<String> cartItemNames = new ArrayList<>();
    ListView listItems;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        // pictures for each item
        Integer[] itemPics = {R.drawable.realmadridjersey, R.drawable.macbookair,
                R.drawable.samsungwatch, R.drawable.nikehoodie, R.drawable.ps5console,
                R.drawable.boots};

        // names for each item
        String[] itemNames = {"Real Madrid Jersey (Men's Football Kit)",
                "Apple Macbook Air 19-inch 2020 Model",
                "Samsung S3 Gear Frontier Smartwatch",
                "Nike Tech Fleece - Tracksuit Hoodie",
                "PS5 - Digital Edition (Pre-Order)", "Nike CR7 Mercurial Superfly Football Boots"};

        // prices for each item
        double[] itemPrices = {146.99, 2824.99, 777.29, 167.99, 399.99, 209.99};

        // all the items in the store
        mainItems = new ArrayList<>();
        for (int i = 0; i < itemPics.length; i++) {
            MainItem item = new MainItem(itemPics[i], itemNames[i], itemPrices[i]);
            mainItems.add(item);
        }

        // Setting the horizontal "carousel"-like items
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                MainActivity.this, LinearLayoutManager.HORIZONTAL, false
        );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdapter = new MainAdapter(MainActivity.this, mainItems);
        recyclerView.setAdapter(mainAdapter);

        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onAddButtonClick(int position, TextView textQuantity) {
                MainItem item = mainItems.get(position);
                item.addToCart(textQuantity);
                textQuantity.setText(String.format("Quantity: %s", mainItems.get(position).getItemQuantity()));
                updateCart(item, true);
            }

            @Override
            public void onRemoveButtonClick(int position, TextView textQuantity) {
                MainItem item = mainItems.get(position);
                item.removeFromCart(textQuantity);
                textQuantity.setText(String.format("Quantity: %s", mainItems.get(position).getItemQuantity()));
                updateCart(item, false);
            }
        });

        // control the listItems view using the adapter
        listItems = findViewById(R.id.listItems);
        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, cartItemNames);
        listItems.setAdapter(arrayAdapter);

    }

    public void updateCart(MainItem item, boolean increased){

        // Check if the cart just got items added to or removed from, and change the listItems view accordingly
        if (increased) {
            cartItems.add(item);
            int index = containsItem(item);
            if (index != -1) {
                cartItemNames.set(index, item.getItemName() + " x" + item.getItemQuantity());
            }
            else {
                cartItemNames.add(item.getItemName() + " x" + item.getItemQuantity());
            }
        }
        else {
            cartItems.remove(item);
            int temp_quantity = item.getQuantity() + 1;
            if (item.getQuantity() > 0) {
                int index = cartItemNames.indexOf(item.getItemName() + " x" + temp_quantity);
                cartItemNames.set(index, item.getItemName() + " x" + item.getItemQuantity());
            }
            else {
                cartItemNames.remove(item.getItemName() + " x" + temp_quantity);
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }


    // helper function to see if item is already in the cart or not
    public int containsItem(MainItem item) {
        for (int i = 0; i < cartItemNames.size(); i++) {
            String itemName = cartItemNames.get(i);
            if (itemName.contains(item.getItemName())) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<MainItem> getCartItems() {
        return cartItems;
    }

    // clicking the checkout button sends the user to the second page, along with the items they have in the cart
    public void checkoutClick(@NonNull View view) {
        boolean isEmpty = true;
        for(int i = 0; i < mainItems.size(); i++){
            if (mainItems.get(i).getQuantity() != 0){
                isEmpty = false;
            }
        }
        if (!isEmpty){
            // proceed to checkout page
            Intent myIntent = new Intent(getBaseContext(), checkoutPage.class);
            ArrayList<MainItem> itemsInCart = getCartItems();
            myIntent.putParcelableArrayListExtra("cartItems", itemsInCart);
            startActivity(myIntent);
        }
        else {
            ExampleDialog exampleDialog = new ExampleDialog();
            exampleDialog.show(getSupportFragmentManager(), "example dialog");
        }
    }
}