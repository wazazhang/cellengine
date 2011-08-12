//
//  EAGLView.m
//  TestGL
//
//  Created by WAZA on 08-5-22.
//  Copyright __MyCompanyName__ 2008. All rights reserved.
//



#import <QuartzCore/QuartzCore.h>
#import <OpenGLES/EAGLDrawable.h>

#import "EAGLView.h"


#include "CScreenManager.h"
#include "Screens.h"

#define USE_DEPTH_BUFFER 1

// A class extension to declare private methods
@interface EAGLView ()

@property (nonatomic, retain) EAGLContext *context;
@property (nonatomic, assign) NSTimer *animationTimer;

- (BOOL) createFramebuffer;
- (void) destroyFramebuffer;

@end


@implementation EAGLView

@synthesize context;
@synthesize animationTimer;
@synthesize animationInterval;


// You must implement this
+ (Class) layerClass {
	return [CAEAGLLayer class];
}


//The GL view is stored in the nib file. When it's unarchived it's sent -initWithCoder:
- (id)initWithCoder:(NSCoder*)coder {
    
	if ((self = [super initWithCoder:coder])) {
		// Get the layer
		CAEAGLLayer *eaglLayer = (CAEAGLLayer *)self.layer;
		
		eaglLayer.opaque = YES;
		eaglLayer.drawableProperties = [NSDictionary dictionaryWithObjectsAndKeys:
										[NSNumber numberWithBool:NO],
										kEAGLDrawablePropertyRetainedBacking,
										kEAGLColorFormatRGBA8, 
										kEAGLDrawablePropertyColorFormat,
										nil];
		
		context = [[EAGLContext alloc] initWithAPI:kEAGLRenderingAPIOpenGLES1];
		
		if (!context || ![EAGLContext setCurrentContext:context]) {
			[self release];
			return nil;
		}
		
		animationInterval = 1.0 / 60.0;
		
		//Configure and start accelerometer
		[[UIAccelerometer sharedAccelerometer] setUpdateInterval:(1.0 / 30)];
		[[UIAccelerometer sharedAccelerometer] setDelegate:self];
		
		// Initialization code.
		com_cell::ScreenManager::init(new gt_teris::Screens());	
	}		
    
	return self;
}


- (void)layoutSubviews {
	[EAGLContext setCurrentContext:context];
	[self destroyFramebuffer];
	[self createFramebuffer];
	[self drawView];
}

- (BOOL)createFramebuffer
{
	glGenFramebuffersOES(1, &viewFramebuffer);
	glGenRenderbuffersOES(1, &viewRenderbuffer);
	
	glBindFramebufferOES(GL_FRAMEBUFFER_OES, viewFramebuffer);
	glBindRenderbufferOES(GL_RENDERBUFFER_OES, viewRenderbuffer);
	[context renderbufferStorage:GL_RENDERBUFFER_OES fromDrawable:(id<EAGLDrawable>)self.layer];
	glFramebufferRenderbufferOES(GL_FRAMEBUFFER_OES, GL_COLOR_ATTACHMENT0_OES, GL_RENDERBUFFER_OES, viewRenderbuffer);
	
	glGetRenderbufferParameterivOES(GL_RENDERBUFFER_OES, GL_RENDERBUFFER_WIDTH_OES, &backingWidth);
	glGetRenderbufferParameterivOES(GL_RENDERBUFFER_OES, GL_RENDERBUFFER_HEIGHT_OES, &backingHeight);
	
	if(glCheckFramebufferStatusOES(GL_FRAMEBUFFER_OES) != GL_FRAMEBUFFER_COMPLETE_OES) {
		NSLog(@"failed to make complete framebuffer object %x", glCheckFramebufferStatusOES(GL_FRAMEBUFFER_OES));
		return NO;
	}
	
	return YES;
}

- (void)destroyFramebuffer
{
	glDeleteFramebuffersOES(1, &viewFramebuffer);
	viewFramebuffer = 0;
	glDeleteRenderbuffersOES(1, &viewRenderbuffer);
	viewRenderbuffer = 0;
	
	if(depthRenderbuffer) {
		glDeleteRenderbuffersOES(1, &depthRenderbuffer);
		depthRenderbuffer = 0;
	}
}

