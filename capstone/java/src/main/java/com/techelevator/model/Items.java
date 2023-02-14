package com.techelevator.model;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class Items {
    private int listId;
    private int itemId;
    @NotBlank
    private String item;

    private String category;
    private int userId;

    public Items(int listId, String item, int itemId, String category, int userId) {
        this.listId = listId;
        this.item = item;
        this.itemId = itemId;

        this.category = category;
        this.userId = userId;
    }

    public Items(){}

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
