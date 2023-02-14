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
}

.home {
    display: grid;
    grid-template-rows: 1fr 1fr 1fr 1fr;
    grid-template-columns: 1fr 1fr 1fr;
    min-height: 100vh;
    border-radius: 20px;
    background: #eeeeee;
}

.account-container {
    display: grid;
    grid-row: 1 / 4;
    grid-column: 1 / 5;
    grid-template-rows: 1fr 1fr 1fr;
    grid-template-columns: 1fr 1fr 1fr;
    background-color: #F7FAFC;
    margin-top: 100px;
    min-height: 100vh;
}

.account-container header {
    grid-row: 1;
    grid-column: 1 / 4;
    margin-top: -100px;
}

.account-container main {
    grid-row: 1 / 4;
    grid-column: 1 / 4;
}
</style>