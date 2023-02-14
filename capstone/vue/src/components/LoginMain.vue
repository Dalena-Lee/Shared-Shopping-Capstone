<template>
    <main class="main-container">
        <div class="intro">
            <!-- <h1 class="intro-puns">Pear up with your friends!</h1> -->
            <h1 class="intro-header">Welcome to GrocerWe! Share your shopping lists with friends and family!</h1>
        </div>
        <login-form class="login-form"></login-form>  
    </main>  
</template>

<script>
import LoginForm from "../components/LoginForm.vue";

export default {
    name: "LoginMain",
    components: {
        LoginForm
    }
}
</script>

<style>
@import url(//db.onlinewebfonts.com/c/4185e534bb81561ce640883597901c98?family=SignPainter);

    .main-container {
        display: grid;
        grid-template-columns: 1fr 1fr 1fr;
        grid-template-rows: 1fr 1fr 1fr;
        margin-bottom: 20px;
    }
    
    .login-form {
        grid-column: 3;
        grid-row: 1;
        margin-bottom: 20px;
    }

    .intro {
        display: grid;
        grid-template-columns: 1fr 1fr 1fr 1fr;
        grid-template-rows: 1fr 1fr 1fr;
        background: white;
        grid-column: 1 / 3;
        grid-row: 1 / 4;
        padding: 20px;
    }

    .intro-header {
        grid-column: 1 / 5;
        grid-row: 1;
        margin-right: 20px;
        margin-left: 20px;
        margin-top: 20px;
        font-size: 30px;
        font-family: 'Gelion', sans-serif;
    }
    
    .intro-puns {
        grid-column: 1 / 5;
        grid-row: 1;
        margin-top: 40px;
        margin-left: auto;
        margin-right: auto;
        font-family: "SignPainter";
        color: #3DC795;
    }

</style>
