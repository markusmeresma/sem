# USE CASE 5: Delete employee's details
## CHARACTERISTIC INFORMATION
### Goal in Context

As an *HR advisor* I want to delete employee's details so that company is compliant with data retention legislation.

### Scope

Company.

### Level

Primary task

### Preconditions

HR advisor has the details of the employee. Database contains current employee salary data.

### Success End Condition

Details of an employee are deleted successfully.

### Failed End Condition

Deleting details of an employee fails.

### Primary Actor

HR Advisor.

### Trigger

An employee leaves the company.

## MAIN SUCCESS SCENARIO

1. An employee leaves the company.
2. HR advisors removes the employee from the system successfully.

## EXTENSIONS

**Missing employee information:**
- HR Advisor has to collect sufficient data about the employee to add them to the system

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE:** Release 0.1.0.5