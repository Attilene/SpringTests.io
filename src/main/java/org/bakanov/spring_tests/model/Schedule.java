package org.bakanov.spring_tests.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "schedules")
public class Schedule implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "row_id")
    private int id;

    @NotNull
    private Integer duration;

    @NotNull
    private Date start_dt;

    @NotNull
    private Time start_time;

    @NotNull
    private Date end_dt;

    @NotNull
    private Time end_time;

    @NotNull
    private Boolean active;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TestList testList;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Group group;

    public int getId() { return id; }

    public Group getGroup() { return group; }

    public Time getStart_time() { return start_time; }

    public Time getEnd_time() { return end_time; }

    public Date getStart_dt() { return start_dt; }

    public Date getEnd_dt() { return end_dt; }

    public Boolean getActive() { return active; }

    public Integer getDuration() { return duration; }

    public TestList getTestList() { return testList; }

    public void setActive(Boolean active) { this.active = active; }

    public void setStart_time(Time start_time) { this.start_time = start_time; }

    public void setStart_dt(Date start_dt) { this.start_dt = start_dt; }

    public void setEnd_time(Time end_time) { this.end_time = end_time; }

    public void setEnd_dt(Date end_dt) { this.end_dt = end_dt; }

    public void setDuration(Integer duration) { this.duration = duration; }

    public void setGroup(Group group) { this.group = group; }

    public void setTestList(TestList testList) { this.testList = testList; }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", duration=" + duration +
                ", start_dt=" + start_dt +
                ", start_time=" + start_time +
                ", end_dt=" + end_dt +
                ", end_time=" + end_time +
                ", active=" + active +
                ", testList=" + testList +
                ", group=" + group +
                '}';
    }
}
