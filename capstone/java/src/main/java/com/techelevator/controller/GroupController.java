package com.techelevator.controller;

import com.techelevator.dao.GroupDao;
import com.techelevator.dao.JdbcGroupDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Group;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class GroupController {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private UserDao userDao;

    public GroupController(GroupDao groupDao, UserDao userDao) {
        this.groupDao = groupDao;
        this.userDao = userDao;
    }

    //Tested: Yes
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/groups/create", method = RequestMethod.POST)
    public String createGroup(Principal principal, @Valid @RequestBody Group group) {
        int principalID = userDao.findIDByUsername(principal.getName());
        User user = userDao.getUserById(principalID);
        try {
            groupDao.createGroup(group, principalID);
            groupDao.addUserToGroup(user.getId(), group.getGroupID());
        } catch (Exception e) {
            return "Something went wrong. Group was unable to be create.";
        } finally {
            return "Group was created successfully!";
        }
    }

    //User must be able to list all groups that they're in.
    //Tested: Yes
    @RequestMapping(path = "/groups/myGroups", method = RequestMethod.GET)
    public List<Group> findMyGroups(Principal principal) {
        int principalID = userDao.findIDByUsername(principal.getName());
        return groupDao.findMyGroups(principalID);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/groups/edit/{id}", method = RequestMethod.PUT)
    public String editGroupName(@PathVariable int id, @RequestBody Group group, Principal principal) {
        int principalID = userDao.findIDByUsername(principal.getName());
        String newName = group.getGroupName();
        Group editGroup = groupDao.getGroupByID(id);

        try {
            if (principalID == editGroup.getOwnerID()) {
                this.groupDao.editGroupName(id, newName);
            }
        } catch (Exception e) {
            return "Something went wrong. Unable to edit name of group with ID: " + id;
        } finally {
            return "Name of group with ID: " + id + "has been updated to " + newName;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/groups/delete/{id}", method = RequestMethod.DELETE)
    public String deleteGroup(@PathVariable int id, Principal principal) {
        int principalID = userDao.findIDByUsername(principal.getName());
        Group group = groupDao.getGroupByID(id);

        try {
            //ONLY delete group if principalID is ownerID
            if (principalID == group.getOwnerID()) {
                this.groupDao.deleteGroup(id);
                this.groupDao.removeUserFromGroup(principalID, id);
            }
        } catch (Exception e) {
            return "Group unable to be deleted. You are only able to delete groups that you have created.";
        } finally {
            return "User has deleted the group with ID: " + id;
        }
    }

    //User must be able to leave a group.
    @ResponseStatus(HttpStatus.GONE)
    @RequestMapping(path = "/groups/leave/{id}", method = RequestMethod.DELETE)
    public String leaveGroup(@RequestParam int id, Principal principal) {
        int principalID = userDao.findIDByUsername(principal.getName());
        try {
            this.groupDao.removeUserFromGroup(principalID, id);
            //if principalID is ownerID, delete the group
        } catch (Exception e) {
            return "Something went wrong. Unable to leave group.";
        } finally {
            return "User has left the group with ID: " + id;
        }
    }

    @RequestMapping(path = "/users/groups/{groupId}", method = RequestMethod.GET)
    public List<User> getUsersByGroupId(@PathVariable int groupId) {
        return userDao.getUsersByGroupId(groupId);
    }

    @RequestMapping(path = "/groups/{groupID}/join/{code}", method = RequestMethod.POST)
    public void addUserToGroup(@PathVariable int groupID, @PathVariable String code, Principal principal) {
        int principalID = userDao.findIDByUsername(principal.getName());
        groupDao.addUserToGroup(principalID, groupID);
    }
}
