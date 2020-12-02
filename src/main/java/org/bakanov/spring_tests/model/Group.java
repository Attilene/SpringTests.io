package org.bakanov.spring_tests.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "groups")
public class Group implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "row_id")
    private int id;

    @NotNull
    @Column(unique = true, length = 50)
    private String name;

    @NotNull
    private Integer year;

    private Integer semester;

    public int getId() { return id; }

    public String getName() { return name; }

    public Integer getSemester() { return semester; }

    public Integer getYear() { return year; }

    public void setName(String name) { this.name = name; }

    public void setYear(Integer year) { this.year = year; }

    public void setSemester(Integer semester) { this.semester = semester; }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", semester=" + semester +
                '}';
    }
}