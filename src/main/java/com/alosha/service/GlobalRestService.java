package com.alosha.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alosha.dao.entities.Fruit;
import com.alosha.dao.entities.ScheduledInfo;
import com.alosha.dao.repo.FruitRepo;
import com.alosha.dao.repo.ScheduledInfoRepo;
import com.alosha.scheduler.JobData;
import com.alosha.scheduler.MySchedulerListener;
import com.alosha.scheduler.ScheduledJob;

@Service
public class GlobalRestService {

	@Autowired
	FruitRepo fruitRepo;
	
	@Autowired
	Scheduler quartzScheduler;
	
	@Autowired
	ScheduledInfoRepo scheduledInfoRepo;
	
	@Autowired
	MySchedulerListener mySchedulerListener;
	
	
	  @PostConstruct public void postContruct() { try { 
		  
		  
		  quartzScheduler.start(); 
		  quartzScheduler.getListenerManager().addSchedulerListener(mySchedulerListener);
		  
	  
	  }
	  catch(SchedulerException exception) {
	  System.out.println("scheduler thorws exception "+exception); } }
	  
	  @PreDestroy public void preDestroy() { try { quartzScheduler.shutdown(); }
	  catch (SchedulerException exception) {
	  System.out.println("scheduler thorws exception "+exception); } }
	 
	
	public Fruit saveFruit(Fruit fruit)
	{
		Fruit savedFruit = fruitRepo.save(fruit);
		return savedFruit;
	}
	
	public List<Fruit> findAllFruits()
	{
		Iterable<Fruit> allFruits = fruitRepo.findAll();
		return (List<Fruit>) allFruits;
	}
	
	public void deleteFruit(Long fruitId)
	{
		Optional<Fruit> fruit = fruitRepo.findById(fruitId);
		
			if(fruit.isPresent())
				fruitRepo.delete(fruit.get());
		
	}
	
	public long countByFruitName(String fruitName)
	{
		
		long count = fruitRepo.countByFruitName(fruitName);
		return count;
	}
	
	
	public void schedule(JobData data)
	{
		String jobName = data.getJobName();
		String jobGroup = data.getJobGroup();
		
		int counter = data.getCounter();
		int gapDuration = data.getGapDuration();
		
		ZonedDateTime zonedDateTime = ZonedDateTime.of(data.getStartTime(), ZoneId.of("Asia/Kolkata"));
		
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("test", "this is just for demo");
		
		
		ScheduledInfo  scheduledInfo = new ScheduledInfo();
		scheduledInfo.setCounter(counter);
		scheduledInfo.setGapDuration(gapDuration);
		scheduledInfo.setJobGroup(jobGroup);
		scheduledInfo.setJobName(jobName);
		scheduledInfo.setStartTime(data.getStartTime());
		
		
		JobDetail detail = JobBuilder.newJob(ScheduledJob.class)
				.withIdentity(jobName, jobGroup).usingJobData(dataMap).storeDurably(false)
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
				.startAt(Date.from(zonedDateTime.toInstant()))
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(gapDuration).withRepeatCount(counter))
				.build();
		
		
		
		
		try {
			quartzScheduler.scheduleJob(detail, trigger);
		scheduledInfoRepo.save(scheduledInfo);
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public List<ScheduledInfo> findAllJob()
	{
		Iterable<ScheduledInfo> findAll = scheduledInfoRepo.findAll();
		Iterator<ScheduledInfo> iterator = findAll.iterator();
		List<ScheduledInfo> allScheduledInfo = new ArrayList<ScheduledInfo>();
		while(iterator.hasNext())
			allScheduledInfo.add(iterator.next());
		return allScheduledInfo;
	}
	
	public void deleteJob(String jobName , String jobGroup)
	{
		JobKey jobKey = new JobKey(jobName, jobGroup);
		try {
			quartzScheduler.deleteJob(jobKey);
			
			ScheduledInfo scheduledInfo = scheduledInfoRepo.findByJobName(jobName);
			if(scheduledInfo!=null)
				scheduledInfoRepo.delete(scheduledInfo);
			
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}




