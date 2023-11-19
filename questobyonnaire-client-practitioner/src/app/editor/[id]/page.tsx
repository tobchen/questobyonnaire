"use client";

import ItemEdit from "@/components/item-edit";
import MetaEdit from "@/components/meta-edit";
import { fhirRead } from "@/lib/fhir";
import { Questionnaire, QuestionnaireItem, QuestionnaireItemConstraint, QuestionnaireItemType } from "@/lib/questionnaire";
import { useEffect, useState } from "react";

export default function QuestionnaireEditor({
    params,
}:{
    params: {
        id: string,
    },
})
{
    const [fetchPending, setFetchPending] = useState(true);
    const [questionnaire, setQuestionnaire] = useState<Questionnaire | null>(null);

    const [postPending, setPostPending] = useState(false);

    let content;
    if (fetchPending)
    {
        content = <span>Fetching questionnaire...</span>;
    }
    else if (questionnaire !== null)
    {
        const handleTitleChange = (title: string) => {
            setQuestionnaire({
                ...questionnaire,
                title,
            })
        };

        const handleDescriptionChange = (description: string) => {
            setQuestionnaire({
                ...questionnaire,
                description,
            })
        };

        const handleAddItem = () => {
            const items = questionnaire.item !== undefined ?
                questionnaire.item : new Array<QuestionnaireItem>();
            
            items.push({
                linkId: crypto.randomUUID(),
                type: "string",
                answerConstraint: "optionsOrType",
            });

            setQuestionnaire({
                ...questionnaire,
                item: items
            })
        };

        const handleItemTextChange = (linkId: string, text: string) => {
            if (questionnaire.item !== undefined)
            {
                setQuestionnaire({
                    ...questionnaire,
                    item: questionnaire.item.map(item => {
                        return {
                            ...item,
                            text: item.linkId === linkId ? text : item.text,
                        };
                    })
                })
            }
        };

        const handleItemTypeChange = (linkId: string, type: QuestionnaireItemType) => {
            if (questionnaire.item !== undefined)
            {
                setQuestionnaire({
                    ...questionnaire,
                    item: questionnaire.item.map(item => {
                        return {
                            ...item,
                            type: item.linkId === linkId ? type : item.type,
                        };
                    })
                })
            }
        };

        const handleItemConstraintChange = (linkId: string, constraint: QuestionnaireItemConstraint) => {
            if (questionnaire.item !== undefined)
            {
                setQuestionnaire({
                    ...questionnaire,
                    item: questionnaire.item.map(item => {
                        return {
                            ...item,
                            answerConstraint: item.linkId === linkId ? constraint : item.answerConstraint,
                        };
                    })
                })
            }
        };

        const handleItemRequiredChange = (linkId: string, required: boolean) => {
            if (questionnaire.item !== undefined)
            {
                setQuestionnaire({
                    ...questionnaire,
                    item: questionnaire.item.map(item => {
                        return {
                            ...item,
                            required: item.linkId === linkId ? required : item.required,
                        };
                    })
                })
            }
        };

        const handleItemRepeatsChange = (linkId: string, repeats: boolean) => {
            if (questionnaire.item !== undefined)
            {
                setQuestionnaire({
                    ...questionnaire,
                    item: questionnaire.item.map(item => {
                        return {
                            ...item,
                            repeats: item.linkId === linkId ? repeats : item.repeats,
                        };
                    })
                })
            }
        };

        const handleMoveUp = (linkId: string) => {
            if (questionnaire.item !== undefined)
            {
                const index = questionnaire.item.findIndex(item => item.linkId === linkId);
                if (index >= 1)
                {
                    const newItems = questionnaire.item.toSpliced(index, 1);
                    newItems.splice(index - 1, 0, questionnaire.item[index]);
                    setQuestionnaire({
                        ...questionnaire,
                        item: newItems,
                    })
                }
            }
        };

        const handleMoveDown = (linkId: string) => {
            if (questionnaire.item !== undefined)
            {
                const index = questionnaire.item.findIndex(item => item.linkId === linkId);
                if (index >= 0 && index < questionnaire.item.length - 1)
                {
                    const newItems = questionnaire.item.toSpliced(index, 1);
                    newItems.splice(index + 1, 0, questionnaire.item[index]);
                    setQuestionnaire({
                        ...questionnaire,
                        item: newItems,
                    })
                }
            }
        };

        const handleDelete = (linkId: string) => {
            if (questionnaire.item !== undefined)
            {
                setQuestionnaire({
                    ...questionnaire,
                    item: questionnaire.item.filter(item => item.linkId !== linkId),
                });
            }
        };

        content = <form>
            <MetaEdit
                questionnaire={questionnaire}
                onTitleChange={handleTitleChange}
                onDescriptionChange={handleDescriptionChange}
            />

            <fieldset>
                {questionnaire.item !== undefined ?
                    questionnaire.item.map(item =>
                    <ItemEdit
                        key={item.linkId}
                        item={item}
                        onTextChange={handleItemTextChange}
                        onTypeChange={handleItemTypeChange}
                        onConstraintChange={handleItemConstraintChange}
                        onRequiredChange={handleItemRequiredChange}
                        onRepeatsChange={handleItemRepeatsChange}
                        onMoveUp={handleMoveUp}
                        onMoveDown={handleMoveDown}
                        onDelete={handleDelete}
                    />) : null}

                <input
                    type="button"
                    value="Add Item"
                    onClick={handleAddItem}
                    className="block w-full mt-2 rounded bg-white cursor-pointer"
                />
            </fieldset>

            <fieldset className="mt-2 grid grid-cols-3 gap-2">
                <input
                    type="button"
                    value="Save Draft"
                    disabled={postPending || questionnaire.status !== "draft"}
                    className="rounded bg-white disabled:opacity-50 enabled:cursor-pointer"
                />
                <input
                    type="submit"
                    value="Publish"
                    disabled={postPending || questionnaire.status !== "draft"}
                    className="rounded bg-white disabled:opacity-50 enabled:cursor-pointer"
                />
                <input
                    type="button"
                    value="Retire"
                    disabled={postPending || questionnaire.status === "retired"}
                    className="rounded bg-white disabled:opacity-50 enabled:cursor-pointer"
                />
            </fieldset>
        </form>;
    }
    else
    {
        content = <span>Unable to fetch questionnaire!</span>;
    }

    useEffect(() => {
        async function startFetching()
        {
            let fetchedQuestionnaire = null;

            try
            {
                fetchedQuestionnaire = await fhirRead<Questionnaire>(process.env.NEXT_PUBLIC_FHIR_BASE as string,
                    "Questionnaire", params.id);
            }
            catch (error)
            {
                alert(error);
            }

            setQuestionnaire(fetchedQuestionnaire);
            setFetchPending(false);
        }

        startFetching();
    }, [params.id]);

    return (
        <main>
            <section className="p-2 rounded bg-emerald-500">
                <h2 className="text-xl font-bold">Edit Questionnaire</h2>

                {content}
            </section>
        </main>
    )
}
