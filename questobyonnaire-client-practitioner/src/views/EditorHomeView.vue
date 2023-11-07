<script setup>
    import { ref } from "vue";
    import { useRouter } from "vue-router";
    import { createQuestionnaire, receiveAllQuestionnaires, sendNewQuestionnaire } from "../scripts/questionnaire";
    import QuestionnaireMetaEdit from "../components/QuestionnaireMetaEdit.vue";

    const router = useRouter();

    const fetchPending = ref(true);
    const fetchSuccess = ref(false);

    const questionnaireList = ref(null);

    const newQuestionnaire = ref(createQuestionnaire("draft"));

    const isSubmitEnabled = ref(true);

    receiveAllQuestionnaires("Questionnaire", { "status:not": "retired" }).then(questionnaires => {
        questionnaireList.value = questionnaires;

        fetchSuccess.value = true;
    }).catch(() => {
        // Do nothing
    }).finally(() => {
        fetchPending.value = false;
    });

    function newQuestionnaireClicked(event)
    {
        event.preventDefault();

        isSubmitEnabled.value = false;

        sendNewQuestionnaire(newQuestionnaire.value).then(questionnaire => {
            router.push({ path: `/editor/${questionnaire.id}` });
        }).catch(() => {
            alert("Questionnaire creation failed!");
        }).finally(() => {
            isSubmitEnabled.value = true;
        });
    }
</script>

<template>
    <section class="bg-emerald-500 rounded p-2">
        <h3 class="font-bold text-xl">Choose Questionnaire</h3>

        <div v-if="fetchPending">
            Fetching questionnaires...
        </div>
        <div v-else-if="fetchSuccess">
            <ul class="text-white">
                <li v-for="questionnaire in questionnaireList">
                    <router-link :to="`/editor/${questionnaire.id}`" class="hover:underline">
                        {{ questionnaire.title }}
                    </router-link>
                </li>
            </ul>
        </div>
        <div v-else>
            Unable to fetch questionnaire!
        </div>
    </section>

    <section class="bg-emerald-500 rounded mt-2 p-2">
        <h3 class="font-bold text-xl">New Questionnaire</h3>
        
        <form @submit.capture="newQuestionnaireClicked">
            <QuestionnaireMetaEdit
                v-model:title="newQuestionnaire.title"
                v-model:description="newQuestionnaire.description"
            />

            <input
                type="submit"
                :disabled="!isSubmitEnabled"
                value="New Questionnaire"
                class="block w-full bg-white cursor-pointer rounded mt-2"
            />
        </form>
    </section>
</template>
