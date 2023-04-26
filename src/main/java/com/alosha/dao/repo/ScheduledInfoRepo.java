package com.alosha.dao.repo;

import org.springframework.data.repository.CrudRepository;

import com.alosha.dao.entities.ScheduledInfo;

public interface ScheduledInfoRepo extends CrudRepository<ScheduledInfo, Long> {

	public ScheduledInfo findByJobName(String jobName);
	
}
