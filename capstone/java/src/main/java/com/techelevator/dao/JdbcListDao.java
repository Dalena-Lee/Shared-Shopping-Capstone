package com.techelevator.dao;

import com.techelevator.model.Group;
import com.techelevator.model.Items;
import com.techelevator.model.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcListDao implements ListDao{

    private final JdbcUserDao userDao;
    private final JdbcTemplate jdbcTemplate;
    private final JdbcGroupDao groupDao;

    public JdbcListDao(JdbcUserDao userDao, JdbcTemplate jdbcTemplate, JdbcGroupDao groupDao) {
        this.userDao = userDao;
        this.jdbcTemplate = jdbcTemplate;
        this.groupDao = groupDao;
    }

    @Override
    public void createList(Lists lists, int groupId) {
        String sql = "INSERT INTO lists (list_name, group_id) VALUES (?, ?);";
        jdbcTemplate.update(sql, lists.getListName(), groupId);
    }

    @Override
    public List<Lists> displayListsInGroup(int groupId) {
        List <Lists> groupLists = new ArrayList<>();
        String sql = "select * from lists where group_id = ?;";
        SqlRowSet  results = jdbcTemplate.queryForRowSet(sql,groupId);

        while(results.next()){
            Lists lists = mapRowToList(results);
            groupLists.add(lists);
        }

        return groupLists;
    }

    public List<Lists> displayMyLists(Group group){
        List <Lists> groupLists = new ArrayList<>();
        String sql = "select list_id, list_name, group_id from lists where owner_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, group.getGroupID());
        while(results.next()){
            Lists lists = mapRowToList(results);
            groupLists.add(lists);

        }
        return groupLists;
    }


    @Override
    public void removeList(int listId) {
        //postman says list deleted successfully bu databse doesnt reflect update yet
        String sql = "DELETE FROM lists WHERE list_id = ?;";
            jdbcTemplate.update(sql,listId);
    }



    public Lists mapRowToList(SqlRowSet rs){
        Lists list = new Lists();
        list.setListId(rs.getInt("list_id"));
        list.setListName(rs.getString("list_name"));
        list.setGroupId(rs.getInt("group_id"));
        return list;
    }
}
