package com.napier.sem;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // LAB 06
        ArrayList<Employee> employees = a.getDepartment("Sales");
        a.printSalaries(employees);

        // CODE FROM PREVIOUS LABS
        // Extract employee salary information
        // ArrayList<Employee> employees = a.getAllSalaries();

        // Extract salary information of a given role
        // Not working as intended - connects to a database but nothing happens
        // ArrayList<Employee> employees = a.getSalariesByRole("Engineer");

        // Test the size of the returned data - should be 240124
        // System.out.println(employees.size());
        // Print a list of salaries
        //a.printSalaries(employees);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(0);
                //Connect to database locally - use for debugging (repackaging the project and running the code is easier & faster
                //con = DriverManager.getConnection("jdbc:mysql://localhost:33060/employees?useSSL=true", "root", "example");

                // Connect to database inside docker
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public Employee getEmployee(int ID) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT emp_no, first_name, last_name "
                            + "FROM employees "
                            + "WHERE emp_no = " + ID;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                return emp;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public void displayEmployee(Employee emp) {
        if (emp != null) {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }

    /**
     * Gets all the current employees and salaries.
     *
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01'"
                            + "ORDER BY employees.emp_no ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Gets a list of salaries of a given role
     *
     * @return employees The list of employees to print
     */
    public ArrayList<Employee> getSalariesByRole(String role) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries, titles "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' AND titles.to_date = '9999-01-01' AND titles.title ='" + role + "' "
                            + "ORDER BY employees.emp_no ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Prints a list of employees.
     *
     * @param employees The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employees) {
        if (employees == null) {
            System.out.println("Employees list is empty");
            return;
        } else {
            // Print header
            System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
            // Loop over all employees in the list
            for (Employee emp : employees) {
                if (emp == null)
                    continue;
                String emp_string =
                        String.format("%-10s %-15s %-20s %-8s",
                                emp.emp_no, emp.first_name, emp.last_name, emp.salary);
                System.out.println(emp_string);
            }
        }
    }

    public ArrayList<Employee> getDepartment(String dept_name) {
        try {
            // Create String for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                        + "FROM employees, salaries, dept_emp, departments "
                        + "WHERE employees.emp_no = salaries.emp_no "
                        + "AND employees.emp_no = dept_emp.emp_no "
                        + "AND dept_emp.dept_no = departments.dept_no "
                        + "AND salaries.to_date = '9999-01-01' "
                        + "AND departments.dept_name LIKE ? "
                        + "ORDER BY employees.emp_no ASC";

            PreparedStatement preparedStatement = con.prepareStatement(strSelect);
            preparedStatement.setString(1, dept_name);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();

            while(resultSet.next()) {
                Employee emp = new Employee();
                emp.emp_no = resultSet.getInt("employees.emp_no");
                emp.first_name = resultSet.getString("employees.first_name");
                emp.last_name = resultSet.getString("employees.last_name");
                emp.salary = resultSet.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get department");
        }

        return null;
    }

    public void displayDepartment(Department dept) {
        if(dept != null) {
            System.out.println(
                    "Dept no: " + dept.dept_no + "\n"
                    + "Dept_name: " + dept.dept_name
            );
        } else {
            System.out.println("Department is NULL");
        }
    }
}
