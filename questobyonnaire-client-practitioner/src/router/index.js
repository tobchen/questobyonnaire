import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import EditorView from "../views/EditorView.vue";
import EditorHomeView from "../views/EditorHomeView.vue";
import EditorQuestionnaireView from "../views/EditorQuestionnaireView.vue";

export default createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "home",
            component: HomeView,
        },
        {
            path: "/editor",
            name: "editor",
            component: EditorView,
            children: [
                {
                    path: "",
                    name: "editor-home",
                    component: EditorHomeView,
                },
                {
                    path: ":id",
                    name: "editor-questionnaire",
                    component: EditorQuestionnaireView,
                },
            ],
        },
    ],
});
