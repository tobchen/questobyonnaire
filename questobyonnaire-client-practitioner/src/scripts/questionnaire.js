const supportedTypes = new Set([
    "boolean",
    "decimal",
    "integer",
    "date",
    "dateTime",
    "time",
    "string",
    "text",
    "url",
    "choice",
    "open-choice",
]);

const choiceTypes = new Set([
    "choice",
    "open-choice",
]);

export function createMeta(status = "unknown")
{
    return {
        title: "",
        name: "",
        version: "",
        description: "",
        status,
    };
}

export function fillMeta(meta, questionnaire)
{
    meta.title = "title" in questionnaire ? questionnaire["title"] : "";
    meta.name = "name" in questionnaire ? questionnaire["name"] : "";
    meta.version = "version" in questionnaire ? questionnaire["version"] : "";
    meta.description = "description" in questionnaire ? questionnaire["description"] : "";
    meta.status = "status" in questionnaire ? questionnaire["status"] : "";
}

export function createItem()
{
    return {
        text: "",
        type: "",
        options: "",
        required: false,
    };
}

export function createResource(meta, items = null, id = null)
{
    // TODO Check empty fields
    const resource = {
        resourceType: "Questionnaire",
        status: "unknown",
    };

    if (meta.title.length > 0)
        resource["title"] = meta.title;

    if (meta.name.length > 0)
        resource["name"] = meta.name;

    if (meta.version.length > 0)
        resource["version"] = meta.version;

    if (meta.description.length > 0)
        resource["description"] = meta.description;

    if (meta.status.length > 0)
        resource["status"] = meta.status;

    if (items != null)
    {
        const resourceItems = new Array();

        for (const [index, item] of items.entries())
        {
            if (!supportedTypes.has(item.type))
                continue;
            
            const resourceItem = {
                linkId: `item-${index}`,
                text: item.text,
                type: item.type,
                required: item.required,
            };

            if (choiceTypes.has(item.type))
            {
                resourceItem["answerOption"] = item.options.split(",").map((x) => {
                    return {
                        valueString: x.trim()
                    };
                });
            }

            resourceItems.push(resourceItem);
        }

        if (resourceItems.length > 0)
            resource["item"] = resourceItems;
    }

    if (id !== null)
        resource["id"] = id;

    return resource;
}
