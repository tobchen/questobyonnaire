function parseReference(reference)
{
    if (typeof(reference) !== "string")
    {
        return null;
    }

    let type = null;
    let id = null;
    let vid = null;

    // XXX Regex? Are regex cool yet?
    const data = reference.split("/");

    if (data.length < 2)
    {
        return null;
    }

    if (data[data.length - 2] === "_history")
    {
        if (data.length < 4)
        {
            return null;
        }

        type = data[data.length - 4];
        id = data[data.length - 3];
        vid = data[data.length - 1];
    }
    else
    {
        type = data[data.length - 2];
        id = data[data.length - 1];
    }

    return [type, id, vid];
}

export async function create(base, resource)
{
    if (typeof(resource) !== "object" || !("resourceType" in resource))
    {
        throw "Resource isn't resource!";
    }

    const url = `${base}/${resource["resourceType"]}`;

    return await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/fhir+json"
        },
        body: JSON.stringify(resource),
    }).then(
        response => {
            if (response.status !== 201)
            {
                throw "Creation failed!";
            }

            const location = response.headers.get("Location");
            if (location === null)
            {
                throw "Location header missing!";
            }

            const parsedReference = parseReference(location);
            if (parsedReference === null)
            {
                throw "Location parsing failed!";
            }

            return parsedReference;
        },
        () => {
            throw "Posting failed!";
        }
    );
}
