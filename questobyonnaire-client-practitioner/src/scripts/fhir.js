function parseReference(reference)
{
    if (typeof(reference) !== "string")
    {
        return null;
    }

    const result = {
        type: null,
        id: null,
        vid: null,
    };

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

        result.type = data[data.length - 4];
        result.id = data[data.length - 3];
        result.vid = data[data.length - 1];
    }
    else
    {
        result.type = data[data.length - 2];
        result.id = data[data.length - 1];
    }

    return result;
}

export async function create(base, resource)
{
    if (typeof(resource) !== "object" || !("resourceType" in resource))
    {
        throw new TypeError("Bad resource");
    }

    const url = `${base}/${resource["resourceType"]}`;

    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/fhir+json",
        },
        body: JSON.stringify(resource),
    });

    const result = {
        success: response.ok,
        parsedLocation: null,
    };
    
    if (response.ok) {
        const location = response.headers.get("Location");
        if (location !== null) {
            result.parsedLocation = parseReference(location);
        }
    }

    return result;
}

export async function read(base, resourceType, id, vid = null)
{
    const url = `${base}/${resourceType}/${id}${vid !== null ? `/_history/${vid}` : ""}`;

    try
    {
        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Accept": "application/fhir+json",
            }
        });

        return response.ok ? response.json() : null;
    }
    catch
    {
        return null;
    }
}
