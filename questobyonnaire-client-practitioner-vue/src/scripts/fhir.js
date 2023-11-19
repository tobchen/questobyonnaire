import { rejectNonOkResponse } from "./util"

export async function read(base, resourceType, id)
{
    const url = `${base}/${resourceType}/${id}`;

    const response = await fetch(url, {
        method: "GET",
        headers: {
            "Accept": "application/fhir+json",
        }
    });

    return rejectNonOkResponse(response).json();
}

export async function search(base, resourceType, parameters = null)
{
    const url = `${base}/${resourceType}${parameters !== null ?
        `?${new URLSearchParams(parameters)}` : ""}`;
    
    const response = await fetch(url, {
        method: "GET",
        headers: {
            "Accept": "application/fhir+json",
        }
    });

    return rejectNonOkResponse(response).json();
}
