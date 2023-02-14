package com.techelevator.controller;

import com.techelevator.dao.GroupDao;
import com.techelevator.dao.InvitationDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Invitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/groups")
@PreAuthorize("isAuthenticated()")
public class InvitationController {

    @Autowired
    private InvitationDao invitationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupDao groupDao;

    public InvitationController(InvitationDao invitationDao,UserDao userDao, GroupDao groupDao){

        this.invitationDao = invitationDao;
        this.userDao = userDao;
        this.groupDao = groupDao;
    }

    //Note: The path '/groups' is already declared at the top for all methods.
    //Be sure to use that when consuming APIs (sending requests).

    //Tested: No
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/invitations/send", method = RequestMethod.POST)
    public String inviteUser(@Valid @RequestBody Invitation invitation, Principal principal) {
        String recipientUsername = invitation.getRecipient();
        String senderUsername = principal.getName();
        int groupID = invitation.getGroupID();

        try {
            this.invitationDao.sendInvite(groupID, senderUsername, recipientUsername);
        } catch (Exception e) {
            return "Something went wrong. Invitation was not sent.";
        } finally {
            return "Invitation sent successfully.";
        }
    }

    //Tested: No
    @RequestMapping(path = "/invitations/pending", method = RequestMethod.GET)
    public List<Invitation> getPendingInvitations(Principal principal){
        String principalUsername = principal.getName();
        return this.invitationDao.getPendingInvitations("Pending", principalUsername);
    }

    //User must be able to accept an invitation to join a group.
    //User must send a request with the sender's username in the body.
    //Tested: No
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/invitations/accept", method = RequestMethod.PUT)
    public String acceptInvitation(@RequestBody Invitation invitation, Principal principal) {
        String principalUsername = principal.getName();
        int recipientID = userDao.findIDByUsername(invitation.getRecipient());
        int groupID = invitation.getGroupID();

        try {
            this.invitationDao.acceptInvitation(principalUsername, invitation.getRecipient());
            this.groupDao.addUserToGroup(recipientID, groupID);
        } catch (Exception e) {
            return "Something went wrong. Could not accept invitation.";
        } finally {
            return "Invitation accepted.";
        }
    }

    //User must be able to deny an invitation to join a group.
    //User must send a request with the sender's username in the body.
    //Tested: No
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/invitations/reject", method = RequestMethod.PUT)
    public String rejectInvitation(@RequestBody Invitation invitation, Principal principal){
        String principalUsername = principal.getName();

        try {
            this.invitationDao.rejectInvitation(principalUsername, invitation.getSender());
        } catch (Exception e) {
            return "Something went wrong. Could not reject invitation.";
        } finally {
            return "Invitation rejected.";
        }
    }

    //Sender must be able to cancel an invitation they've sent.
    @ResponseStatus(HttpStatus.GONE)
    @RequestMapping(path = "/invitations/cancel", method = RequestMethod.POST)
    public String cancelInvitation(@RequestBody Invitation invitation, Principal principal){
        String principalUsername = principal.getName();

        try {
            this.invitationDao.cancelInvitation(principalUsername, invitation.getRecipient());
        } catch (Exception e) {
            return "Something went wrong. Could not cancel invitation.";
        } finally {
            return "Invitation canceled.";
        }
    }

}

