<script setup>
    defineProps({
        index: Number,
        text: String,
        type: String,
        options: String,
        required: Boolean,
        multiple: Boolean,
    });

    defineEmits([
        "update:text",
        "update:type",
        "update:options",
        "update:required",
        "update:multiple",
        "moveUp",
        "moveDown",
        "delete",
    ]);
</script>

<template>
    <fieldset class="mt-2 p-2 rounded border-2 border-white">
        <input
            type="text"
            :value="text"
            placeholder="Text"
            @input="$emit('update:text', $event.target.value)"
            class="block placeholder:text-emerald-500 w-full text-xl"
        />

        <select
            :value="type"
            @input="$emit('update:type', $event.target.value)"
            class="block w-full mt-2 p-1 bg-white"
        >
            <option value="" disabled="true">Item Type</option>
            <option value="boolean">Boolean</option>
            <option value="decimal">Decimal</option>
            <option value="integer">Integer</option>
            <option value="date">Date</option>
            <option value="dateTime">Date & Time</option>
            <option value="time">Time</option>
            <option value="text">Text</option>
            <option value="longText">Long Text</option>
            <option value="choice">Choice</option>
            <option value="openChoice">Choice / Free Text</option>
            <option value="attachment">Attachment</option>
        </select>

        <input
            type="text"
            :value="options"
            placeholder="Options (Comma Separated)"
            @input="$emit('update:options', $event.target.value)"
            v-show="type === 'choice' || type === 'openChoice'"
            class="block w-full mt-2 placeholder:text-emerald-500"
        />

        <label class="inline-block mt-2">
            <input
                type="checkbox"
                :checked="required"
                @input="$emit('update:required', $event.target.checked)"
            />
            Required
        </label>

        <label class="inline-block ml-2 mt-2">
            <input
                type="checkbox"
                :checked="multiple"
                @input="$emit('update:multiple', $event.target.checked)"
            />
            Multiple
        </label>
        
        <fieldset class="mt-2 grid grid-cols-3 gap-2">
            <input
                type="button"
                value="Move Up"
                @click="$emit('moveUp', index)"
                class="rounded bg-white cursor-pointer"
            />
            <input
                type="button"
                value="Move Down"
                @click="$emit('moveDown', index)"
                class="rounded bg-white cursor-pointer"
            />
            <input
                type="button"
                value="Delete"
                @click="$emit('delete', index)"
                class="rounded bg-white cursor-pointer"
            />
        </fieldset>
    </fieldset>
</template>