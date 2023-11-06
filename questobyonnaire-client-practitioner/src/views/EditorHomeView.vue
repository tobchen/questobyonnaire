<script setup>
    import { ref } from "vue";
    import { useRouter } from "vue-router";
    import { create as fhirCreate, search as fhirSearch } from "../scripts/fhir";
    import { createMeta, createResource as createQuestionnaire } from "../scripts/questionnaire";
    import QuestionnaireMetaEdit from "../components/QuestionnaireMetaEdit.vue";

    const router = useRouter();

    const fetchPending = ref(true);
    const fetchSuccess = ref(false);

    const questionnaires = ref(new Array());

    const newMeta = ref(createMeta("draft"));

    const isSubmitEnabled = ref(true);

    fhirSearch("http://localhost:8080/fhir", "Questionnaire", { "status:not": "retired" }).then(bundle => {
        if ("entry" in bundle)
        {
            for (const entry of bundle["entry"])
            {
                if (!("resource" in entry))
                    continue;

                const resource = entry["resource"];

                questionnaires.value.push({
                    id: resource["id"],
                    title: "title" in resource ? resource["title"] : "Untitled",
                })
            }
        }

        fetchSuccess.value = true;
    }).catch(() => {
        // Do nothing
    }).finally(() => {
        fetchPending.value = false;
    });

    function newQuestionnaire(event)
    {
        event.preventDefault();

        isSubmitEnabled.value = false;
        
        const resource = createQuestionnaire(newMeta.value);

        fhirCreate("http://localhost:8080/fhir", resource).then(parsedLocation => {
            if (parsedLocation !== null)
            {
                router.push({ path: `/editor/${parsedLocation.id}` });
            }
            else
            {
                alert("Questionnaire created, location unknown, though.");
            }
        }).catch(() => {
            alert("Questionnaire creation failed!");
        }).finally(() => {
            isSubmitEnabled.value = true;
        });
    }
</script>

<template>
    <section v-if="fetchPending">
        Fetching questionnaires...
    </section>
    <section v-else-if="fetchSuccess">
        <ul>
            <li v-for="questionnaire in questionnaires">
                <router-link :to="`/editor/${questionnaire.id}`">
                    {{ questionnaire.title }}
                </router-link>
            </li>
        </ul>
    </section>
    <section v-else>
        Unable to fetch questionnaire!
    </section>

    <section>
        <form @submit.capture="newQuestionnaire">
            <QuestionnaireMetaEdit
                v-model:title="newMeta.title"
                v-model:name="newMeta.name"
                v-model:version="newMeta.version"
                v-model:description="newMeta.description"
            />

            <input type="submit" :disabled="!isSubmitEnabled" value="New Questionnaire" />
        </form>
    </section>
</template>
