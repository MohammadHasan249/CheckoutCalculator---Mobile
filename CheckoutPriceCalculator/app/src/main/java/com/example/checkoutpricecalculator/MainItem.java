package com.example.checkoutpricecalculator;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

public class MainItem implements Parcelable {
    Integer itemPic;
    String itemName;
    double itemPrice;
    int itemQuantity;
    TextView text;

    public MainItem(Integer itemPic, String itemName, double itemPrice) {
        this.itemPic = itemPic;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = 0;
        this.text = null;
    }

    protected MainItem(Parcel in) {
        if (in.readByte() == 0) {
            itemPic = null;
        } else {
            itemPic = in.readInt();
        }
        itemName = in.readString();
        itemPrice = in.readDouble();
        itemQuantity = in.readInt();
    }

    public static final Creator<MainItem> CREATOR = new Creator<MainItem>() {
        @Override
        public MainItem createFromParcel(Parcel in) {
            return new MainItem(in);
        }

        @Override
        public MainItem[] newArray(int size) {
            return new MainItem[size];
        }
    };

    public void reset(){
        itemQuantity = 0;
        text.setText(String.format("Quantity: 0"));
    }

    public void addToCart(TextView t) {
        itemQuantity++;
        text = t;
    }

    public void removeFromCart(TextView t) {
        if(itemQuantity > 0){
            itemQuantity--;
        }
        text = t;
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

    public int getQuantity() { return itemQuantity; }

    public String getItemQuantity() {
        return String.valueOf(itemQuantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (itemPic == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemPic);
        }
        dest.writeString(itemName);
        dest.writeDouble(itemPrice);
        dest.writeInt(itemQuantity);
    }
}