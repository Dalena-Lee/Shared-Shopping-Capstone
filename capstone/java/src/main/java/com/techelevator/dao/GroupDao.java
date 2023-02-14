package com.techelevator.dao;

import com.techelevator.model.Group;
import com.techelevator.model.User;

import java.util.List;

public interface GroupDao {

    void createGroup(Group group, int principalID);

    List<Group> findMyGroups(int principalID);

    void addUserToGroup(int userID, int groupID);

    void removeUserFromGroup(int userID, int groupID);

    void editGroupName (int groupID, String newName);

    void deleteGroup(int groupID);

    Group getGroupByID(int principalID);

}
