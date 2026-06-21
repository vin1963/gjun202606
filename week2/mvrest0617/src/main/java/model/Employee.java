package model;

/**
 * 員工資料模型
 * Jackson 會自動序列化/反序列化 getter/setter
 */
public class Employee {

    private int id;
    private String name;
    private String department;
    private double salary;

    // 無參數建構子（Jackson 反序列化需要）
    public Employee() {}

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + 
               "', department='" + department + "', salary=" + salary + "}";
    }
}
