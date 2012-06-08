LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := mfengine_shared

LOCAL_MODULE_FILENAME := libmfengine


LOCAL_STATIC_LIBRARIES := png_static_prebuilt
LOCAL_STATIC_LIBRARIES += xml2_static_prebuilt
LOCAL_STATIC_LIBRARIES += jpeg_static_prebuilt
LOCAL_WHOLE_STATIC_LIBRARIES += cocos2dx_static
                         
#############################################################################
# zhangyifei append
#############################################################################
# EngineCocos2Dx/Lib
MF_LIB_FILES	:=$(wildcard $(LOCAL_PATH)/Lib/*.cpp)
MF_LIB_FILES	:=$(MF_LIB_FILES:$(LOCAL_PATH)/%=%)

# EngineCocos2Dx/Engine
MF_ENGINE_FILES	:=$(wildcard $(LOCAL_PATH)/Engine/*.cpp)
MF_ENGINE_FILES	:=$(MF_ENGINE_FILES:$(LOCAL_PATH)/%=%)
#############################################################################
 
LOCAL_SRC_FILES := 	$(MF_ENGINE_FILES) \
					$(MF_LIB_FILES)

		   
LOCAL_EXPORT_C_INCLUDES := 	$(LOCAL_PATH)/ \
							$(LOCAL_PATH)/Engine/ \
                   			$(LOCAL_PATH)/Lib/
                   	
LOCAL_C_INCLUDES :=			$(LOCAL_PATH)/ \
							$(LOCAL_PATH)/Engine/ \
                   			$(LOCAL_PATH)/Lib/
                   			
                   			
include $(BUILD_STATIC_LIBRARY)

$(call import-module,cocos2dx/platform/third_party/android/modules/libpng)
$(call import-module,cocos2dx/platform/third_party/android/modules/libxml2)
$(call import-module,cocos2dx/platform/third_party/android/modules/libjpeg)
