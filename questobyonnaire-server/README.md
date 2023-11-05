# Questobyonnaire Server

## Development Notes

### To Do

* FHIR Endpoint
  * Resources
    * Patient
      * Read
      * Search
        * Family
        * Given
        * Birthdate
        * Identifier
      * Update
        * Update as Create
      * Handle Patient Merge
    * Questionnaire
      * Update
        * Check Status Against Previous Version
    * QuestionnaireResponse
      * Read
      * Search
      * Create
      * Update
      * Check Against Questionnaire
    * Subscription
      * Search
  * Subscription Notification (targets defined in *application.yaml*)
  * Authentication
  * Authorization
  * CORS with Specific Origins
* REST Endpoint
  * Master Username & Password
  * User management
    * Scope
* Identity Provision Endpoint
  * QuestionnaireResponse JWT
  * CORS
