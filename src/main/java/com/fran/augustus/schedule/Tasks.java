package com.fran.augustus.schedule;

import com.fran.augustus.services.ChiefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Tasks {

	@Autowired
	@Lazy
	ChiefService chiefService;

	@Scheduled(cron = "3 */30 6-21 * * *")
	public void daily() throws InterruptedException {
		chiefService.command();
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void midnight() throws InterruptedException {
		chiefService.command();
	}

	@Scheduled(cron = "0 0 3 * * *")
	public void devilTime() throws InterruptedException {
		chiefService.command();
	}

}
