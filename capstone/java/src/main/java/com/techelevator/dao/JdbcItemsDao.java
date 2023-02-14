package com.techelevator.dao;

import com.techelevator.model.Items;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcItemsDao implements ItemsDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcItemsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String addItemToList(Items item, int listsId, int principalID) {
        String sql = "INSERT INTO items (item_name, item_category, list_id, user_id) VALUES (?, ?, ?, ?) RETURNING item_id;";
        jdbcTemplate.queryForObject(sql, int.class, item.getItem(), item.getCategory(), listsId, principalID);
        //add in timestamp for when item is added
        return "Item added";
    }

    @Override
    public void removeItemFromList(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = (?);";
        jdbcTemplate.queryForObject(sql, int.class, itemId);
    }

    @Override
    public void clearList(int listId) {
        String sql = "DELETE * FROM items WHERE list_id = (?);";
        jdbcTemplate.update(sql,listId);
    }

    @Override
    public void editItemInList(Items item, int id) {
        String sql = "UPDATE items SET item_name = (?) WHERE item_id = (?);";
        jdbcTemplate.update(sql, item.getItem(), id);
    }

    // maybe instead of a string return an object?
    // create new object that contains only the values we want to display
    // convert user_id into the user's name
    // return timestamp of added item
    @Override
    public List<String> displayListItems(int listId) {
        List<String> itemsList = new ArrayList<>();
        Items items = new Items();
        String sql = "select item_name, item_category, user_id from items where list_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, listId);
        while (results.next()) {

            itemsList.add("Name: " +items.getItem() + "   |   Category: " + items.getCategory() + "   |   UserID: " + items.getUserId());
        }
        return itemsList;
    }

    // useless??
    public Items mapRowToItem(SqlRowSet rs) {
        Items item = new Items();
        item.setListId(rs.getInt("list_id"));
        item.setItem(rs.getString("item_name"));
        return item;
    }

    public Items mapDisplayToItem(SqlRowSet rs) {
        Items item = new Items();
        item.setItem(rs.getString("item_name"));
        item.setCategory(rs.getString("item_category"));
        item.setUserId(rs.getInt("user_id"));
        return item;
    }



}
