package org.bakanov.spring_tests.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "row_id")
    private int id;

    @NotNull
    @Column(length = 50)
    private String first_name;

    @NotNull
    @Column(length = 50)
    private String last_name;

    @Column(length = 50)
    private String middle_name;

    @Column(unique = true, length = 50)
    private String login;

    @Column(length = 256)
    private String password_hash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Group group;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Role role;

    public int getId() { return id; }

    public String getLogin() { return login; }

    public String getPassword_hash() { return password_hash; }

    public String getMiddle_name() { return middle_name; }

    public String getLast_name() { return last_name; }

    public String getFirst_name() { return first_name; }

    public Group getGroup() { return group; }

    public Role getRole() { return role; }

    public void setPassword_hash(String password_hash) { this.password_hash = password_hash; }

    public void setLogin(String login) { this.login = login; }

    public void setMiddle_name(String middle_name) { this.middle_name = middle_name; }

    public void setLast_name(String last_name) { this.last_name = last_name; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public void setRole(Role role) { this.role = role; }

    public void setGroup(Group group) { this.group = group; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", login='" + login + '\'' +
                ", password_hash='" + password_hash + '\'' +
                ", group=" + group +
                ", role=" + role +
                '}';
    }
}
