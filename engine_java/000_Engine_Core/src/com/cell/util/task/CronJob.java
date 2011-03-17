package com.cell.util.task;

import java.util.Date;

public interface CronJob {

	  public void execute(CronJobExecutionContext context) throws Throwable;
	
}
