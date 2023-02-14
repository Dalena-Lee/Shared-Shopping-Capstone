import axios from 'axios';

export default {
    getMyGroups() {
        return axios.get('/groups/myGroups');
    },
    createGroup(group) {
        return axios.post('/groups/create', group);
    },
    updateGroup(group) {
        return axios.put(`/groups/${group.id}`, group);
    },
    deleteGroup(groupID) {
        return axios.delete(`/groups/${groupID}`);
    },
    getUsers(groupID) {
        return axios.get(`/users/groups/${groupID}`);
    }
}