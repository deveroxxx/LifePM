Feature: Application Health Check

  Scenario: Verify application is healthy
    Given the application is running
    When I check the health status
    Then the application should be "UP"