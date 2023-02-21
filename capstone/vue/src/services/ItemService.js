import axios from 'axios';

export default {
    getItems(listID) {
        console.log("DEBUG" + listID)
        return axios.get(`/groups/lists/items/all/${listID}`);
    },
    addItemsToList(item, listID) {
        return axios.post(`/groups/lists/${listID}/add`, item);
    },
    removeItemsFromList(listID, itemID) {
        return axios.delete(`/groups/lists/${listID}/remove/${itemID}`);
    }
}