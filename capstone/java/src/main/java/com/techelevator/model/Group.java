package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.List;

public class Group {

    private int ownerID;
    private int groupID;
    @NotBlank
    private String groupName;
    private String group_code;

    public Group(int groupID, String groupName, int ownerID) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.ownerID = ownerID;
    }

    public Group() {

    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }
}
