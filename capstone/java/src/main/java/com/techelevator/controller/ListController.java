package com.techelevator.controller;


import com.techelevator.dao.GroupDao;
import com.techelevator.dao.ListDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Group;
import com.techelevator.model.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ListController {

    @Autowired
    private ListDao listDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupDao groupDao;

    public ListController(ListDao listDao, UserDao userDao, GroupDao groupDao) {
        this.listDao = listDao;
        this.userDao = userDao;
        this.groupDao = groupDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/groups/lists/create", method = RequestMethod.POST)
    public String createList(@RequestBody Lists lists){
        try {
            listDao.createList(lists, lists.getGroupId());

        } catch (Exception e) {
            return "Something went wrong";
        }
        return "List created successfully";

    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/groups/lists/{id}", method = RequestMethod.GET)
    public List<Lists> displayListsInGroup(@PathVariable int id){
        return listDao.displayListsInGroup(id);
    }


    @ResponseStatus(HttpStatus.GONE)
    @RequestMapping(path = "/groups/lists/delete/{listId}", method = RequestMethod.DELETE)
    //postman says list deleted successfully bu databse doesnt reflect update yet
    public void removeList(@Valid @PathVariable int listId){

            listDao.removeList(listId);

    }

    // displayMyLists ??? its in listDao

}
