<script setup>
    import { ref } from "vue";
    import { useRoute } from "vue-router";
    import { createQuestionnaireItem, receiveQuestionnaire, sendUpdatedQuestionnaire } from "../scripts/questionnaire";
    import QuestionnaireMetaEdit from "../components/QuestionnaireMetaEdit.vue";
    import QuestionnaireItemEdit from "../components/QuestionnaireItemEdit.vue";

    const route = useRoute();

    const fetchPending = ref(true);
    const fetchSuccess = ref(false);

    const isSubmitEnabled = ref(true);

    const thisQuestionnaire = ref(null);

    receiveQuestionnaire(route.params.id).then(questionnaire => {
        thisQuestionnaire.value = questionnaire;

        fetchSuccess.value = true;
    }).catch(() => {
        // Do nothing
    }).finally(() => {
        fetchPending.value = false;
    });

    function addItem()
    {
        thisQuestionnaire.value.items.push(createQuestionnaireItem());
    }

    function deleteItem(index)
    {
        thisQuestionnaire.value.items.splice(index, 1);
    }

    function moveUpItem(index)
    {
        if (index > 0)
        {
            const items = thisQuestionnaire.value.items.splice(index, 1);
            thisQuestionnaire.value.items.splice(index - 1, 0, ...items);
        }
    }

    function moveDownItem(index)
    {
        if (index < thisQuestionnaire.value.items.length - 1)
        {
            const items = thisQuestionnaire.value.items.splice(index, 1);
            thisQuestionnaire.value.items.splice(index + 1, 0, ...items);
        }
    }

    function saveDraft(event)
    {
        event.preventDefault();

        isSubmitEnabled.value = false;

        sendUpdatedQuestionnaire(thisQuestionnaire.value).then(questionnaire => {
            thisQuestionnaire.value = questionnaire;
        }).catch(() => {
            alert("Updating failed!")
        }).finally(() => {
            isSubmitEnabled.value = true;
        });
    }

    function publish()
    {
        isSubmitEnabled.value = false;

        // TODO

        isSubmitEnabled.value = true;
    }

    function retire()
    {
        isSubmitEnabled.value = false;

        // TODO

        isSubmitEnabled.value = true;
    }
</script>

<template>
    <section class="p-2 rounded bg-emerald-500">
        <h2 class="text-xl font-bold">Edit Questionnaire</h2>

        <div v-if="fetchPending">
            Fetching questionnaire...
        </div>
        <div v-else-if="fetchSuccess">
            <form @submit.capture="saveDraft">
                <QuestionnaireMetaEdit
                    v-model:title="thisQuestionnaire.title"
                    v-model:description="thisQuestionnaire.description"
                />

                <section>
                    <QuestionnaireItemEdit
                        v-for="(item, index) in thisQuestionnaire.items"
                        :index="index"
                        v-model:text="item.text"
                        v-model:type="item.type"
                        v-model:options="item.options"
                        v-model:required="item.required"
                        v-model:multiple="item.multiple"
                        @delete="deleteItem"
                        @move-up="moveUpItem"
                        @move-down="moveDownItem"
                    />

                    <input
                        type="button"
                        value="Add Item"
                        @click="addItem"
                        class="block w-full mt-2 rounded bg-white cursor-pointer"
                    />
                </section>

                <fieldset class="mt-2 grid grid-cols-3 gap-2">
                    <input
                        type="submit"
                        :disabled="!isSubmitEnabled || thisQuestionnaire.status !== 'draft'"
                        value="Save Draft"
                        class="rounded bg-white disabled:opacity-50 enabled:cursor-pointer"
                    />
                    <input
                        type="button"
                        :disabled="!isSubmitEnabled || thisQuestionnaire.status !== 'draft'"
                        value="Publish"
                        @click="publish"
                        class="rounded bg-white disabled:opacity-50 enabled:cursor-pointer"
                    />
                    <input
                        type="button"
                        :disabled="!isSubmitEnabled || thisQuestionnaire.status === 'retired'"
                        value="Retire"
                        @click="retire"
                        class="rounded bg-white disabled:opacity-50 enabled:cursor-pointer"
                    />
                </fieldset>
            </form>
        </div>
        <div v-else>
            Unable to fetch questionnaire!
        </div>
    </section>
</template>
