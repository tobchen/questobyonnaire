<script setup>
    import { ref } from "vue";
    import { useRoute } from "vue-router";
    import { read as fhirRead } from "../scripts/fhir";
    import QuestionnaireMetaEdit from "../components/QuestionnaireMetaEdit.vue";
    import QuestionnaireItemEdit from "../components/QuestionnaireItemEdit.vue";

    const route = useRoute();

    const fetchPending = ref(true);
    const fetchSuccess = ref(false);

    const isSubmitEnabled = ref(true);

    const thisTitle = ref("");
    const thisName = ref("");
    const thisVersion = ref("");
    const thisDescription = ref("");

    const theseItems = ref(new Array());

    fhirRead("http://localhost:8080/fhir", "Questionnaire", route.params.id).then(resource => {
        if (resource !== null)
        {
            fetchSuccess.value = true;

            thisTitle.value = "title" in resource ? resource["title"] : "";
            thisName.value = "name" in resource ? resource["name"] : "";
            thisVersion.value = "version" in resource ? resource["version"] : "";
            thisDescription.value = "description" in resource ? resource["description"] : "";

            // TODO Add Items
        }

        fetchPending.value = false;
    });

    function addItem()
    {
        theseItems.value.push({
            text: "",
            type: "",
            choices: "",
            required: false,
        });
    }

    function saveDraft()
    {
        isSubmitEnabled.value = false;

        isSubmitEnabled.value = true;
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
                v-model:title="thisTitle"
                v-model:name="thisName"
                v-model:version="thisVersion"
                v-model:description="thisDescription"
            />

            <section>
                <QuestionnaireItemEdit
                    v-for="item in theseItems"
                    v-model:text="item.text"
                    v-model:type="item.type"
                    v-model:choices="item.choices"
                    v-model:required="item.required"
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
