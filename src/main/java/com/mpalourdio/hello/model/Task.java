package com.mpalourdio.hello.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_list")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private int id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_priority")
    private String taskPriority;

    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "task_archived")
    private int taskArchived = 0;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<People> people;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(final String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(final String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(final String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(final String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int isTaskArchived() {
        return taskArchived;
    }

    public void setTaskArchived(final int taskArchived) {
        this.taskArchived = taskArchived;
    }

    public List<People> getPeople() {
        return people;
    }

    public void setPeople(final List<People> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", taskName=" + taskName
                + ", taskDescription=" + taskDescription + ", taskPriority="
                + taskPriority + ",taskStatus=" + taskStatus + "]";
    }
}
