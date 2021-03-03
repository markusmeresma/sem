# USE CASE 2: Produce a report on the salary of all the employees of a department
## CHARACTERISTIC INFORMATION
### Goal in Context

As a *Department manager* I want to produce a report on the salary of all the employees of my department so that I can support financial reporting of the organisation.

### Scope

Company.

### Level

Primary task

### Preconditions

We know the department. Database contains current employee salary data.

### Success End Condition

A report is available for the department manager to provide to finance.

### Failed End Condition

No report is produced

### Primary Actor

Department manager.

### Trigger

A request for finance information is sent to department manager.

## MAIN SUCCESS SCENARIO

1. Finance request salary information for a given department.
2. Department manager captures teh salary request.
3. Department manager extracts current salary information of all employees of the given department.
4. Department manager provides report to finance.

## EXTENSIONS

**No employees in the department:**
- Department manager informs finance that there aren't any employees in the department

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE:** Release 0.1.0.5