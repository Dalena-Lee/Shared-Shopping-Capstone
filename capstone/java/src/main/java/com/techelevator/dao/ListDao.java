package com.techelevator.dao;

import com.techelevator.model.Group;
import com.techelevator.model.Items;
import com.techelevator.model.Lists;

import java.util.List;

public interface ListDao {


    void createList(Lists lists, int groupId);

    List<Lists> displayListsInGroup(int groupId);

    String removeList(int listId);

    List<Lists> displayMyLists(Group group);


}
