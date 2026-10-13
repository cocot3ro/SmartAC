import { camelizeKeys, decamelize, decamelizeKeys } from "humps";
import queryString, { type StringifiableRecord } from "query-string";

const contentTypeHeader = "Content-Type";

interface BuildUrlArgs {
    endpoint: string;
    params?: StringifiableRecord;
}

function decamelizeParams(params?: StringifiableRecord): StringifiableRecord | undefined {
    if (!params) {
        return undefined;
    }
    const result: StringifiableRecord = {};
    for (const [ key, value ] of Object.entries(params)) {
        result[decamelize(key)] = value;
    }

    return result;
}

function buildUrl({ endpoint, params }: BuildUrlArgs) {
    return queryString.stringifyUrl({
        url: endpoint,
        query: decamelizeParams(params)
    });
}

function buildBody(data?: Record<string, any>): string | undefined {
    if (data) {
        return JSON.stringify(decamelizeKeys(data));
    }
}

async function processResponse(response: Response) {
    if (!response.ok) {
        // if (response.status === 401) {
            // Force logout user and reload the page if Unauthorized
            // console.log("Unauthorized");
            // window.location.reload();
        // }

        throw new Error(response.status + " " + response.statusText);
    }

    const payload = await response.json();
    return camelizeKeys(payload) as any;
}

interface GetArgs {
    url: string;
    params?: queryString.StringifiableRecord;
}

async function baseGet({ url, params }: GetArgs, abortController?: AbortController) {
    const apiUrl = buildUrl({ endpoint: url, params });
    const method = "GET";
    const headers = {};
    const signal = abortController?.signal;
    return await fetch(apiUrl, { method, headers, signal });
}

export async function get(args: GetArgs, abortController?: AbortController) {
    return processResponse(await baseGet(args, abortController));
}

interface PostArgs {
    url: string;
    params?: queryString.StringifiableRecord;
    data?: any;
    noAuth?: boolean;
}

export async function post({ url, params, data, noAuth }: PostArgs, abortController?: AbortController) {
    const apiUrl = buildUrl({ endpoint: url, params });
    const method = "POST";

    let headers: Record<string, string> = {};
    if (!noAuth) {
        headers = {};
    }

    let body: string | FormData | undefined;
    // Check if the data is an instance of FormData
    // If data is FormData, let the browser set the Content-Type header
    if (data instanceof FormData) {
        body = data;
    } else {
        // If data is JSON, set the Content-Type header to 'application/json'
        headers = {
            ...headers,
            [contentTypeHeader]: "application/json"
        };
        body = buildBody(data);
    }

    const signal = abortController?.signal;
    const response = await fetch(apiUrl, { method, headers, body, signal });
    return processResponse(response);
}

interface PutArgs {
    url: string;
    params?: queryString.StringifiableRecord;
    data?: Record<string, any>;
}

export async function put({ url, params, data }: PutArgs, abortController?: AbortController) {
    const apiUrl = buildUrl({ endpoint: url, params });
    const method = "PUT";
    const headers = {
        [contentTypeHeader]: "application/json"
    };
    const signal = abortController?.signal;
    const body = buildBody(data);
    const response = await fetch(apiUrl, { method, headers, body, signal });
    return processResponse(response);
}

interface DeleteArgs {
    url: string;
    params?: queryString.StringifiableRecord;
}

export async function del({ url, params }: DeleteArgs, abortController?: AbortController) {
    const apiUrl = buildUrl({ endpoint: url, params });
    const method = "DELETE";
    const headers = {};
    const signal = abortController?.signal;
    const response = await fetch(apiUrl, { method, headers, signal });
    return processResponse(response);
}
