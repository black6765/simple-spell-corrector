# simple-spell-corrector
This project performs spell correction for user-inputted keywords using an HTTP API.

The spell correction algorithm is based on **SymSpell**, leveraging frequency-based Korean/English dictionaries to generate correction candidates.

For example, given the input `dictlonary` (intended to mean "list of words"), the API provides correction suggestions `dictionary`.

## Request and Response Example
### Request
```
${host}?keyword=dictlonary
```

### Response
```json
{
    "originKeyword" : "dictlonary",
    "correctedKeyword" : "dictionary",
    "corrected" : true
}
```
- originKeyword: The original keyword provided in the request 
- correctedKeyword: The result of spell correction performed by the spell corrector
- corrected: Indicates whether spell correction was performed