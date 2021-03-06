package org.bakanov.spring_tests.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "answers")
public class Answer implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "row_id")
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;

    @NotNull
    private String text;

    @NotNull
    private Boolean correct;

    public int getId() { return id; }

    public String getText() { return text; }

    public Question getQuestion() { return question; }

    public boolean getCorrect() { return correct; }

    public void setText(String text) { this.text = text; }

    public void setCorrect(Boolean correct) { this.correct = correct; }

    public void setQuestion(Question question) { this.question = question; }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", question=" + question +
                '}';
    }
}
