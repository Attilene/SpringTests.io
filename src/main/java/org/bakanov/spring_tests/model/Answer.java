package org.bakanov.spring_tests.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answers")
public class Answer implements Serializable {
    //    TODO Fix AnswerModel
    @Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(
            name="answer_generator",
            sequenceName = "answer_sequence"
    )
    private int row_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;

    @Column(columnDefinition = "text")
    private String text;

    @Column(columnDefinition = "boolean")
    private Boolean correct;

    public int getRow_id() { return row_id; }

    public String getText() { return text; }

    public Question getQuestion() { return question; }

    public boolean getCorrect() { return correct; }

    public void setText(String text) { this.text = text; }

    public void setCorrect(Boolean correct) { this.correct = correct; }

    public void setQuestion(Question question) { this.question = question; }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + row_id +
                ", text='" + text + '\'' +
                ", question=" + question +
                '}';
    }
}
