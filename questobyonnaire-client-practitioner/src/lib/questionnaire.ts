// TODO Regex for date

import { Resource } from "./fhir";

export type QuestionnaireItemType = "boolean" | "decimal" | "integer" | "date" | "dateTime" | "time" |
    "string" | "text" | "url";
export type QuestionnaireItemConstraint = "optionsOnly" | "optionsOrType" | "optionsOrString";
export type QuestionnaireStatus = "draft" | "active" | "retired" | "unknown";

// TODO Regex for time
export interface QuestionnaireItemOption
{
    valueInteger?: number;
    valueDate?: string;
    valueTime?: string;
    valueString?: string;
}

export interface QuestionnaireItem
{
    linkId: string;
    text?: string;
    type: QuestionnaireItemType;
    required?: boolean;
    repeats?: boolean;
    answerConstraint?: QuestionnaireItemConstraint;
    answerOption?: QuestionnaireItemOption[];
}

export interface Questionnaire extends Resource
{
    resourceType: "Questionnaire";
    title?: string;
    status: QuestionnaireStatus;
    description?: string;
    item?: QuestionnaireItem[];
}
