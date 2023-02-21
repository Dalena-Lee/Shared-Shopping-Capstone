<template>
  <div class="home">
    <div class="account-container">
      <account-header></account-header>
      <account-main></account-main>
    </div>
  </div>
</template>

<script>
import AccountHeader from "../components/AccountHeader.vue";
import AccountMain from "../components/AccountMain.vue";

export default {
  name: "home",
  components: {
    AccountHeader,
    AccountMain,
  }
};
</script>

<style scoped>
body {
  background: rgb(215, 243, 204);
  min-height: 100vh;
}
</style>