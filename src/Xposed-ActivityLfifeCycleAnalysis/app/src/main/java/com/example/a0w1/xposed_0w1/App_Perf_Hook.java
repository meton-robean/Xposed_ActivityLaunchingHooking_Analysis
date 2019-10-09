package com.example.a0w1.xposed_0w1;

/**
 * Created by 0w1 on 2018/6/1.
 */

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import android.view.View;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class App_Perf_Hook implements IXposedHookZygoteInit {
    public void initZygote (StartupParam startupParam) throws Throwable {
        XposedBridge.log("ZygoteInit: " + startupParam.modulePath);

        findAndHookMethod("android.os.BinderProxy", null,"transact",
                            int.class, Parcel.class, Parcel.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        int code = (int)param.args[0];                 //读取目标方法的第一个参数code
                        Log.d("code: ", ""+code);

                        //步骤1
                        if( code == IBinder.FIRST_CALL_TRANSACTION+2){ //判断第一个参数是否为“START_ACTIVITY_TRANSACTION”
                            Parcel data = (Parcel)param.args[1];       //读取目标方法第二个参数data（parcel类）
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();

                            //Log.d("cmt1", TokenString);

                            if(TokenString.equals("android.app.IActivityManager")){
                                IBinder StrongBinder = data.readStrongBinder();
                                String  callingPackage = data.readString();
                                Intent intent = new Intent();
                                try{
                                    intent = Intent.CREATOR.createFromParcel(data);
                                }catch (Exception e){
                                }
                                String intentString = intent.toString();
                                if (intentString.contains("android.intent.category.LAUNCHER")){ //LAUNCHER 的 intent

                                    XposedBridge.log("step1: Launcher send #transact.START_ACTIVITY_TRANSACTION# to AMS : "
                                                      +System.currentTimeMillis());
                                }
                            }
                            data.setDataPosition(dataPositionInit);
                        }

                        //步骤3
                        if( code == IBinder.FIRST_CALL_TRANSACTION){    //是否为SCHEDULE_PAUSE_ACTIVITY_TRANSACTION

                            Parcel data = (Parcel)param.args[1];
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();

                            if(TokenString.equals("android.app.IApplicationThread")){   //@cmt ApplicationThread在AMS这边
                                XposedBridge.log("step3: AMS发送 #transact.SCHEDULE_PAUSE_ACTIVITY_TRANSACTION# 给 Launcher : "+System.currentTimeMillis());

                                IBinder StrongBinder = data.readStrongBinder();
                            }

                            data.setDataPosition(dataPositionInit);
                        }

                        //步骤5
                        if( code == IBinder.FIRST_CALL_TRANSACTION+18){    //是否为ACTIVITY_PAUSED_TRANSACTION

                            Parcel data = (Parcel)param.args[1];
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();

                            if(TokenString.equals("android.app.IActivityManager")){
                                XposedBridge.log("step5: Launcher 发送 #transact.ACTIVITY_PAUSED_TRANSACTION# 给 AMS : "+System.currentTimeMillis());

                                IBinder StrongBinder = data.readStrongBinder();
                            }

                            data.setDataPosition(dataPositionInit);
                        }

                        //步骤9
                        if( code == IBinder.FIRST_CALL_TRANSACTION+16){    //是否为ATTACH_APPLICATION_TRANSACTION

                            Parcel data = (Parcel)param.args[1];
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();

                            if(TokenString.equals("android.app.IActivityManager")){
                                XposedBridge.log("step9: Activity发送 #transact.ATTACH_APPLICATION_TRANSACTION# 给AMS,通知进程启动工作完成 : "+System.currentTimeMillis());

                                IBinder StrongBinder = data.readStrongBinder();
                            }

                            data.setDataPosition(dataPositionInit);
                        }

                        //步骤11
                        if( code == IBinder.FIRST_CALL_TRANSACTION+6){    //是否为SCHEDULE_LAUNCH_ACTIVITY_TRANSACTION

                            Parcel data = (Parcel)param.args[1];
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();

                            if(TokenString.equals("android.app.IApplicationThread")){
                                Intent intent = Intent.CREATOR.createFromParcel(data);
                                String intentString = intent.toString();

                                if (intentString.contains("android.intent.category.LAUNCHER")  ){ //& intentString.contains("com.danielkim.soundrecorder")
                                    XposedBridge.log("step11: AMS 发送组件信息以及#transact.SCHEDULE_LAUNCH_ACTIVITY_TRANSACTION# 给Activity进程: "+System.currentTimeMillis());
                                }
                            }

                            data.setDataPosition(dataPositionInit);
                        }

                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called after the clock was updated by the original method
                    }
        });


        //位于AMS进程中...
        findAndHookMethod("android.app.ActivityManagerNative", null,"onTransact",
                int.class, Parcel.class, Parcel.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called before the clock was updated by the original method

                        int code = (int)param.args[0];

                        //步骤2
                        if((int)param.args[0] == IBinder.FIRST_CALL_TRANSACTION+2){//是否为START_ACTIVITY_TRANSACTION

                            Parcel data = (Parcel)param.args[1];
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();
                            if(TokenString.equals("android.app.IActivityManager")){ //位于AMS的进程
                                IBinder StrongBinder = data.readStrongBinder();
                                String callingPackage = data.readString();
                                Intent intent = new Intent();
                                try{
                                    intent = Intent.CREATOR.createFromParcel(data);
                                }catch (Exception e){
                                }
                                String intentString = intent.toString();

                                if (intentString.contains("android.intent.category.LAUNCHER")){ // & intentString.contains("com.danielkim.soundrecorder")
                                    XposedBridge.log("step2: AMS 收到 Launcher 的 #onTransact.START_ACTIVITY_TRANSACTION# 请求 : "+System.currentTimeMillis());
                                }
                            }


                            data.setDataPosition(dataPositionInit);
                        }

                        //步骤6
                        if( code == IBinder.FIRST_CALL_TRANSACTION+18){    //是否为ACTIVITY_PAUSED_TRANSACTION

                            Parcel data = (Parcel)param.args[1];
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();

                            if(TokenString.equals("android.app.IActivityManager")){
                                XposedBridge.log("step6: AMS收到 #onTransact.ACTIVITY_PAUSED_TRANSACTION# : "+System.currentTimeMillis());
                            }

                            data.setDataPosition(dataPositionInit);
                        }

                        //步骤10
                        if( code == IBinder.FIRST_CALL_TRANSACTION+16){    //是否为ATTACH_APPLICATION_TRANSACTION

                            Parcel data = (Parcel)param.args[1];
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();

                            if(TokenString.equals("android.app.IActivityManager")){
                                XposedBridge.log("step10: AMS 收到 #onTransact.ATTACH_APPLICATION_TRANSACTION# : "+System.currentTimeMillis());
                            }

                            data.setDataPosition(dataPositionInit);
                        }

                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called after the clock was updated by the original method
                    }
        });


        //位于Activity进程中....
        findAndHookMethod("android.app.ApplicationThreadNative", null,"onTransact",
                int.class, Parcel.class, Parcel.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called before the clock was updated by the original method

                        int code = (int)param.args[0];

                        //步骤4
                        if( code == IBinder.FIRST_CALL_TRANSACTION){    //是否为SCHEDULE_PAUSE_ACTIVITY_TRANSACTION

                            Parcel data = (Parcel)param.args[1];
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();
                            IBinder StrongBinder = data.readStrongBinder();

                            if(TokenString.equals("android.app.IApplicationThread")){
                                XposedBridge.log("step4: Launcher 收到 #onTransact.SCHEDULE_PAUSE_ACTIVITY_TRANSACTION# : "+System.currentTimeMillis());
                            }

                            data.setDataPosition(dataPositionInit);
                        }

                        //步骤12
                        if( code == IBinder.FIRST_CALL_TRANSACTION+6){    //是否为SCHEDULE_LAUNCH_ACTIVITY_TRANSACTION

                            Parcel data = (Parcel)param.args[1];
                            int dataPositionInit = data.dataPosition();
                            data.setDataPosition(0);

                            int TokenInt = data.readInt();
                            String TokenString = data.readString();

                            if(TokenString.equals("android.app.IApplicationThread")){ //在待启动的Activity进程中

                                Intent intent = Intent.CREATOR.createFromParcel(data);
                                String intentString = intent.toString();

                                if (intentString.contains("android.intent.category.LAUNCHER") ){ //& intentString.contains("com.danielkim.soundrecorder")
                                    XposedBridge.log("step12: Activity进程收到 #onTransact.SCHEDULE_LAUNCH_ACTIVITY_TRANSACTION# 开始加载组件 : "+System.currentTimeMillis());
                                    XposedBridge.log("------------------------------------------------------------------");
                                }
                            }

                            data.setDataPosition(dataPositionInit);
                        }

                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called after the clock was updated by the original method
                    }
        });


        //步骤7
        findAndHookMethod("android.os.Process", null,"start", String.class,
                String.class, int.class, int.class, int[].class, int.class, int.class,
                int.class, String.class, String.class, String.class, String.class, String[].class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called before the clock was updated by the original method
                        XposedBridge.log("step7: AMS调用 Process.start方法 : "+System.currentTimeMillis());
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called after the clock was updated by the original method
                    }
        });


        //步骤8
        findAndHookMethod("android.app.ActivityThread", null,
                "main", String[].class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called before the clock was updated by the original method
                        XposedBridge.log("step8: Activity调用进入 ActivityThread.main : "+System.currentTimeMillis());
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // this will be called after the clock was updated by the original method
                    }
        });


    }
}

