package com.alosha.scheduler;

import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.alosha.dao.repo.FruitRepo;

@Component
public class ScheduledJob extends QuartzJobBean{

	@Autowired
	FruitRepo fruitRepo;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDataMap mergedJobDataMap = context.getMergedJobDataMap();

		System.out.println("Executing Job with key {}"+ context.getJobDetail().getKey());
		for(String key : mergedJobDataMap.getKeys())
		{
			System.out.println(" from scheduled job :: "+mergedJobDataMap.get(key));
		}
		
		
		List<String> fruitName = fruitRepo.findUniqueFruitName();
		for(String fruit:fruitName)
		{
			System.out.println(" fruit = "+fruit+" and count is "+fruitRepo.countByFruitName(fruit));
		}
		
		
	}

}
