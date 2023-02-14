package com.techelevator.dao;

import com.techelevator.model.Invitation;

import java.util.List;

public interface InvitationDao {
    String generateInviteCode(int groupID);

    void sendInvite(int groupID, String sender, String recipient);

    List<Invitation> getPendingInvitations(String status, String principalUsername);

    void acceptInvitation(String sender, String recipient);

    void rejectInvitation(String sender, String username);

    void cancelInvitation(String sender, String username);

}
