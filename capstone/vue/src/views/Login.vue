<template>
  <div class="login-container">
    <app-header class="login-header"></app-header>
    <login-main class="login-main"></login-main>
  </div>
</template>

<script>
import LoginMain from "../components/LoginMain.vue";
import AppHeader from "../components/AppHeader.vue";

export default {
  name: "login",
  components: {
    LoginMain,
    AppHeader
  }
}
</script>
