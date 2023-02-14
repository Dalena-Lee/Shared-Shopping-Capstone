<template>
     <div id="create-list-form" class="overlay2">
        <div class="form2">
            <h1>Create new list</h1>
            <a class="close" href="#">&times;</a>
            <div class="content">
                <form>
                    <div>
                        <label for="listName"><p>List name</p></label>
                        <input type="text" name="listName" placeholder="Name your list" v-model="list.listName"/>
                    </div>
                    <button type="submit" v-on:click.prevent="saveList()">
                        <p>Create</p>
                    </button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import ListService from "../services/ListService";

export default {
    name: "create-list",
    data() {
        return {
            list: {
                listName: "",
            }
        }
    },
    methods: {
        saveList() {
            const newList = {
                listName: this.listName
            }

            ListService.createList(newList)
            .then(response => {
                if (response.status === 201) {
                    this.$router.push(`/groups/lists/${this.list.listId}`);
                }
            })
        }
    }
}

</script>