package com.techelevator.dao;
//comment
import com.techelevator.model.Group;
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
    public void addItemToList(Items item, int listsId, int principalID) {
        String sql = "INSERT INTO items (item_name, item_quantity, item_category, list_id, user_id) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.queryForObject(sql, int.class, item.getItem(), item.getQuantity(), item.getCategory(), listsId, principalID);
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
        //String sql = " insert into items (item_name) values (?) where item_id = ?;";
        jdbcTemplate.update(sql, item.getItem(), id);
    }


    @Override
    public List<Items> displayListItems(int listId) {
        List<Items> itemsList = new ArrayList<>();
        String sql = "select * from items where list_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, listId);
        while (results.next()) {
            Items newItem = mapRowToItem(results);
            itemsList.add(newItem);
        }
        return itemsList;
    }


    public Items mapRowToItem(SqlRowSet rs) {
        Items item = new Items();
        item.setListId(rs.getInt("list_id"));
        item.setItem(rs.getString("item_name"));
        return item;
    }

    public Items mapNameToItem(SqlRowSet rs) {
        Items item = new Items();
        item.setItem(rs.getString("item_name"));
        return item;
    }
}
