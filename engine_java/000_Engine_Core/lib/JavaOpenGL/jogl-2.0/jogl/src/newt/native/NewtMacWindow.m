/*
 * Copyright (c) 2009 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 */

#import "NewtMacWindow.h"
#import "InputEvent.h"
#import "KeyEvent.h"
#import "MouseEvent.h"

static jmethodID updateWindowID  = NULL;


jint GetDeltaY(NSEvent *event, jint javaMods) {
    CGFloat deltaY = 0.0;
    CGEventRef cgEvent = [event CGEvent];

    if (CGEventGetIntegerValueField(cgEvent, kCGScrollWheelEventIsContinuous)) {
        // mouse pad case
        deltaY =
            CGEventGetIntegerValueField(cgEvent, kCGScrollWheelEventPointDeltaAxis1);
    } else {
        // traditional mouse wheel case
        deltaY = [event deltaY];
        if (deltaY == 0.0 && (javaMods & EVENT_SHIFT_MASK) != 0) {
            // shift+vertical wheel scroll produces horizontal scroll
            // we convert it to vertical
            deltaY = [event deltaX];
        }
        if (deltaY < 1.0  && deltaY > -1.0) {
            deltaY *= 10.0;
        } else {
            if (deltaY < 0.0) {
                deltaY = deltaY - 0.5f;
            } else {
                deltaY = deltaY + 0.5f;
            }
        }
    }

    if (deltaY > 0) {
        return (NSInteger)deltaY;
    } else if (deltaY < 0) {
        return -(NSInteger)deltaY;
    }

    return 0;
}

@implementation NewtView

- (id)initWithFrame:(NSRect)frameRect {
    if (self = [super initWithFrame: frameRect]) {
        javaWindowObject = nil;
        resizeTimer = nil;
        env = nil;
    }
    return self;
}

- (void) setJNIEnv: (JNIEnv*) theEnv
{
    env = theEnv;
}
- (JNIEnv*) getJNIEnv
{
    return env;
}

- (void) setJavaWindowObject: (jobject) javaWindowObj
{
    javaWindowObject = javaWindowObj;
}

- (jobject) getJavaWindowObject
{
    return javaWindowObject;
}

- (void) rightMouseDown: (NSEvent*) theEvent
{
    NSResponder* next = [self nextResponder];
    if (next != nil) {
        [next rightMouseDown: theEvent];
    }
}

/** FIXME: Tried child window: message reception ..
- (void)viewWillDraw
{
    fprintf(stderr, "*************** viewWillDraw: 0x%p", javaWindowObject); fflush(stderr);
    [super viewWillDraw];
} */
- (void) viewDidMoveToWindow {
  if ([self window] != NULL) {
    [self updateTrackingRect];
  }
}

- (void) updateTrackingRect {
  if (enterExitTrackingRectTag > 0) {
    [self removeTrackingRect:enterExitTrackingRectTag];
    enterExitTrackingRectTag = 0;
  }
  enterExitTrackingRectTag = [self addTrackingRect:[self visibleRect]
                                             owner:self 
                                          userData:NULL
                                      assumeInside:NO];
}

- (void) mouseEntered: (NSEvent*) theEvent {
  NewtMacWindow* win = (NewtMacWindow *) [self window];
  [win sendMouseEvent: theEvent eventType: EVENT_MOUSE_ENTERED];
}

- (void) mouseExited: (NSEvent*) theEvent {
  NewtMacWindow* win = (NewtMacWindow *) [self window];
  [win sendMouseEvent: theEvent eventType: EVENT_MOUSE_EXITED];
}

- (void)viewWillStartLiveResize
{
    [super viewWillStartLiveResize];

    resizeTimer = [[NSTimer scheduledTimerWithTimeInterval:0.01
                                                    target:self
                                                  selector:@selector(viewResizing:) 
                                                  userInfo:NULL repeats:YES] retain];
    [[NSRunLoop currentRunLoop] addTimer: resizeTimer 
                                 forMode: NSEventTrackingRunLoopMode];
}

- (void)viewDidEndLiveResize
{
    [super viewDidEndLiveResize];

    if (resizeTimer != nil) {
        [resizeTimer invalidate];
        [resizeTimer release];
        resizeTimer = nil;
    }
}

- (void)viewResizing:(NSTimer *)timer
{
    (*env)->CallVoidMethod(env, javaWindowObject, updateWindowID);
}

- (void)drawRect:(NSRect)dirtyRect {
    if ([self inLiveResize]) {
        (*env)->CallVoidMethod(env, javaWindowObject, updateWindowID);
    }
}

@end

