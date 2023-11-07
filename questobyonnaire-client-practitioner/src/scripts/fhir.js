import { rejectNonOkResponse } from "./util"

export async function read(base, resourceType, id, vid = null)
{
    const url = `${base}/${resourceType}/${id}${vid !== null ? `/_history/${vid}` : ""}`;

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
        `?${typeof(parameters) === "string" ? parameters : new URLSearchParams(parameters)}` : ""}`;
    
    const response = await fetch(url, {
        method: "GET",
        headers: {
            "Accept": "application/fhir+json",
        }
    });

    return rejectNonOkResponse(response).json();
}
