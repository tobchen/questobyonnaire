<script setup>
    import { ref } from "vue";
    import { useRouter } from 'vue-router';
    import QuestionnaireMetaEdit from '../components/QuestionnaireMetaEdit.vue';
    import { create as fhirCreate } from "../scripts/fhir";

    const router = useRouter();

    const newTitle = ref("");
    const newName = ref("");
    const newVersion = ref("");
    const newDescription = ref("");

    async function newQuestionnaire(event)
    {
        event.preventDefault();

        // TODO Disable re-submit
        
        // TODO Check empty fields
        const resource = {
            "resourceType": "Questionnaire",
            "title": newTitle.value,
            "name": newName.value,
            "version": newVersion.value,
            "description": newDescription.value,
            "status": "draft"
        };

        const newId = (await fhirCreate("http://localhost:8080/fhir", resource))[1];

        router.push({ path: `/editor/${newId}` });
    }
</script>

<template>
    <section>
        <form @submit.capture="newQuestionnaire">
            <QuestionnaireMetaEdit
                v-model:title="newTitle"
                v-model:name="newName"
                v-model:version="newVersion"
                v-model:description="newDescription"
            />

            <input type="submit" value="New Questionnaire" />
        </form>
    </section>
</template>
