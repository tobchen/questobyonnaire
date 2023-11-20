# Questobyonnaire Server

## FHIR

The server uses R5 resources.

### Logic

#### Patient

Only one set of names is searchable, prioritized as follows:

1. `official`
1. `usual`
1. First in list

For identifiers, only the actual medical record number (MRN) is searchable, submitted with type of code `MR` and system `http://terminology.hl7.org/CodeSystem/v2-0203`. If multiple MRNs are supplied, only the first one is taken. MRNs can only be set once.

#### Questionnaire

The server allows only the following status updates:

| Status | Next Status |
|---|---|
| `unknown` | `unknown`, `draft`, `active`, `retired` |
| `draft` | `draft`, `active`, `retired` |
| `active` | `active`, `retired` |
| `retired` | `retired` |

If the questionnaire is in either status `active` or `retired` no updates to its contents are registered (except for status updates).

Questionnaire [rules](https://www.hl7.org/fhir/questionnaire.html#invs) are not enforced yet.
