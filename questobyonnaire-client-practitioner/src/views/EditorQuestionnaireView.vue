<script setup>
    import { ref } from "vue";
    import { useRoute } from "vue-router";
    import { read as fhirRead, update as fhirUpdate } from "../scripts/fhir";
    import { createMeta, fillMeta, createResource as createQuestionnaire,
        createItem } from "../scripts/questionnaire";
    import QuestionnaireMetaEdit from "../components/QuestionnaireMetaEdit.vue";
    import QuestionnaireItemEdit from "../components/QuestionnaireItemEdit.vue";

    const route = useRoute();

    const fetchPending = ref(true);
    const fetchSuccess = ref(false);

    const isSubmitEnabled = ref(true);

    const thisId = route.params.id;
    const thisMeta = ref(createMeta());
    const thisItems = ref(new Array());

    fhirRead("http://localhost:8080/fhir", "Questionnaire", thisId).then(resource => {
        if (resource !== null)
        {
            fetchSuccess.value = true;

            fillMeta(thisMeta.value, resource);

            // TODO Add Items
        }
    }).catch(() => {
        // Do nothing
    }).finally(() => {
        fetchPending.value = false;
    });

    function addItem()
    {
        thisItems.value.push(createItem());
    }

    function deleteItem(index)
    {
        thisItems.value.splice(index, 1);
    }

    function moveUpItem(index)
    {
        if (index > 0)
        {
            const items = thisItems.value.splice(index, 1);
            thisItems.value.splice(index - 1, 0, ...items);
        }
    }

    function moveDownItem(index)
    {
        if (index < thisItems.value.length - 1)
        {
            const items = thisItems.value.splice(index, 1);
            thisItems.value.splice(index + 1, 0, ...items);
        }
    }

    function saveDraft()
    {
        isSubmitEnabled.value = false;

        const resource = createQuestionnaire(thisMeta.value, thisItems.value, thisId);

        fhirUpdate("http://localhost:8080/fhir", resource).then(() => {
            // Do nothing?
        }).catch(() => {
            alert("Updating failed!")
        }).finally(() => {
            isSubmitEnabled.value = true;
        });
    }

    function finish(event)
    {
        event.preventDefault();

        isSubmitEnabled.value = false;

        isSubmitEnabled.value = true;
    }
</script>

<template>
    <section v-if="fetchPending">
        Fetching questionnaire...
    </section>
    <section v-else-if="fetchSuccess">
        <form @submit.capture="finish">
            <QuestionnaireMetaEdit
                v-model:title="thisMeta.title"
                v-model:name="thisMeta.name"
                v-model:version="thisMeta.version"
                v-model:description="thisMeta.description"
            />

            <section>
                <QuestionnaireItemEdit
                    v-for="(item, index) in thisItems"
                    :index="index"
                    v-model:text="item.text"
                    v-model:type="item.type"
                    v-model:options="item.options"
                    v-model:required="item.required"
                    @delete="deleteItem"
                    @move-up="moveUpItem"
                    @move-down="moveDownItem"
                />
                <input type="button" value="Add Item" @click="addItem" />
            </section>

            <input type="button" :disabled="!isSubmitEnabled" value="Save Draft" @click="saveDraft" />
            <input type="submit" :disabled="!isSubmitEnabled" value="Finish" />
        </form>
    </section>
    <section v-else>
        Unable to fetch questionnaire!
    </section>
</template>
