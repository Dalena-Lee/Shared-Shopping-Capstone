import axios from 'axios';

export default {
    getLists(groupID) {
        return axios.get(`/groups/lists/${groupID}`);
    },
    createList(list, groupID) {
        return axios.post(`/groups/${groupID}/lists/create`, list);
    }
}