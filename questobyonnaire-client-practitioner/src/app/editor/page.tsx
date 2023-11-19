"use client";

import MetaEdit from "@/components/meta-edit"
import { SyntheticEvent, useEffect, useState } from "react"
import { Questionnaire } from "@/lib/questionnaire";
import { fhirCreate, fhirSearch } from "@/lib/fhir";
import { useRouter } from "next/navigation";
import Link from "next/link";

export default function Editor()
{
    const router = useRouter();

    const [fetchPending, setFetchPending] = useState(true);
    const [questionnaires, setQuestionnaires] = useState<Questionnaire[] | null>(null);

    const [questionnaire, setQuestionnaire] = useState<Questionnaire>({
        resourceType: "Questionnaire",
        status: "draft",
    });

    const [submitDisabled, setSubmitDisabled] = useState(false);

    function handleTitleChange(title: string)
    {
        setQuestionnaire({
            ...questionnaire,
            title,
        })
    }

    function handleDescriptionChange(description: string)
    {
        setQuestionnaire({
            ...questionnaire,
            description,
        })
    }

    async function handleSubmit(event: SyntheticEvent)
    {
        event.preventDefault();

        setSubmitDisabled(true);

        let id: string | undefined = undefined;
        try
        {
            id = await fhirCreate(process.env.NEXT_PUBLIC_FHIR_BASE as string, questionnaire);
        }
        catch (error)
        {
            alert(error);
        }

        setSubmitDisabled(false);

        if (id !== undefined)
        {
            router.push(`/editor/${id}`);
        }
    }

    let content;
    if (fetchPending)
    {
        content = <span>Fetching questionnaires...</span>
    }
    else if (questionnaires !== null)
    {
        content = <ul className="text-white">
            {questionnaires.map(questionnaire => {
                return <li key={questionnaire.id}>
                    <Link href={`/editor/${questionnaire.id}`} className="font-bold hover:underline">
                        {questionnaire.title !== undefined ? questionnaire.title : "!Untitled!"}
                    </Link>
                </li>;
            })}
        </ul>;
    }
    else
    {
        content = <span>Unable to fetch questionnaires!</span>
    }

    useEffect(() => {
        async function startFetching()
        {
            let fetchedQuestionnaires = null;

            try
            {
                fetchedQuestionnaires = await fhirSearch<Questionnaire>(
                    process.env.NEXT_PUBLIC_FHIR_BASE as string,
                    "Questionnaire", new URLSearchParams({"status:not": "retired"}));
            }
            catch (error)
            {
                alert(error);
            }

            setQuestionnaires(fetchedQuestionnaires);
            setFetchPending(false);
        }

        startFetching();
    }, []);

    return (
        <main>
            <section className="p-2 rounded bg-emerald-500">
                <h2 className="text-xl font-bold">Choose Questionnaire to Edit</h2>

                {content}
            </section>

            <section className="mt-2 p-2 rounded bg-emerald-500">
                <h2 className="text-xl font-bold">New Questionnaire</h2>

                <form onSubmit={handleSubmit}>
                    <MetaEdit
                        questionnaire={questionnaire}
                        onTitleChange={handleTitleChange}
                        onDescriptionChange={handleDescriptionChange}
                    />

                    <input
                        type="submit"
                        value="New Questionnaire..."
                        disabled={submitDisabled}
                        className="block w-full mt-2 rounded bg-white disabled:opacity-50 enabled:cursor-pointer"
                    />
                </form>
            </section>
        </main>
    )
}
