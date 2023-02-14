<template>
    <div class="logo">
        <img v-bind:src="require('../assets/logo.png')" alt=""/>
    </div>
</template>

<script>
    export default {
        name: 'AppLogo'
    }
</script>
<style scoped>
    .logo, .logo *{
        width: 15rem;
    }
</style>