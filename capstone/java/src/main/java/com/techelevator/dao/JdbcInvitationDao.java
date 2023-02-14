package com.techelevator.dao;

import com.techelevator.model.Group;
import com.techelevator.model.Invitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class JdbcInvitationDao implements InvitationDao {

    @Autowired GroupDao groupDao;

    @Autowired UserDao userDao;

    private JdbcTemplate jdbcTemplate;

    public JdbcInvitationDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String generateInviteCode(int id) {
        String groupID = id + "";
        String code = Base64.getEncoder().encodeToString(groupID.getBytes());
        return code;
    }

    @Override
    public void sendInvite(int groupID, String sender, String recipient) {
        //Automatically change an invitation's status to pending when sent.
        String sql = "INSERT INTO invitations " +
                "(group_id, sender, recipient, status) " +
                "VALUES (?, ?, ?, 'Pending');";
        jdbcTemplate.update(sql, groupID, sender, recipient);
    }

    @Override
    public List<Invitation> getPendingInvitations(String status, String principalUsername) {
        String sql = "SELECT * " +
                "FROM invitations " +
                "WHERE (status = ?) AND (sender = ?);";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, status, principalUsername);

        List<Invitation> filteredInvitations = new ArrayList<>();
        while(rowSet.next()){
            Invitation invitation = mapInvitationFromRowSet(rowSet);
            filteredInvitations.add(invitation);
        }

        return filteredInvitations;
    }

    @Override
    public void acceptInvitation(String sender, String recipient) {
        String sql = "UPDATE invitations " +
                "SET status = 'Accepted' " +
                "WHERE sender = ?" +
                "AND recipient = ?;";
        jdbcTemplate.update(sql, sender, recipient);
    }

    @Override
    public void rejectInvitation(String sender, String recipient) {
        String sql = "UPDATE invitations " +
                "SET status = 'Rejected' " +
                "WHERE sender = ?" +
                "AND recipient = ?;";
        jdbcTemplate.update(sql, sender, recipient);
    }

    @Override
    public void cancelInvitation(String sender, String recipient) {
        String sql = "DELETE FROM invitations " +
                "WHERE sender = ?" +
                "AND recipient = ?;";
        jdbcTemplate.update(sql, sender, recipient);
    }


    private Invitation mapInvitationFromRowSet(SqlRowSet rowSet){

        Invitation invitation = new Invitation();
        invitation.setInvitationID(rowSet.getInt("invitation_id"));
        invitation.setSender(rowSet.getString("sender"));
        invitation.setRecipient(rowSet.getString("recipient"));
        invitation.setStatus(rowSet.getString("status"));

        return invitation;

    }


}

