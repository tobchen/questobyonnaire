<script setup>
    import { ref } from "vue";
    import { useRouter } from "vue-router";
    import QuestionnaireMetaEdit from '../components/QuestionnaireMetaEdit.vue';
    import { create as fhirCreate } from "../scripts/fhir";
    import { createMeta, createResource as createQuestionnaire } from "../scripts/questionnaire";

    const router = useRouter();

    const newMeta = ref(createMeta("draft"));

    const isSubmitEnabled = ref(true);

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
