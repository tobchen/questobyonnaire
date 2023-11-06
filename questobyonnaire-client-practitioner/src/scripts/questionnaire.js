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

    if (id !== null)
        resource["id"] = id;

    return resource;
}
