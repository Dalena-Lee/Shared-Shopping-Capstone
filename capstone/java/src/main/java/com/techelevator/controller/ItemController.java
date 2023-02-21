package com.techelevator.controller;

import com.techelevator.dao.JdbcGroupDao;
import com.techelevator.dao.JdbcItemsDao;
import com.techelevator.dao.JdbcListDao;
import com.techelevator.dao.JdbcUserDao;
import com.techelevator.model.Items;
import com.techelevator.model.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/groups")
@PreAuthorize("isAuthenticated()")
public class ItemController {


    @Autowired
    JdbcGroupDao groupDao;

    @Autowired
    JdbcListDao listDao;

    @Autowired
    JdbcUserDao userDao;

    @Autowired
    JdbcItemsDao itemsDao;

    public ItemController(JdbcGroupDao groupDao, JdbcListDao listDao, JdbcUserDao userDao, JdbcItemsDao itemsDao) {
        this.groupDao = groupDao;
        this.listDao = listDao;
        this.userDao = userDao;
        this.itemsDao = itemsDao;
    }
   // no underscore item: , listId:
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/lists/{id}/items/add",method = RequestMethod.POST)
    public void addItemsToList(@Valid @RequestBody Items item, @PathVariable int id, Principal principal) {
        int principalID = userDao.findIDByUsername(principal.getName());
        itemsDao.addItemToList(item, id, principalID);
    }

    //Need to pass in list id as a path variable; need to update methods to take in
    //new parameters
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/lists/{id}/items/remove/{itemId}", method = RequestMethod.DELETE)
    public String removeItemsFromList(@Valid @PathVariable int itemId) {
        try {
            itemsDao.removeItemFromList(itemId);
        } catch (Exception e) {
            return "There was a problem deleting the item.";
        }
        return "Item deleted successfully.";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/lists/{list_id}/clear", method = RequestMethod.DELETE)//change
    public String clearList(@Valid @PathVariable int listId) {
        try {
            itemsDao.clearList(listId);
        } catch (Exception e) {
            return "There was a problem clearing the list.";
        }
        return "List cleared successfully.";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/lists/items/edit/{id}", method = RequestMethod.PUT)

    //returns "item edited successfully" in postman,
    // but does not reflect updated values in database yet

    public String editItemInList(@Valid @RequestBody Items items,@PathVariable int id) {
        try {
            itemsDao.editItemInList(items, id);
        } catch (Exception e) {
            return "There was a problem editing the item.";
        }
        return "Item edited successfully.";
    }

//    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/lists/items/all/{listId}",method = RequestMethod.GET)
    public List<Items> displayListItems(@Valid @PathVariable int listId) {
        System.out.println("got here");
        System.out.println(listId);
        return itemsDao.displayListItems(listId);
    }


}
