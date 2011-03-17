@echo off

@wrapper.exe -t ./config/wrapper_02_log_server.conf
@wrapper.exe -t ./config/wrapper_10_role_server.conf
@wrapper.exe -t ./config/wrapper_20_scene_server_1.conf
@wrapper.exe -t ./config/wrapper_20_scene_server_2.conf
@wrapper.exe -t ./config/wrapper_30_mail_server.conf
@wrapper.exe -t ./config/wrapper_40_path_server.conf
@wrapper.exe -t ./config/wrapper_50_chat_server.conf

pause
