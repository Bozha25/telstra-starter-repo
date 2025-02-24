Feature: SIM Card Activation
  As a user
  I want to activate a SIM card
  So that I can use mobile services

  Scenario: Successful SIM card activation
    Given the SIM card activation service is running
    When I submit an activation request with ICCID "1255789453849037777"
    Then the activation should be successful
    And I should be able to query the activation status as "active"

  Scenario: Failed SIM card activation
    Given the SIM card activation service is running
    When I submit an activation request with ICCID "8944500102198304826"
    Then the activation should fail
    And I should be able to query the activation status as "inactive"