package B5_Session5.B12_Ex12;
import java.io.Serializable;


public class Student implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private double score;

    public Student(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    @Override
    public String toString()
    {
        return "Student[Name: " + name + ", Age: " + age + ", Score: " + score + "]";
    }
}