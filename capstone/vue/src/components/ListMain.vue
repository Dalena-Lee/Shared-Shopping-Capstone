<template>
    <main class="account-main-container">
        <div class="list-container">
            <h3 class="list-name">List Name</h3>
        </div>
    </main>  
</template>

<script>
export default {
    name: "list-main",
    data () {
        return {
            lists: [],
        }
    },
    props: {
        list: Object
    },
    components: {

    },
    created() {

    },
    
}
</script>


