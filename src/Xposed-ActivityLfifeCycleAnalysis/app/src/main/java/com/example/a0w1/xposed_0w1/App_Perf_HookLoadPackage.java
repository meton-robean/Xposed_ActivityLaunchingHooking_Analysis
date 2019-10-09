package com.example.a0w1.xposed_0w1;

/**
 * Created by 0w1 on 2018/6/1.
 */

import android.content.pm.ActivityInfo;
import android.content.pm.ShortcutInfo;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class App_Perf_HookLoadPackage implements IXposedHookLoadPackage {
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        if (lpparam.packageName.equals("com.android.launcher")){
            XposedBridge.log("Loaded app: " + lpparam.packageName);

            findAndHookMethod("com.android.launcher2.Launcher", lpparam.classLoader,
                    "onClick",
                    View.class, new XC_MethodHook() {

                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            // this will be called before the clock was updated by the original method
                            XposedBridge.log("step0: Launcher-ON_CLICK: "+ System.currentTimeMillis());
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            // this will be called after the clock was updated by the original method
                        }


            });
        }
    }
}

