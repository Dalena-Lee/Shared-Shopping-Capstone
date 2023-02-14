import axios from 'axios';

export default {
    getLists(groupID) {
        return axios.get(`/groups/lists/${groupID}`);
    },
    createList(list) {
        return axios.post('/groups/lists/create', list);
    }
}