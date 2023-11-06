<script setup>
    import { ref } from "vue";
    import uuid from "../scripts/uuid";

    defineProps({
        index: Number,
        text: String,
        type: String,
        options: String,
        required: Boolean,
    });

    defineEmits([
        "update:text",
        "update:type",
        "update:options",
        "update:required",
        "moveUp",
        "moveDown",
        "delete",
    ]);

    const textId = ref(`questionnaire-item-edit-text-${uuid()}`);
    const typeId = ref(`questionnaire-item-edit-type-${uuid()}`);
    const optionsId = ref(`questionnaire-item-edit-options-${uuid()}`);
    const requiredId = ref(`questionnaire-item-edit-required-${uuid()}`);
</script>

<template>
    <div>
        <label :for="textId">Text:</label>
        <br />
        <input
            type="text"
            :id="textId"
            :value="text"
            @input="$emit('update:text', $event.target.value)"
        />
        <br />
        <select :id="typeId" :value="type" @input="$emit('update:type', $event.target.value)">
            <option value="" disabled="true">Item Type</option>
            <option value="boolean">Boolean</option>
            <option value="decimal">Decimal</option>
            <option value="integer">Integer</option>
            <option value="date">Date</option>
            <option value="dateTime">Date & Time</option>
            <option value="time">Time</option>
            <option value="string">Text</option>
            <option value="text">Long Text</option>
            <option value="url">URL</option>
            <option value="choice">Choice</option>
            <option value="open-choice">Choice / Free Text</option>
        </select>
        <br />
        <div v-show="type === 'choice' || type === 'open-choice'">
            <label>Options (Comma Separated):</label>
            <br />
            <input
                type="text"
                :id="optionsId"
                :value="options"
                @input="$emit('update:options', $event.target.value)"
            />
        </div>
        <label :for="requiredId">Required:</label>
        <input
            type="checkbox"
            :id="requiredId"
            :checked="required"
            @input="$emit('update:required', $event.target.checked)"
        />
        <br />
        <input type="button" value="Move Up" @click="$emit('moveUp', index)" />
        <input type="button" value="Move Down" @click="$emit('moveDown', index)" />
        <input type="button" value="Delete" @click="$emit('delete', index)" />
    </div>
</template>