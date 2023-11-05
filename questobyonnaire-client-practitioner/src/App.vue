<script setup>
    import { RouterLink, RouterView } from "vue-router";
</script>

<template>
    <header>
        <h1>Questobyonnaire</h1>

        <nav>
            <RouterLink to="/">Home</RouterLink>
            |
            <RouterLink to="/editor">Editor</RouterLink>
        </nav>
    </header>

    <RouterView />
</template>
