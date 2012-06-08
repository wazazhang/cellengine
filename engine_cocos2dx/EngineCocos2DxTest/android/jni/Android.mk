LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := game_shared

LOCAL_MODULE_FILENAME := libgame

LOCAL_SRC_FILES := helloworld/main.cpp \
                   ../../Classes/AppDelegate.cpp \
                   ../../Classes/HelloWorldScene.cpp \
                   ../../Classes/MyNode.cpp \
                   ../../../lua/cocos2dx_support/CCLuaEngine.cpp \
                   ../../../lua/cocos2dx_support/Cocos2dxLuaLoader.cpp \
                   ../../../lua/cocos2dx_support/LuaCocos2d.cpp \
                   ../../../lua/cocos2dx_support/tolua_fix.c
                   
#############################################################################
# zhangyifei append
#############################################################################
#Android NDK的例子中，每个需要编译的c/c++文件都是清晰的列在
#Android.mk文件中LOCAL_SRC_FILES变量后面的，如果文件很多，这样就不好办了。
#我们可以使用通配符来解决大量的源文件添加到Android.mk的问题：
#
#第1行是使用GNU Make函数wildcard来查找所有目录下面的文件，
#这里查找的是"当前项目路径/foo"目录下面的所有的“.c”文件，
#第2行会把所有找到的文件名中的$(LOCLA_PATH)路径给去掉，只剩下foo/*.c类似的名字。
#
#
#MY_FILES := $(wildcard $(LOCAL_PATH)/foo/*.c)
#MY_FILES := $(MY_FILES:$(LOCAL_PATH)/%=%)
#
#LOCAL_SRC_FILES += $(MY_FILES)
#############################################################################

# 包含所有lib文件
MF_LIB_FILES	:=$(wildcard $(LOCAL_PATH)/../../Lib/*.cpp)
MF_LIB_FILES	:=$(MF_LIB_FILES:$(LOCAL_PATH)/%=%)
LOCAL_SRC_FILES	+=$(MF_LIB_FILES)

# 包含所有engine文件
MF_ENGINE_FILES	:=$(wildcard $(LOCAL_PATH)/../../Engine/*.cpp)
MF_ENGINE_FILES	:=$(MF_ENGINE_FILES:$(LOCAL_PATH)/%=%)
LOCAL_SRC_FILES	+=$(MF_ENGINE_FILES)

#############################################################################

LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../Classes \
                    $(LOCAL_PATH)/../../Engine \
                    $(LOCAL_PATH)/../../Lib

#############################################################################

#LOCAL_STATIC_LIBRARIES :=
#LOCAL_SHARED_LIBRARIES :=


LOCAL_WHOLE_STATIC_LIBRARIES := cocos2dx_static cocosdenshion_static box2d_static cocos_lua_static

include $(BUILD_SHARED_LIBRARY)

$(call import-module,CocosDenshion/android) $(call import-module,cocos2dx) $(call import-module,Box2D) $(call import-module,lua/proj.android/jni)