static jmethodID sendMouseEventID  = NULL;
static jmethodID sendKeyEventID    = NULL;
static jmethodID insetsChangedID   = NULL;
static jmethodID sizeChangedID     = NULL;
static jmethodID positionChangedID = NULL;
static jmethodID focusChangedID    = NULL;
static jmethodID windowDestroyNotifyID = NULL;
static jmethodID windowDestroyedID = NULL;
//Rises a bit on every ButtonDown and releases on every ButtonUp event.
//Thus we know the button's mask for every event including Exited/Entered which 
//by default has wrong modifiers.
static jint mouseButtonsDown = 0;

@implementation NewtMacWindow

+ (BOOL) initNatives: (JNIEnv*) env forClass: (jclass) clazz
{
    sendMouseEventID  = (*env)->GetMethodID(env, clazz, "sendMouseEvent",  "(IIIIII)V");
    sendKeyEventID    = (*env)->GetMethodID(env, clazz, "sendKeyEvent",    "(IIIC)V");
    sizeChangedID     = (*env)->GetMethodID(env, clazz, "sizeChanged",     "(II)V");
    insetsChangedID     = (*env)->GetMethodID(env, clazz, "insetsChanged", "(IIII)V");
    positionChangedID = (*env)->GetMethodID(env, clazz, "positionChanged", "(II)V");
    updateWindowID = (*env)->GetMethodID(env, clazz, "updateWindow", "()V");
    focusChangedID = (*env)->GetMethodID(env, clazz, "focusChanged", "(Z)V");
    windowDestroyNotifyID    = (*env)->GetMethodID(env, clazz, "windowDestroyNotify",    "()V");
    windowDestroyedID    = (*env)->GetMethodID(env, clazz, "windowDestroyed",    "()V");
    if (sendMouseEventID && sendKeyEventID && sizeChangedID && insetsChangedID &&
        positionChangedID && focusChangedID && windowDestroyedID && windowDestroyNotifyID &&
        updateWindowID)
    {
        return YES;
    }
    return NO;
}

- (void) updateInsets: (JNIEnv*) env
{
    NSView* nsview = [self contentView];
    if( ! [nsview isMemberOfClass:[NewtView class]] ) {
        return;
    }
    NewtView* view = (NewtView *) nsview;
    jobject javaWindowObject = [view getJavaWindowObject];
    if (env==NULL || javaWindowObject == NULL) {
        return;
    }
    
    NSRect frameRect = [self frame];
    NSRect contentRect = [self contentRectForFrameRect: frameRect];

    // note: this is a simplistic implementation which doesn't take
    // into account DPI and scaling factor
    CGFloat l = contentRect.origin.x - frameRect.origin.x;
    jint top = (jint)(frameRect.size.height - contentRect.size.height);
    jint left = (jint)l;
    jint bottom = (jint)(contentRect.origin.y - frameRect.origin.y);
    jint right = (jint)(frameRect.size.width - (contentRect.size.width + l));

    (*env)->CallVoidMethod(env, javaWindowObject, insetsChangedID,
                           left, top, right, bottom);
}

- (id) initWithContentRect: (NSRect) contentRect
       styleMask: (NSUInteger) windowStyle
       backing: (NSBackingStoreType) bufferingType
       screen:(NSScreen *)screen
{
    id res = [super initWithContentRect: contentRect
                    styleMask: windowStyle
                    backing: bufferingType
                    defer: YES
                    screen: screen];
    // Why is this necessary? Without it we don't get any of the
    // delegate methods like resizing and window movement.
    [self setDelegate: self];
    return res;
}

- (BOOL) canBecomeKeyWindow
{
    // Even if the window is borderless, we still want it to be able
    // to become the key window to receive keyboard events
    return YES;
}

static jint mods2JavaMods(NSUInteger mods)
{
    int javaMods = 0;
    if (mods & NSShiftKeyMask) {
        javaMods |= EVENT_SHIFT_MASK;
    }
    if (mods & NSControlKeyMask) {
        javaMods |= EVENT_CTRL_MASK;
    }
    if (mods & NSCommandKeyMask) {
        javaMods |= EVENT_META_MASK;
    }
    if (mods & NSAlternateKeyMask) {
        javaMods |= EVENT_ALT_MASK;
    }
    // wouldn't it be nice if it was that easy?
/*     if (mods & NSLeftMouseDownMask) { */
/*         javaMods |= EVENT_BUTTON1_MASK; */
/*     } */
/*     if (mods & NSRightMouseDownMask) { */
/*         javaMods |= EVENT_BUTTON3_MASK; */
/*     } */
/*     if (mods & NSOtherMouseDownMask) { */
/*         javaMods |= EVENT_BUTTON2_MASK; */
/*     } */

    return javaMods;
}