- (void)drawView 
{
	// Replace the implementation of this method to do your own custom drawing
	[EAGLContext setCurrentContext:context];
	glBindFramebufferOES(GL_FRAMEBUFFER_OES, viewFramebuffer);
    
	// main update
	com_cell::getScreenManager()->call_Update([self bounds]);
	
	glBindRenderbufferOES(GL_RENDERBUFFER_OES, viewRenderbuffer);
	[context presentRenderbuffer:GL_RENDERBUFFER_OES];
	
	[[NSRunLoop currentRunLoop] runUntilDate: [NSDate distantPast]];
}



- (void)startAnimation {
	self.animationTimer = [NSTimer scheduledTimerWithTimeInterval:animationInterval target:self selector:@selector(drawView) userInfo:nil repeats:YES];
}


- (void)stopAnimation {
	self.animationTimer = nil;
}


- (void)setAnimationTimer:(NSTimer *)newTimer {
	[animationTimer invalidate];
	animationTimer = newTimer;
}


- (void)setAnimationInterval:(NSTimeInterval)interval {
	animationInterval = interval;
	if (animationTimer) {
		[self stopAnimation];
		[self startAnimation];
	}
}


- (void)dealloc {
	
	[self stopAnimation];
	
	if ([EAGLContext currentContext] == context) {
		[EAGLContext setCurrentContext:nil];
	}
	
	[context release];	
	[super dealloc];
	
	// delete screen
	com_cell::ScreenManager::destory();
	
}


////////////////////////////////////////////////////////////////////////////////////////////////
// touch events

// pointer pressed
- (void) touchesBegan:(NSSet*)touches withEvent:(UIEvent*)event
{
	UITouch*			touch = [touches anyObject];
	CGPoint				location = [touch locationInView:self],
	lastLocation = [touch previousLocationInView:self];
	
	//px = location.x;
	//py = location.y;
	
	// system call
	com_cell::getScreenManager()->call_PointerPressed(0, location.x, location.y);
	com_cell::getScreenManager()->call_PointerPressed(1, lastLocation.x, lastLocation.y);
	
	//[self setNeedsDisplay];
	//printf("touchesBegan\n");
}

// pointer moved
- (void) touchesMoved:(NSSet*)touches withEvent:(UIEvent*)event
{
	UITouch*			touch = [touches anyObject];
	CGPoint				location = [touch locationInView:self],
	lastLocation = [touch previousLocationInView:self];
	
    //	px = location.x;
    //	py = location.y;
	
	// system call
	com_cell::getScreenManager()->call_PointerDragged(0, location.x, location.y);
	com_cell::getScreenManager()->call_PointerDragged(1, lastLocation.x, lastLocation.y);
	
	//printf("touchesMoved\n");
	
}



// pointer released
- (void) touchesEnded:(NSSet*)touches withEvent:(UIEvent*)event
{
	UITouch*			touch = [touches anyObject];
	CGPoint				location = [touch locationInView:self],
	lastLocation = [touch previousLocationInView:self];
	
    //	px = location.x;
    //	py = location.y;
	
	// system call
	com_cell::getScreenManager()->call_PointerReleased(0, location.x, location.y);
	com_cell::getScreenManager()->call_PointerReleased(1, lastLocation.x, lastLocation.y);
	//
	//printf("touchesEnded\n");
	
	
}

////////////////////////////////////////////////////////////////////////////////////////////////
// accelerometer events
// 
- (void)accelerometer:(UIAccelerometer*)accelerometer didAccelerate:(UIAcceleration*)acceleration
{
	//Use a basic low-pass filter to only keep the gravity in the accelerometer values
	//printf("acc : x=%f y=%f z=%f\n",acceleration.x,acceleration.y,acceleration.z);
	com_cell::getScreenManager()->call_Accelerometer(acceleration.x, acceleration.y, acceleration.z);
}



@end

