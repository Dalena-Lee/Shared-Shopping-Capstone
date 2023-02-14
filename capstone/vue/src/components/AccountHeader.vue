<template>
    <header>
        <div class="account-header-container">
            <img class="account-logo" v-bind:src="require('../assets/logo.png')" alt=""/>
            <h1 class="brand">GrocerWe</h1>
            <router-link 
            class="logout" 
            v-bind:to="{ name: 'logout' }" 
            v-if="$store.state.token != ''"
            style="text-decoration: none;">
            <span class="material-symbols-outlined">logout</span>
            <h3>Logout</h3>
            </router-link> 
        </div>
    </header>
</template>

<script>
    export default {
        name: 'account-header',
        components: {
        }
    }
</script>

<style scoped>
@import url('https://fonts.cdnfonts.com/css/gelion');

.account-header-container {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-template-rows: 1fr;
    height: 80px;
    background:white;
}

.account-header-container h3 {
    font-size: 18px;
}

.account-logo {
    grid-column: 1;
    grid-row: 1;
    width: 4rem;
    margin-top: auto;
    margin-bottom: auto;
    margin-left: 20px;
}

.brand {
    font-size: 3rem;
    margin-left: 100px;
    margin-top: 15px;
}

.logout {
    display: flex;
    grid-column: 3;
    margin-left: auto;
    margin-right: 40px;
    margin-top: auto;
    margin-bottom: auto;
    color: #3DC795;
}

.material-symbols-outlined {
    vertical-align: center;
}

</style>