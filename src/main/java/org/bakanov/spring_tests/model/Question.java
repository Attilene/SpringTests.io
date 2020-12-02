package org.bakanov.spring_tests.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "row_id")
    private int id;

    @NotNull
    private String text;

    @NotNull
    private Integer score;

    @NotNull
    private Boolean active;

    public int getId() { return id; }

    public String getText() { return text; }

    public Integer getScore() { return score; }

    public Boolean getActive() { return active; }

    public void setText(String text) { this.text = text; }

    public void setActive(Boolean active) { this.active = active; }

    public void setScore(Integer score) { this.score = score; }

    @Override
    public String toString() {
        return "Question{" +
                "row_id=" + id +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", active=" + active +
                '}';
    }
}
