package com.techelevator.dao;

import com.techelevator.model.Group;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGroupDao implements GroupDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InvitationDao invitationDao;

    public JdbcGroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createGroup(Group group, int principalID){
        String groupSQL = "INSERT INTO groups (group_name, owner_id) VALUES (?, ?) RETURNING group_id;";
        Integer newGroupId = jdbcTemplate.queryForObject(groupSQL, Integer.class, group.getGroupName(), principalID);

        group.setGroupID(newGroupId);

        String userGroupSQL = "INSERT INTO user_group (group_id, user_id) VALUES (?, ?);";
        jdbcTemplate.update(userGroupSQL, group.getGroupID(), principalID);

        String code = invitationDao.generateInviteCode(newGroupId);
        String updateGroupSQL = "UPDATE groups SET group_code = ? WHERE group_id = ?";
        jdbcTemplate.update(updateGroupSQL, code, newGroupId);
    }

    @Override
    public List<Group> findMyGroups(int principalID) {
        List<Group> groups = new ArrayList<>();
        String sql =
                "SELECT * " +
                "FROM groups " +
                "WHERE group_id IN " +
                "(SELECT group_id FROM user_group WHERE user_id = ?)";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, principalID);
        while (results.next()) {
            Group group = mapRowToGroup(results);
            groups.add(group);
        }
        return groups;
    }

    @Override
    public void addUserToGroup(int userID, int groupID) {
        String userGroupSQL = "INSERT INTO user_group (group_id, user_id) VALUES (?, ?);";
        jdbcTemplate.update(userGroupSQL, groupID, userID);
    }

    @Override
    public void removeUserFromGroup(int userID, int groupID) {
        String sql = "DELETE FROM user_group WHERE user_id = ? AND group_id = ?;";
        jdbcTemplate.queryForRowSet(sql,userID, groupID);
    }

    @Override
    public void editGroupName (int groupID, String newName){
        String sql = "UPDATE groups SET group_name = ? WHERE group_id = ?";
        jdbcTemplate.update(sql, groupID, newName);
    }

    public void deleteGroup(int groupID) {
        String sql = "DELETE FROM groups WHERE group_id = ?";
        jdbcTemplate.update(sql, groupID);
    }

    @Override
    public Group getGroupByID(int groupID) {
        String sql = "SELECT * FROM groups WHERE group_id = ?"; //check table name case sensetivity
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, groupID);
        if (results.next()) {
            return mapRowToGroup(results);
        } else {
            return null;
        }
    }

    public Group mapRowToGroup(SqlRowSet rs){
        Group group = new Group();
        group.setGroupID(rs.getInt("group_id"));
        group.setGroup_code(rs.getString("group_code"));
        group.setGroupName(rs.getString("group_name"));
        group.setOwnerID(rs.getInt("owner_id"));
        return group;
    }
}
