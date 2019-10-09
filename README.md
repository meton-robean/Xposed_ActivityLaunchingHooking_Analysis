# Xposed_ActivityLifeCycle_Analysis
Android System Activity Launching and  LifeCycle Analysis using Xposed Hooking Framework (利用Xposed分析Activity启动底层细节与生命周期)  

这是安卓操作系统底层分析的其中一个课程作业 
### 目录介绍:
src/: exposed hooking 安卓活动启动过程的 实现  
doc/: 实验使用说明和测试分析文档  
test/: 实验用的测试app demo以及实验测试结果记录  

### 背景介绍:  
#### Activity启动的底层细节与分析  
![Activity启动流程](https://github.com/meton-robean/Xposed_ActivityLifeCycle_Analysis/blob/master/doc/Activity_Launching_Details2.png)  
![利用Xposed进行hooking与分析](https://github.com/meton-robean/Xposed_ActivityLifeCycle_Analysis/blob/master/doc/Activity_Hooking_Details.png) 
![Xposed原理说明](https://github.com/meton-robean/Xposed_ActivityLifeCycle_Analysis/blob/master/doc/xposed%E5%8E%9F%E7%90%86%E8%AF%B4%E6%98%8E.png) 

#### 要求：  
选定三个app（存放与/test/3examples下），利用Xposed框架跟踪安卓系统底层活动的启动过程，进行分析.  

### 测试结果:   
![其中一个app的活动启动环节说明](https://github.com/meton-robean/Xposed_ActivityLifeCycle_Analysis/blob/master/test/test_result_backup/pic2/FileManager.png)  

![三个app的活动各个启动环节时间分析](https://github.com/meton-robean/Xposed_ActivityLifeCycle_Analysis/blob/master/test/test_result_backup/pic2/exposed_1.png)  

耗时环节分析：  
Step 2-3中：AMS首先保存要创建的MainActivity组件的相关信息，然后再向Launcher发送一个进入终止状态的IPC请求. 如果组件信息比较复杂,可能比较耗时.  
Step 4-5中: 在ActivityThread::handlePauseActivity()涉及调用QueuedWork类的waitToFinish等待完成前面的一些数据写入磁盘的操作，将来重新进入Resumed状态时用于恢复状态数据.将数据写入磁盘保存比较耗时.  
Step 7-8中: 通过Process.start()，再一次离开了AMS,到了Linux底层进行进程创建.Zygote参与其中，并使用了binder通信机制,这一过程比较复杂.  

更多详细结果分析见/doc/README.pptx
 


