//
//  CThread.h
//  100_MusicGame
//
//  Created by wazazhang on 11-9-1.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#ifndef _COM_CELL_THREAD
#define _COM_CELL_THREAD

#include <string>
#include <vector>
#include <map>
#include <fstream>

#include <iostream.h>

#include "CType.h"

namespace com_cell
{
//	#define cell_sync(MUTEX) @synchronized(MUTEX)
//	
//	typedef struct Lock NSLock;
	
	class Runnable;
	class TaskFuture;
	
	//  ----------------------------------------------------------------------------------------------------------------------------//
	//  TaskManager
	//  ----------------------------------------------------------------------------------------------------------------------------//
	
	class TaskManager
	{
	// static
	private:
		static TaskManager* instance;
		
	public:
		inline static TaskManager* init(int concurrent) {
			if (instance == NULL) {
				instance = new TaskManager(concurrent);
			}
			return instance;
		}
		inline static TaskManager* getInstance() {
			return instance;
		}
		inline static void destory() {
			delete instance;
		}
		
	// self
	private:	
		
		NSOperationQueue*	operationQueue;
		
		TaskManager(int count);		
		
		~TaskManager();
		
	public:
		
		TaskFuture executeTask(Runnable* task);
		
//		
//		/**
//		 * 异步执行一个任务
//		 * @param r
//		 */
//		Future<?> executeTask(Runnable r);
//		
//		/**
//		 * 计划执行一个任务
//		 * @param r
//		 * @param delay 延后多少毫秒
//		 * @return
//		 */
//		ScheduledFuture<?> schedule(Runnable r, long delay);
//		
//		/**
//		 * 计划执行一个任务，并周期执行
//		 * @param r
//		 * @param initial 延后多少毫秒开始
//		 * @param period 开始后，每隔多长时间重新执行
//		 * @return
//		 */
//		ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long initial, long period);

		
	};
	
	//  ----------------------------------------------------------------------------------------------------------------------------//
	//  Task
	//  ----------------------------------------------------------------------------------------------------------------------------//
	
	class Runnable
	{
	public:
		virtual ~Runnable(){}
		
		virtual void run() = 0;
		
	}; // class Runnable
	
	class TaskFuture
	{
	private:
		NSOperation* op;
	public:
	}; // class TaskFuture
	
	
	//  ----------------------------------------------------------------------------------------------------------------------------//
	//  DisplayObjectContainer
	//  ----------------------------------------------------------------------------------------------------------------------------//
	
	inline TaskManager* getTaskManager()
	{
		return TaskManager::getInstance();
	}
	
	
	
	
}; // namespace com.cell

#endif // #define _COM_CELL_THREAD