# USE CASE 5: View employees details
## CHARACTERISTIC INFORMATION
### Goal in Context

As an *HR advisor* I want to view employee's details so that employee's promotion request can be supported.

### Scope

Company.

### Level

Primary task

### Preconditions

HR advisor has the details of the employee. Database contains current employee salary data.

### Success End Condition

A report on the details of an employee is produced.

### Failed End Condition

Producing a report fails.

### Primary Actor

HR Advisor.

### Trigger

Employee hands in a promotion request.

## MAIN SUCCESS SCENARIO

1. Employee hands in a promotion request.
2. HR advisors views the details of an employee
3. HR advisors decides on the promotion request

## EXTENSIONS

**Missing employee information:**
- HR Advisor has to collect sufficient data about the employee to add them to the system

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE:** Release 0.1.0.5