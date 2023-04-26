package com.alosha.dao.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class ScheduledInfo {

	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String jobName;
	private String jobGroup;
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private LocalDateTime startTime;
	private int counter;
	private int gapDuration;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public int getGapDuration() {
		return gapDuration;
	}
	public void setGapDuration(int gapDuration) {
		this.gapDuration = gapDuration;
	}
	
	
	
}
