package org.bakanov.spring_tests.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "tests")
public class Test implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "row_id")
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TestList testList;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;

    public int getId() { return id; }

    public TestList getTestList() { return testList; }

    public Question getQuestion() { return question; }

    public void setTestList(TestList testList) { this.testList = testList; }

    public void setQuestion(Question question) { this.question = question; }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", testList=" + testList +
                ", question=" + question +
                '}';
    }
}
