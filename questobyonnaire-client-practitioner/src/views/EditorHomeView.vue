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
    <section class="p-2 rounded bg-emerald-500">
        <h2 class="text-xl font-bold">Choose Questionnaire to Edit</h2>

        <div v-if="fetchPending">
            Fetching questionnaires...
        </div>
        <div v-else-if="fetchSuccess">
            <ul class="text-white">
                <li v-for="questionnaire in questionnaireList">
                    <router-link :to="`/editor/${questionnaire.id}`" class="hover:underline">
                        {{ questionnaire.title.length > 0 ? questionnaire.title : "!Untitled!" }}
                    </router-link>
                </li>
            </ul>
        </div>
        <div v-else>
            Unable to fetch questionnaire!
        </div>
    </section>

    <section class="mt-2 p-2 rounded bg-emerald-500">
        <h2 class="text-xl font-bold">New Questionnaire</h2>
        
        <form @submit.capture="newQuestionnaireClicked">
            <QuestionnaireMetaEdit
                v-model:title="newQuestionnaire.title"
                v-model:description="newQuestionnaire.description"
            />

            <input
                type="submit"
                :disabled="!isSubmitEnabled"
                value="New Questionnaire"
                class="block w-full mt-2 rounded bg-white disabled:opacity-50 enabled:cursor-pointer"
            />
        </form>
    </section>
</template>
