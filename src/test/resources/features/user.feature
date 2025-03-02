Feature: User related operations

  Scenario: User can register then log in
    Given No user exists with username "User A"
    When User register with user name: "User_A", password: "pass" and email: "test@tes.com"
    Then User logs in with user name: "User_A", password: "pass"