- (void) sendKeyEvent: (NSEvent*) event eventType: (jint) evType
{
    NSView* nsview = [self contentView];
    if( ! [nsview isMemberOfClass:[NewtView class]] ) {
        return;
    }
    NewtView* view = (NewtView *) nsview;
    jobject javaWindowObject = [view getJavaWindowObject];
    JNIEnv* env = [view getJNIEnv];
    if (env==NULL || javaWindowObject == NULL) {
        return;
    }

    int i;
    jint keyCode = (jint) [event keyCode];
    NSString* chars = [event charactersIgnoringModifiers];
    int len = [chars length];
    jint javaMods = mods2JavaMods([event modifierFlags]);

    for (i = 0; i < len; i++) {
        // Note: the key code in the NSEvent does not map to anything we can use
        jchar keyChar = (jchar) [chars characterAtIndex: i];

        (*env)->CallVoidMethod(env, javaWindowObject, sendKeyEventID,
                               evType, javaMods, keyCode, keyChar);
    }
}

- (void) keyDown: (NSEvent*) theEvent
{
    [self sendKeyEvent: theEvent eventType: EVENT_KEY_PRESSED];
}

- (void) keyUp: (NSEvent*) theEvent
{
    [self sendKeyEvent: theEvent eventType: EVENT_KEY_RELEASED];
}

- (void) sendMouseEvent: (NSEvent*) event eventType: (jint) evType
{
    NSView* nsview = [self contentView];
    if( ! [nsview isMemberOfClass:[NewtView class]] ) {
        return;
    }
    NewtView* view = (NewtView *) nsview;
    jobject javaWindowObject = [view getJavaWindowObject];
    JNIEnv* env = [view getJNIEnv];
    if (env==NULL || javaWindowObject == NULL) {
        return;
    }

    jint javaMods = mods2JavaMods([event modifierFlags]);
    NSRect frameRect = [self frame];
    NSRect contentRect = [self contentRectForFrameRect: frameRect];
    // NSPoint location = [event locationInWindow];
    // The following computation improves the behavior of mouse drag
    // events when they also affect the location of the window, but it
    // still isn't perfect
    NSPoint curLocation = [NSEvent mouseLocation];
    NSPoint location = NSMakePoint(curLocation.x - frameRect.origin.x,
                                   curLocation.y - frameRect.origin.y);

    // convert to 1-based button number (or use zero if no button is involved)
    // TODO: currently we don't detect the mouse button masks properly:
    // we need to track all buttons. For example, we don't set the modifiers
    // when wheel is scrolled with mouse button pressed
    jint javaButtonNum = 0;
    jint scrollDeltaY = 0;
    switch ([event type]) {
    case NSScrollWheel: {
        scrollDeltaY = GetDeltaY(event, javaMods);
        if (scrollDeltaY == 0) {
            // ignore 0 increment wheel scroll events
            return;
        }
        break;
    }
    case NSLeftMouseDown:
         mouseButtonsDown |= EVENT_BUTTON1_MASK;
    case NSLeftMouseDragged:
        javaMods |= EVENT_BUTTON1_MASK;
        javaButtonNum = 1;
	break;
    case NSLeftMouseUp:
        mouseButtonsDown &= ~EVENT_BUTTON1_MASK;
        javaButtonNum = 1;
        break;
    case NSRightMouseDown:
         mouseButtonsDown |= EVENT_BUTTON3_MASK;
    case NSRightMouseDragged:
        javaMods |= EVENT_BUTTON3_MASK;
        javaButtonNum = 3;
        break;
    case NSRightMouseUp:
        mouseButtonsDown &= ~EVENT_BUTTON3_MASK;
        javaButtonNum = 3;
        break;
    case NSOtherMouseDown:
         mouseButtonsDown |= EVENT_BUTTON2_MASK;
    case NSOtherMouseDragged:
        javaMods |= EVENT_BUTTON2_MASK;
        javaButtonNum = 2;
        break;
    case NSOtherMouseUp:
        mouseButtonsDown &= ~EVENT_BUTTON2_MASK;
        javaButtonNum = 2;
        break;
    case NSMouseEntered:
    case NSMouseExited:
        //We may within a drag sequense here. For that case we add cached Java modifiers into the javaMods.
        javaMods |= mouseButtonsDown;
        break;
    default:
        javaButtonNum = 0;
        break;
    }

    (*env)->CallVoidMethod(env, javaWindowObject, sendMouseEventID,
                           evType, javaMods,
                           (jint) location.x,
                           (jint) (contentRect.size.height - location.y),
                           javaButtonNum, scrollDeltaY);
}

- (void) mouseMoved: (NSEvent*) theEvent
{
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_MOVED];
}

- (void) scrollWheel: (NSEvent*) theEvent
{
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_WHEEL_MOVED];
}

- (void) mouseDown: (NSEvent*) theEvent
{
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_PRESSED];
}

