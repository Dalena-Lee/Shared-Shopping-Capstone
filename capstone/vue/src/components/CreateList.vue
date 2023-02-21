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
                    <button class="actions" type="submit" v-on:click.prevent="saveList()">
                        <p>Create</p>
                    </button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: "create-list",
    data() {
        return {
            list: {
                listName: "",
            },
        }
    },
}

</script>