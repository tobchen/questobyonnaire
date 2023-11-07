import { rejectNonOkResponse } from "./util";

const baseUrl = "http://localhost:8080/rest/questionnaires"

export function createQuestionnaire(status = "draft")
{
    return {
        title: "",
        description: "",
        status,
        items: new Array(),
    };
}

export function createQuestionnaireItem()
{
    return {
        text: "",
        type: "text",
        required: false,
        multiple: false,
        options: "",
    };
}

export async function sendNewQuestionnaire(questionnaire)
{
    const response = await fetch(baseUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(questionnaire),
    });

    return rejectNonOkResponse(response).json();
}

export async function sendUpdatedQuestionnaire(questionnaire)
{
    if (questionnaire.id === undefined)
    {
        throw new TypeError("Id missing!");
    }

    const url = `${baseUrl}/${questionnaire.id}`;

    const response = await fetch(url, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(questionnaire),
    });

    return rejectNonOkResponse(response).json();
}

export async function receiveAllQuestionnaires(parameters = null)
{
    const url = `${baseUrl}${parameters !== null ?
        `?${typeof(parameters) === "string" ? parameters : new URLSearchParams(parameters)}` : ""}`;

    const response = await fetch(url, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        }
    });

    return rejectNonOkResponse(response).json();
}

export async function receiveQuestionnaire(id)
{
    const url = `${baseUrl}/${id}`;

    const response = await fetch(url, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        }
    });

    return rejectNonOkResponse(response).json();
}
