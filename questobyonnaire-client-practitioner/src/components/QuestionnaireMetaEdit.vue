<script setup>
    import { ref } from "vue";
    import uuid from "../scripts/uuid";

    const props = defineProps({
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

    const titleId = ref(`questionnaire-meta-edit-title-${uuid()}`);
    const nameId = ref(`questionnaire-meta-edit-name-${uuid()}`);
    const versionId = ref(`questionnaire-meta-edit-version-${uuid()}`);
    const descriptionId = ref(`questionnaire-meta-edit-description-${uuid()}`);
</script>

<template>
    <section>
        <label :for="titleId">Title:</label>
        <br />
        <input
            type="text"
            :id="titleId"
            :value="title"
            @input="$emit('update:title', $event.target.value)"
        />        
        <br />
        <label :for="nameId">Name (Computer Friendly):</label>
        <br />
        <input
            type="text"
            :id="nameId"
            :value="name"
            @input="$emit('update:name', $event.target.value)"
        />
        <br />
        <label :for="versionId">Version Identifier:</label>
        <br />
        <input
            type="text"
            :id="versionId"
            :value="version"
            @input="$emit('update:version', $event.target.value)"
        />
        <br />
        <label :for="descriptionId">Description:</label>
        <br />
        <textarea
            :id="descriptionId"
            :value="description"
            @input="$emit('update:description', $event.target.value)"
        ></textarea>
    </section>
</template>
