<script setup>
    import { ref } from "vue";
    import { useRoute } from "vue-router";
    import { read as fhirRead } from "../scripts/fhir";
    import QuestionnaireMetaEdit from '../components/QuestionnaireMetaEdit.vue';

    const route = useRoute();

    const fetchPending = ref(true);
    const fetchSuccess = ref(false);

    const thisTitle = ref("");
    const thisName = ref("");
    const thisVersion = ref("");
    const thisDescription = ref("");

    fhirRead("http://localhost:8080/fhir", "Questionnaire", route.params.id).then(resource => {
        if (resource !== null)
        {
            fetchSuccess.value = true;

            thisTitle.value = "title" in resource ? resource["title"] : "";
            thisName.value = "name" in resource ? resource["name"] : "";
            thisVersion.value = "version" in resource ? resource["version"] : "";
            thisDescription.value = "description" in resource ? resource["description"] : "";
        }

        fetchPending.value = false;
    });
</script>

<template>
    <section v-if="fetchSuccess">
        <form>
            <QuestionnaireMetaEdit
                v-model:title="thisTitle"
                v-model:name="thisName"
                v-model:version="thisVersion"
                v-model:description="thisDescription"
            />
        </form>
    </section>
    <section v-else-if="fetchPending">
        Fetching questionnaire...
    </section>
    <section v-else>
        Unable to fetch questionnaire!
    </section>
</template>
