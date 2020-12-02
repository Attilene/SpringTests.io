package org.bakanov.spring_tests.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
            name="user_generator",
            sequenceName = "user_sequence"
    )
    private int row_id;

    @Column(columnDefinition = "varchar(50)")
    private String first_name;

    @Column(columnDefinition = "varchar(50)")
    private String last_name;

    @Column(columnDefinition = "varchar(50)")
    private String middle_name;

    @Column(columnDefinition = "varchar(50)")
    private String login;

    @Column(columnDefinition = "varchar(256)")
    private String password_hash;

    public int getRow_id() { return row_id; }

    public String getLogin() { return login; }

    public String getPassword_hash() { return password_hash; }

    public String getMiddle_name() { return middle_name; }

    public String getLast_name() { return last_name; }

    public String getFirst_name() { return first_name; }

    public void setPassword_hash(String password_hash) { this.password_hash = password_hash; }

    public void setLogin(String login) { this.login = login; }

    public void setMiddle_name(String middle_name) { this.middle_name = middle_name; }

    public void setLast_name(String last_name) { this.last_name = last_name; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

    @Override
    public String toString() {
        return "User{" +
                "row_id=" + row_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", login='" + login + '\'' +
                ", password_hash='" + password_hash + '\'' +
                '}';
    }
}
