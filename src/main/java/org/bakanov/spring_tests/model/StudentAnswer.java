package org.bakanov.spring_tests.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "student_answers")
public class StudentAnswer implements Serializable {
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
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "answer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Answer answer;

    public int getId() { return id; }

    public TestList getTestList() { return testList; }

    public User getUser() { return user; }

    public Answer getAnswer() { return answer; }

    public void setTestList(TestList testList) { this.testList = testList; }

    public void setAnswer(Answer answer) { this.answer = answer; }

    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "StudentAnswer{" +
                "id=" + id +
                ", testList=" + testList +
                ", user=" + user +
                ", answer=" + answer +
                '}';
    }
}
