import "./main.css"

import { createApp } from "vue";
import { createRouter, createWebHistory } from "vue-router";
import App from "./App.vue";
import HomeView from "./views/HomeView.vue";
import EditorView from "./views/EditorView.vue";
import EditorHomeView from "./views/EditorHomeView.vue";
import EditorQuestionnaireView from "./views/EditorQuestionnaireView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "home",
            component: HomeView,
        },
        {
            path: "/editor",
            name: "editor-home",
            component: EditorHomeView,
        },
        {
            path: "/editor/:id",
            name: "editor-questionnaire",
            component: EditorQuestionnaireView,
        },
    ],
});

const app = createApp(App);

app.use(router);

app.mount("#app");
