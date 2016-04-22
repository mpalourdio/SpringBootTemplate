package com.mpalourdio.hello.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;

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

    public int getTaskId() {
        return this.id;
    }

    public void setTaskId(final int taskId) {
        this.id = taskId;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(final String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public void setTaskDescription(final String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskPriority() {
        return this.taskPriority;
    }

    public void setTaskPriority(final String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(final String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int isTaskArchived() {
        return this.taskArchived;
    }

    public void setTaskArchived(final int taskArchived) {
        this.taskArchived = taskArchived;
    }

    @Override
    public String toString() {
        return "Task [id=" + this.id + ", taskName=" + this.taskName
                + ", taskDescription=" + this.taskDescription + ", taskPriority="
                + this.taskPriority + ",taskStatus=" + this.taskStatus + "]";
    }
}
