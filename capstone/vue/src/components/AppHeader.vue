<template>
    <header>
        <div class="brand-container">
            <img class="logo" v-bind:src="require('../assets/logo.png')" alt=""/>
            <h1 class="brand">GrocerWe</h1>
            <h1 class="intro-header">Welcome to GrocerWe! Share your shopping lists with friends and family!</h1>
 
        </div>
        
    </header>
</template>

<script>    
    export default {
        name: 'app-header',
        data(){
            return{
                expanded: false,
            }
        },
        components :  {
        },
        methods: {}
    }
</script>
<style scoped>

.brand-container {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-template-rows: 1fr;
}

.brand {
    font-size: 4rem;
    margin-left: 125px;
    margin-top: 40px;
}

h1 {
    margin-left: 110px;
    margin-top: 45px;
}

.logo {
    grid-column: 1;
    grid-row: 1;
    width: 6rem;
    margin-top: 25px;
}

.intro-header {
        color: rgb(121, 121, 121);
        grid-column: 2 / 4;
        grid-row: 1;
        margin-right: 20px;
        margin-top: 60px;
        font-size: 30px;
        font-family: 'Gelion', sans-serif;
    }

</style>
