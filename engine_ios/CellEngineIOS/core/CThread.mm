//
//  CThread.mm
//  100_MusicGame
//
//  Created by wazazhang on 11-9-1.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#include "CThread.h"
#import <Foundation/NSOperation.h>
#import <OpenGLES/EAGL.h>
#import <OpenGLES/ES1/gl.h>
#import <OpenGLES/ES1/glext.h>
#import <OpenAL/al.h>
#import <OpenAL/alc.h>


//////////////////////////////////////////////////////////////////////////////////
// NSInvocationOperation TASK
//////////////////////////////////////////////////////////////////////////////////

@interface OPTask : NSOperation {
	com_cell::Runnable* runnable;
	EAGLContext*		renderContext;
}
- (id) initWithRunnable: (com_cell::Runnable*) task ;
@end

//////////////////////////////////////////////////////////////////////////////////

@implementation OPTask
- (id) initWithRunnable: (com_cell::Runnable*) task 
{
    self = [super init];   // 必須呼叫父類的init
	if (self) {
		runnable		= task;
		renderContext	= [EAGLContext currentContext];
	} else {
		runnable = NULL;
		NSLog(@"can not init NSOperation as OPTask!");
	}
    return self;
}
- (void)main
{	
	@try {
		NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];
		
		if (![EAGLContext currentContext]) {
			[EAGLContext setCurrentContext:renderContext];
		}
		runnable->run();
		// Do some work on myData and report the results.
		[pool release];
	}
	@catch(...) {
		// Do not rethrow exceptions.
		NSLog(@"OPTask: some exceptions happend!");
	}
}
@end

////////////////////////////////////////////////////////////////////////////////////




namespace com_cell
{
	TaskManager* TaskManager::instance = NULL;


	TaskManager::TaskManager(int count)
	{
		operationQueue = [[NSOperationQueue alloc] init]; //初始化操作队列
		[operationQueue retain];
		[operationQueue setMaxConcurrentOperationCount:count];
		//在这里限定了该队列只同时运行一个线程
		//这个队列已经可以使用了
	}
	
	TaskManager::~TaskManager()
	{
		[operationQueue release];
	}
	
	TaskFuture TaskManager::executeTask(com_cell::Runnable *task)
	{
		//创建一个NSInvocationOperation对象，并初始化到方法
		//在这里，selector参数后的值是你想在另外一个线程中运行的方法（函数，Method）
		//在这里，object后的值是想传递给前面方法的数据
//		NSInvocationOperation* theOp = [[NSInvocationOperation alloc]
//										initWithTarget:self
//										selector:@selector(NULL:) object:data];
		
		OPTask* theOP = [[[OPTask alloc] initWithRunnable:task] autorelease];
		
		// 下面将我们建立的操作“Operation”加入到本地程序的共享队列中（加入后方法就会立刻被执行）
		// 更多的时候是由我们自己建立“操作”队列
		[operationQueue addOperation:theOP];

		
		TaskFuture ret;
		return ret;
	}
	
}; // namespace com.cell
