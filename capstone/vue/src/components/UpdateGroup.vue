<template>
  <form v-on:submit.prevent>
    <div class="field">
      <label for="title">Title</label>
      <input type="text" v-model="title" />
    </div>
    <div class="actions">
      <button type="submit" v-on:click="updateGroup()">Save Document</button>
    </div>
  </form>
</template>

<script>
import GroupService from "../services/GroupService";

export default {
  name: "update-group",
  props: ["groupID"],
  data() {
    return {
      title: ""
    };
  },
  methods: {
    updateGroup() {
      const group = { id: this.groupID, title: this.title };
      GroupService.updateGroup(group).then( response => {
        if (response.status === 200) {
          this.$router.push(`/`);
        }
      })
    }
  },
  created() {
    GroupService
      .get(this.groupID)
      .then(response => {
        this.$store.commit("SET_ACTIVE_GROUP", response.data);
        this.title = response.data.title;
      })
      .catch(error => {
        if (error.response.status == 404) {
          this.$router.push({name: 'NotFound'});
        }
      });
  }
};
</script>

<style>
</style>
