package com.techelevator.dao;

import com.techelevator.model.Items;
import com.techelevator.model.Lists;

import java.util.List;

public interface ItemsDao {

    void addItemToList(Items item, int listsId, int principalID);

    List<Items> displayListItems(int listId);

    void removeItemFromList(int itemId);

    void clearList(int listId);

    void editItemInList(Items item, int id);//optional

}
