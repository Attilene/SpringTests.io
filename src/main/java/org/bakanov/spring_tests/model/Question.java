package org.bakanov.spring_tests.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name="question_generator",
            sequenceName = "question_sequence"
    )
    private int row_id;

    @Column(columnDefinition = "text")
    private String text;

    @Column(columnDefinition = "integer")
    private Integer score;

    @Column(columnDefinition = "boolean")
    private Boolean active;

    public int getRow_id() { return row_id; }

    public String getText() { return text; }

    public Integer getScore() { return score; }

    public Boolean getActive() { return active; }

    public void setText(String text) { this.text = text; }

    public void setActive(Boolean active) { this.active = active; }

    public void setScore(Integer score) { this.score = score; }

    @Override
    public String toString() {
        return "Question{" +
                "row_id=" + row_id +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", active=" + active +
                '}';
    }
}
