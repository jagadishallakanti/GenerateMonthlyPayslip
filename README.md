# Generate Monthly payslip 
When user provides name and annual salary package. The program should generate monthly payslip information with Gross monthly income, monthly income tax and net monthly income.
<br/><br/>
The calculation is as follows: <br/>
Gross monthly income = annual_salary/12 <br/>
Monthly income tax = based on tax table provided below<br/>
Net income = Gross monthly income - Monthly income tax <br/><br/>


| Taxable Income     | Tax on this income           |
|--------------------|------------------------------|
| $0 - 20,000        | $0                           |
| $20,001 - $40,000  | 10c for each $1over $20,000  |
| $40,001 - $80,000  | 20c for each $1over $40,000  |
| $80,001 - $180,000 | 30c for each $1over $80,000  |
| $180,001 and over  | 40c for each $1over $180,000 |

# Assumption
* Since we are dealing with financials care is taken to decimal precision calculation by choosing big decimal over double or float
* Salary slip rounds to 2 decimal places for calculation.


Build/Create JAR instructions
-------------------------------

To create executable jar from source:
Go to project root directory and execute below command
```
gradlew clean build
```

To run test cases:

```
gradlew cleanTest test
```

Execute command: [Note path to jar depends on window or mac based system]

```
 java -jar .\build\libs\GenerateMonthlyPayslip-1.0-SNAPSHOT.jar "jagadish" 60000
```