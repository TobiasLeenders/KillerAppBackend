package domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class GroupSchedule {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<User> userlist;

    public GroupSchedule(){

    }

    public GroupSchedule(String name, List<User> users) {
        this.name = name;
        this.userlist = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return userlist;
    }

    public void setUsers(List<User> users) {
        this.userlist = users;
    }
}
