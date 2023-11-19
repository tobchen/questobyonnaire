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

export function correctQuestionnaireItemOptions(questionnaire: Questionnaire): Questionnaire
{
    return {
        ...questionnaire,
        item: questionnaire.item === undefined ? undefined : questionnaire.item.map(item => {
            const options = new Array<QuestionnaireItemOption>();

            if (item.answerOption !== undefined)
            {
                for (const option of item.answerOption)
                {
                    let valueString: string | null = null;
                    for (const [key, value] of Object.entries(option))
                    {
                        if (key.startsWith("value"))
                        {
                            valueString = String(value);
                            break;
                        }
                    }

                    if (valueString !== null)
                    {
                        switch (item.type)
                        {
                        case "integer":
                            options.push({
                                valueInteger: parseInt(valueString),
                            });
                            break;
                        case "date":
                            options.push({
                                valueDate: valueString,
                            });
                            break;
                        case "time":
                            options.push({
                                valueTime: valueString,
                            });
                            break;
                        case "string":
                            options.push({
                                valueString,
                            });
                            break;
                        default:
                            // Nothing
                        }
                    }
                }
            }

            return {
                ...item,
                answerOption: options.length > 0 ? options : undefined,
            }
        }),
    };
}
