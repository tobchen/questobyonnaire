<script setup>
    defineProps({
        title: String,
        description: String,
    });

    defineEmits([
        "update:title",
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

        <label class="block mt-2">
            Description:
            <textarea
                :value="description"
                @input="$emit('update:description', $event.target.value)"
                class="block resize-none w-full h-24"
            ></textarea>
        </label>
    </section>
</template>