- (void) mouseDragged: (NSEvent*) theEvent
{
    // Note use of MOUSE_MOVED event type because mouse dragged events are synthesized by Java
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_MOVED];
}

- (void) mouseUp: (NSEvent*) theEvent
{
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_RELEASED];
}

- (void) rightMouseDown: (NSEvent*) theEvent
{
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_PRESSED];
}

- (void) rightMouseDragged: (NSEvent*) theEvent
{
    // Note use of MOUSE_MOVED event type because mouse dragged events are synthesized by Java
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_MOVED];
}

- (void) rightMouseUp: (NSEvent*) theEvent
{
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_RELEASED];
}

- (void) otherMouseDown: (NSEvent*) theEvent
{
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_PRESSED];
}

- (void) otherMouseDragged: (NSEvent*) theEvent
{
    // Note use of MOUSE_MOVED event type because mouse dragged events are synthesized by Java
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_MOVED];
}

- (void) otherMouseUp: (NSEvent*) theEvent
{
    [self sendMouseEvent: theEvent eventType: EVENT_MOUSE_RELEASED];
}

- (void)windowDidResize: (NSNotification*) notification
{
    NSView* nsview = [self contentView];
    if( ! [nsview isMemberOfClass:[NewtView class]] ) {
        return;
    }
    NewtView* view = (NewtView *) nsview;
    [view updateTrackingRect];
    jobject javaWindowObject = [view getJavaWindowObject];
    JNIEnv* env = [view getJNIEnv];
    if (env==NULL || javaWindowObject == NULL) {
        return;
    }

    // update insets on every window resize for lack of better hook place
    [self updateInsets: env];

    NSRect frameRect = [self frame];
    NSRect contentRect = [self contentRectForFrameRect: frameRect];

    (*env)->CallVoidMethod(env, javaWindowObject, sizeChangedID,
                           (jint) contentRect.size.width,
                           (jint) contentRect.size.height);
}

- (void)windowDidMove: (NSNotification*) notification
{
    NSView* nsview = [self contentView];
    if( ! [nsview isMemberOfClass:[NewtView class]] ) {
        return;
    }
    NewtView* view = (NewtView *) nsview;
    jobject javaWindowObject = [view getJavaWindowObject];
    JNIEnv* env = [view getJNIEnv];
    if (env==NULL || javaWindowObject == NULL) {
        return;
    }

    NSRect rect = [self contentRectForFrameRect: [self frame]];
    NSScreen* screen = NULL;
    NSRect screenRect;
    NSPoint pt;

    screen = [self screen];
    // this allows for better compatibility with awt behavior
    screenRect = [screen frame];
    pt = NSMakePoint(rect.origin.x, 
                     screenRect.origin.y + screenRect.size.height - 
                     rect.origin.y - rect.size.height);

    (*env)->CallVoidMethod(env, javaWindowObject, positionChangedID,
                           (jint) pt.x, (jint) pt.y);
}

- (void)windowWillClose: (NSNotification*) notification
{
    NSView* nsview = [self contentView];
    if( ! [nsview isMemberOfClass:[NewtView class]] ) {
        return;
    }
    NewtView* view = (NewtView *) nsview;
    jobject javaWindowObject = [view getJavaWindowObject];
    JNIEnv* env = [view getJNIEnv];
    if (env==NULL || javaWindowObject == NULL) {
        return;
    }

    (*env)->CallVoidMethod(env, javaWindowObject, windowDestroyNotifyID);
    // Will be called by Window.java (*env)->CallVoidMethod(env, javaWindowObject, windowDestroyedID);

    // EOL ..
    (*env)->DeleteGlobalRef(env, javaWindowObject);
    [view setJavaWindowObject: NULL];
}

- (void) windowDidBecomeKey: (NSNotification *) notification
{
    NSView* nsview = [self contentView];
    if( ! [nsview isMemberOfClass:[NewtView class]] ) {
        return;
    }
    NewtView* view = (NewtView *) nsview;
    jobject javaWindowObject = [view getJavaWindowObject];
    JNIEnv* env = [view getJNIEnv];
    if (env==NULL || javaWindowObject == NULL) {
        return;
    }

    (*env)->CallVoidMethod(env, javaWindowObject, focusChangedID, JNI_TRUE);
}

- (void) windowDidResignKey: (NSNotification *) notification
{
    NSView* nsview = [self contentView];
    if( ! [nsview isMemberOfClass:[NewtView class]] ) {
        return;
    }
    NewtView* view = (NewtView *) nsview;
    jobject javaWindowObject = [view getJavaWindowObject];
    JNIEnv* env = [view getJNIEnv];
    if (env==NULL || javaWindowObject == NULL) {
        return;
    }

    (*env)->CallVoidMethod(env, javaWindowObject, focusChangedID, JNI_FALSE);
}

@end
