<script setup>
    import { RouterLink, RouterView } from "vue-router";
</script>

<template>
    <header class="text-white">
        <h1 class="font-bold text-5xl">Questobyonnaire</h1>

        <nav>
            <RouterLink to="/" class="hover:underline">Home</RouterLink>
            |
            <RouterLink to="/editor" class="hover:underline">Editor</RouterLink>
        </nav>
    </header>

    <main>
        <RouterView />
    </main>
</template>
