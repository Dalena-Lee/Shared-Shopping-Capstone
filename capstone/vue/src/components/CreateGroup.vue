<template>
    <div class="create-form">
        <div class="box">
            <a class="button" href="#create-group-form"> 
                <h5>+ Create new group</h5>
            </a>
        </div>
        <div id="create-group-form" class="overlay2">
            <div class="form2">
                <h1>Create new group</h1>
                <a class="close" href="#">&times;</a>
                <div class="content">
                    <form v-on:submit.prevent>
                        <div>
                            <label for="groupName"><p>Group name</p></label>
                            <input type="text" name="groupName" placeholder="Name your group" v-model="group.groupName"/>
                        </div>
                        <button class="actions" type="submit" v-on:click="saveGroup()" href="#">
                            <p>Create</p>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>       
</template>

<script>
import GroupService from "../services/GroupService";

export default {
    name: "create-group",
    data() {
        return {
            group: {
                groupName: ""
            }
        }
    },
    methods: {
        saveGroup() {
            const newGroup = {
                groupName: this.group.groupName
            }

            GroupService.createGroup(newGroup)
            .then(response => {
                if (response.status === 201) {
                    window.location.reload();
                }
            })
        },
    },
}

</script>
