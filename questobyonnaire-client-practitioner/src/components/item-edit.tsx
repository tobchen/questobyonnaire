import { QuestionnaireItem, QuestionnaireItemConstraint, QuestionnaireItemType } from "@/lib/questionnaire";
import { ChangeEvent } from "react";

export default function ItemEdit({
    item,
    onTextChange,
    onTypeChange,
    onConstraintChange,
    onRequiredChange,
    onRepeatsChange,
    onMoveUp,
    onMoveDown,
    onDelete,
}: {
    item: QuestionnaireItem,
    onTextChange: (linkId: string, text: string) => void,
    onTypeChange: (linkId: string, type: QuestionnaireItemType) => void,
    onConstraintChange: (linkId: string, constraint: QuestionnaireItemConstraint) => void,
    onRequiredChange: (linkId: string, required: boolean) => void,
    onRepeatsChange: (linkId: string, repeats: boolean) => void,
    onMoveUp: (linkId: string) => void,
    onMoveDown: (linkId: string) => void,
    onDelete: (linkId: string) => void,
})
{
    function handleTextChange(event: ChangeEvent<HTMLInputElement>)
    {
        onTextChange(item.linkId, event.target.value);
    }

    function handleTypeChange(event: ChangeEvent<HTMLSelectElement>)
    {
        onTypeChange(item.linkId, event.target.value as QuestionnaireItemType);
    }

    function handleConstraintChange(event: ChangeEvent<HTMLSelectElement>)
    {
        onConstraintChange(item.linkId, event.target.value as QuestionnaireItemConstraint);
    }

    function handleRequiredChange(event: ChangeEvent<HTMLInputElement>)
    {
        onRequiredChange(item.linkId, event.target.checked);
    }

    function handleRepeatsChange(event: ChangeEvent<HTMLInputElement>)
    {
        onRepeatsChange(item.linkId, event.target.checked);
    }

    function handleMoveUp()
    {
        onMoveUp(item.linkId);
    }

    function handleMoveDown()
    {
        onMoveDown(item.linkId);
    }

    function handleDelete()
    {
        onDelete(item.linkId);
    }

    return (
        <fieldset className="mt-2 p-2 rounded border-2 border-white">
            <input
                type="text"
                value={item.text !== undefined ? item.text : ""}
                placeholder="Question"
                onChange={handleTextChange}
                className="block w-full text-xl placeholder:text-emerald-500"
            />

            <select
                value={item.type}
                onChange={handleTypeChange}
                className="block w-full mt-2 p-1 bg-white"
            >
                <option value="boolean">Yes/No</option>
                <option value="decimal">Decimal</option>
                <option value="integer">Integer</option>
                <option value="date">Date</option>
                <option value="dateTime">Date & Time</option>
                <option value="time">Time</option>
                <option value="string">Text</option>
                <option value="text">Long Text</option>
                <option value="url">URL</option>
            </select>

            <select
                value={item.answerConstraint !== undefined ? item.answerConstraint : "optionsOnly"}
                onChange={handleConstraintChange}
                className="inline-block mt-2 mr-2 p-1 bg-white"
            >
                <option value="optionsOnly">Options Only</option>
                <option value="optionsOrType">Options or Type</option>
                <option value="optionsOrString">Options or Text</option>
            </select>

            <label className="inline-block mt-2 mr-2">
                <input
                    type="checkbox"
                    checked={item.required !== undefined ? item.required : false}
                    onChange={handleRequiredChange}
                    className="mr-2"
                />
                Required
            </label>

            <label className="inline-block mt-2">
                <input
                    type="checkbox"
                    checked={item.repeats !== undefined ? item.repeats : false}
                    onChange={handleRepeatsChange}
                    className="mr-2"
                />
                Multiple
            </label>

            <fieldset className="mt-2 grid grid-cols-3 gap-2">
                <input
                    type="button"
                    value="Move Up"
                    onClick={handleMoveUp}
                    className="rounded bg-white cursor-pointer"
                />
                <input
                    type="button"
                    value="Move Down"
                    onClick={handleMoveDown}
                    className="rounded bg-white cursor-pointer"
                />
                <input
                    type="button"
                    value="Delete"
                    onClick={handleDelete}
                    className="rounded bg-white cursor-pointer"
                />
            </fieldset>
        </fieldset>
    );
}