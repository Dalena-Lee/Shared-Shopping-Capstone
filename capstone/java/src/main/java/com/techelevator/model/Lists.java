package com.techelevator.model;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;

public class Lists {
    private int groupId;
    private int listId;
    @NotBlank
    private String listName;

    public Lists(String listName, int listId, int groupId, int itemQuantity) {
        this.listName = listName;
        this.listId = listId;
        this.groupId = groupId;
    }

    public Lists() {

    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    @Override
    public String toString() {
        return "Lists{" +
                "listName='" + listName + '\'' +
                ", listId=" + listId +
                ", groupId=" + groupId +
                '}';
    }

}
