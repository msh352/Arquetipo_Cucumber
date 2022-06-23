Feature: DEMO- SERQUO

  @search
  Scenario Outline: GET Some countries
    When I send a "<method>" simple request to the service "DUCKDUCKGO" for the endpoint "<endpoint_1>"
    Then the response should return status code "<code>"
    And the json schema should be "<schema>"
    And the response body should contain this value "<value>" in this path "<key>"
    When I send a "<method>" simple request to the service "WIKIPEDIA" for the endpoint "<endpoint_2>"
    Then the response should return status code "<code>"

    Examples:
      | method | key         | value                                | code | schema                     | endpoint_1             | endpoint_2   |  |
      | GET    | AbstractURL | https://en.wikipedia.org/wiki/Toledo | 200  | schema/search/GET-200.json | /?q=Toledo&format=json | /wiki/Toledo |  |
      | GET    | AbstractURL | https://en.wikipedia.org/wiki/Madrid | 200  | schema/search/GET-200.json | /?q=Madrid&format=json | /wiki/Madrid |  |

  @reqres
  Scenario Outline: Test POST arquetipo
    When I send a "<method>" request to the service "<service>" for the endpoint "<endpoint>" with the following json:
"""
    {
      "name": "<value_name>",
      "job": "<value_job>"
    }
    """
    Then the response should return status code "<code>"
    And the response body should contain this value "<value_name>" in this path "name"
    And the response body should contain this value "<value_job>" in this path "job"
    Examples:
      | method | endpoint   | service        | code | value_name | value_job |  |
      | POST   | /api/users | REQRES-SERVICE | 201  | morpheus   | leader    |  |

   # POST https://reqres.in/
  @reqres
  Scenario Outline: Test POST arquetipo - 400 error
    When I send a "<method>" request to the service "<service>" for the endpoint "<endpoint>" with the following json:
"""
    {
      "email": "sydney@fife"
    }
    """
    Then the response should return status code "<code>"
    And the response body should be this JSON:
"""
    {
      "error": "Missing password"
    }
    """
    Then the json schema should be "schema/reqres/POST-400-error.json"
    Then the json schema should be:
"""
    {
      "$schema": "http://json-schema.org/draft-04/schema#",
      "type": "object",
      "properties": {
        "error": {
          "type": "string"
        }
      },
      "required": [
        "error"
      ]
    }
    """
    Examples:
      | method | endpoint      | service        | code |  |  |  |
      | POST   | /api/register | REQRES-SERVICE | 400  |  |  |  |

  # PUT https://reqres.in/
  @reqres
  Scenario Outline: Test PUT arquetipo
    When I send a "<method>" request to the service "<service>" for the endpoint "<endpoint>" with the following json:
"""
    {
      "name": "<value_name>",
      "job": "<value_job>"
    }
    """
    Then the response should return status code "<code>"
    And the response body should contain this value "<value_name>" in this path "name"
    And the response body should contain this value "<value_job>" in this path "job"
    Examples:
      | method | endpoint     | service        | code | value_name  | value_job |  |
      | PUT    | /api/users/2 | REQRES-SERVICE | 200  | test_Miguel | leader    |  |


   # DELETE https://reqres.in/
  @reqres
  Scenario Outline: Test DELETE arquetipo
    When I send a "<method>" simple request to the service "<service>" for the endpoint "<endpoint>"
    Then the response should return status code "<code>"
    Examples:
      | method | endpoint          | service        | code |  |  |  |
      | DELETE | /api/users?page=2 | REQRES-SERVICE | 204  |  |  |  |