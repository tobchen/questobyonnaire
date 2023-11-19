import { Questionnaire } from "@/lib/questionnaire";
import { ChangeEvent } from "react";

export default function MetaEdit({
    questionnaire,
    onTitleChange,
    onDescriptionChange,
}: {
    questionnaire: Questionnaire,
    onTitleChange: (title: string) => void,
    onDescriptionChange: (description: string) => void,
}) {
    function handleTitleChange(event: ChangeEvent<HTMLInputElement>)
    {
        onTitleChange(event.target.value);
    }

    function handleDescriptionChange(event: ChangeEvent<HTMLTextAreaElement>)
    {
        onDescriptionChange(event.target.value);
    }

    return (
        <fieldset>
            <label className="block">
                Title:
                <input
                    type="text"
                    value={questionnaire.title !== undefined ? questionnaire.title : ""}
                    onChange={handleTitleChange}
                    className="block w-full text-xl"
                />
            </label>

            <label className="block">
                Description:
                <textarea
                    value={questionnaire.description !== undefined ? questionnaire.description : ""}
                    onChange={handleDescriptionChange}
                    className="block resize-none w-full h-24"
                ></textarea>
            </label>
        </fieldset>
    );
}