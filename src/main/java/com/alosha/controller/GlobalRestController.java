package com.alosha.controller;

import java.util.List;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alosha.dao.entities.Fruit;
import com.alosha.dao.entities.ScheduledInfo;
import com.alosha.scheduler.JobData;
import com.alosha.service.GlobalRestService;

@RestController
public class GlobalRestController {

	@Autowired
	GlobalRestService globalRestService;
	
	@PostMapping("/fruits")
	public ResponseEntity<Fruit> saveFruit(@RequestBody Fruit fruit)
	{
		Fruit savedFruit = globalRestService.saveFruit(fruit);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedFruit);
	}
	
	@GetMapping("/fruits")
	public ResponseEntity<List<Fruit>> findAllFruits()
	{
		List<Fruit> allFruits = globalRestService.findAllFruits();
		return ResponseEntity.status(HttpStatus.OK).body(allFruits);
	}
	
	@GetMapping("/fruits/count/{name}")
	public ResponseEntity<Long> countByNameFruits(@PathVariable String name)
	{
		long count=  globalRestService.countByFruitName(name);
		return ResponseEntity.status(HttpStatus.OK).body(count);
	
	}
	
	@DeleteMapping("/fruits/{fruitId}")
	public ResponseEntity<Void> deleteByFruitId(@PathVariable Long fruitId)
	{
		
		globalRestService.deleteFruit(fruitId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@PostMapping("/schedule")
	public ResponseEntity<Void> schedule(@RequestBody JobData data)
	{
		
		globalRestService.schedule(data);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	@GetMapping("/schedule")
	public ResponseEntity<List<ScheduledInfo>> schedule()
	{
		
		List<ScheduledInfo> findAllJob = globalRestService.findAllJob();
		return ResponseEntity.status(HttpStatus.OK).body(findAllJob);
	}
	
	@DeleteMapping("/schedule/{jobName}/{jobGroup}")
	public ResponseEntity<Void> schedule(@PathVariable String jobName,@PathVariable String jobGroup)
	{
		globalRestService.deleteJob(jobName, jobGroup);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	
}
