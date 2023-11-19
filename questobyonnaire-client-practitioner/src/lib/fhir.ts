export interface Resource
{
    resourceType: string;
    id?: string;
}

export async function fhirCreate(base: string, resource: Resource)
{
    const url = `${base}/${resource.resourceType}`;

    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/fhir+json",
        },
        body: JSON.stringify(resource),
    });

    if (!response.ok)
    {
        throw "Failed to create";
    }

    const location = response.headers.get("Location");
    if (location === null)
    {
        throw "No location returned";
    }

    let id: string | undefined = undefined;

    const data = location.split("/");
    if (data.length >= 5 && data[data.length - 2] === "_history")
    {
        id = data[data.length - 3];
    }
    else if (data.length >= 3)
    {
        id = data[data.length - 1];
    }

    if (id !== undefined)
    {
        return id;
    }
    else
    {
        throw "No id returned";
    }
}

export async function fhirRead<T extends Resource>(base: string, type: string, id: string)
{
    const url = `${base}/${type}/${id}`;

    const response = await fetch(url, {
        method: "GET",
        headers: {
            "Accept": "application/fhir+json",
        }
    });

    if (!response.ok)
    {
        throw "Failed to read";
    }

    return await response.json() as T;
}

export async function fhirSearch<T extends Resource>(base: string, type: string, params?: URLSearchParams)
{
    const url = `${base}/${type}${params !== undefined ? `?${params}` : ""}`;

    const response = await fetch(url, {
        method: "GET",
        headers: {
            "Accept": "application/fhir+json",
        }
    });

    if (!response.ok)
    {
        throw "Failed to search";
    }

    const result = new Array<T>();

    const bundle = await response.json();
    if (bundle.entry !== undefined)
    {
        for (const entry of bundle.entry)
        {
            if (entry.resource !== undefined)
            {
                result.push(entry.resource as T);
            }
        }
    }

    return result;
}
