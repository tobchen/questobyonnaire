<script setup>
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

        <div class="grid gap-2 grid-cols-2 mt-2">
            <select :value="type" @input="$emit('update:type', $event.target.value)">
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

            <label class="block">
                <input
                    type="checkbox"
                    :checked="required"
                    @input="$emit('update:required', $event.target.checked)"
                />
                Required
            </label>
        </div>

        <input
            type="text"
            :value="options"
            placeholder="Options (Comma Separated)"
            @input="$emit('update:options', $event.target.value)"
            v-show="type === 'choice' || type === 'open-choice'"
            class="block placeholder:text-emerald-500 w-full mt-2"
        />
        
        <fieldset class="grid grid-cols-3 gap-2 mt-2">
            <input
                type="button"
                value="Move Up"
                @click="$emit('moveUp', index)"
                class="cursor-pointer rounded bg-white"
            />
            <input
                type="button"
                value="Move Down"
                @click="$emit('moveDown', index)"
                class="cursor-pointer rounded bg-white"
            />
            <input
                type="button"
                value="Delete"
                @click="$emit('delete', index)"
                class="cursor-pointer rounded bg-white"
            />
        </fieldset>
    </fieldset>
</template>