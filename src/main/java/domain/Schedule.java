package domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private LocalDateTime startTime;

    private int duration;

    private Frequency frequency;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Group group;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Activity> activity;

    public Schedule(){

    }

    public Schedule(String name, LocalDateTime startTime, int duration, Frequency frequency, User user, List<Activity> activity) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.frequency = frequency;
        this.user = user;
        this.activity = activity;
    }

    public Schedule(String name, int duration, List<Activity> activity) {
        this.name = name;
        this.duration = duration;
        this.activity = activity;
    }

    public Schedule(String name, LocalDateTime startTime, int duration, Frequency frequency, Group group) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.frequency = frequency;
        this.group = group;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Activity> getActivity() { return activity; }

    public void setActivity(List<Activity> activity) { this.activity = activity; }
}
