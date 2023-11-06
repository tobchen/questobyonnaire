<script setup>
    defineProps({
        title: String,
        name: String,
        version: String,
        description: String,
    });

    defineEmits([
        "update:title",
        "update:name",
        "update:version",
        "update:description",
    ]);
</script>

<template>
    <section>
        <label class="block">
            Title:
            <input
                type="text"
                :value="title"
                @input="$emit('update:title', $event.target.value)"
                class="block w-full text-xl"
            />
        </label>
        
        <label class="block">
            Name (Computer Friendly):
            <input
                type="text"
                :value="name"
                @input="$emit('update:name', $event.target.value)"
                class="block w-full"
            />
        </label>

        <label class="block">
            Version Identifier:
            <input
                type="text"
                :value="version"
                @input="$emit('update:version', $event.target.value)"
                class="block w-full"
            />
        </label>

        <label class="block">
            Description:
            <textarea
                :value="description"
                @input="$emit('update:description', $event.target.value)"
                class="block w-full resize-none h-24"
            ></textarea>
        </label>
    </section>
</template>
