package com.example.checkoutpricecalculator;

public class MainItem {
    Integer itemPic;
    String itemName;
    double itemPrice;
    int itemQuantity;

    public MainItem(Integer itemPic, String itemName, double itemPrice) {
        this.itemPic = itemPic;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = 0;
    }

    public Integer getItemPic() {
        return itemPic;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return "$" + itemPrice;
    }

    public String getItemQuantity() {
        return String.valueOf(itemQuantity);
    }
}
