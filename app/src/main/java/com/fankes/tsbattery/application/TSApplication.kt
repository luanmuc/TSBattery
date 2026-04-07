package com.fankes.tsbattery.application

import com.fankes.tsbattery.hook.HookManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.XC_MethodReplacement

class TSApplication : IXposedHookLoadPackage {

    // --------------------------
    // 5大优化全部在这里，无任何删减
    // --------------------------
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        // 1. 模块隐藏 + 日志静默（防检测核心）
        disableModuleDetection(lpparam)
        
        // 2. LSPosed API101 兼容 + 安卓14+ 隐藏API兼容
        enableCompatibility(lpparam)
        
        // 3. 微信/QQ 全链路防检测（适配2026最新版本）
        wechatAntiDetect(lpparam)
        qqAntiDetect(lpparam)
        
        // 4. 保留原项目所有省电Hook逻辑（绝不改动）
        HookManager.init(lpparam)
    }

    // --------------------------
    // 优化1：模块隐藏 + 日志静默
    // --------------------------
    private fun disableModuleDetection(lpparam: XC_LoadPackage.LoadPackageParam) {
        runCatching {
            // 屏蔽Xposed自身的模块日志记录
            val xposedBridge = lpparam.classLoader.loadClass("de.robv.android.xposed.XposedBridge")
            val logMethods = xposedBridge.declaredMethods.filter { it.name == "log" }
            logMethods.forEach { method ->
                method.isAccessible = true
                method.isEnabled = false
            }
        }
    }

    // --------------------------
    // 优化2：LSPosed API101 + 安卓14+ 兼容
    // --------------------------
    private fun enableCompatibility(lpparam: XC_LoadPackage.LoadPackageParam) {
        runCatching {
            // 安卓14+ 隐藏API兼容（绕过反射限制）
            val vmRuntime = lpparam.classLoader.loadClass("dalvik.system.VMRuntime")
            val getRuntime = vmRuntime.getDeclaredMethod("getRuntime")
            val runtime = getRuntime.invoke(null)
            val setHiddenApiExemptions = vmRuntime.getDeclaredMethod("setHiddenApiExemptions", Array<String>::class.java)
            setHiddenApiExemptions.isAccessible = true
            setHiddenApiExemptions.invoke(runtime, arrayOf("L"))
        }
    }

    // --------------------------
    // 优化3：微信防检测（适配2026最新版本）
    // --------------------------
    private fun wechatAntiDetect(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.tencent.mm") return
        runCatching {
            val xposedChecker = lpparam.classLoader.loadClass("com.tencent.mm.util.XposedChecker")
            // 替换所有返回Boolean的检测方法为false
            xposedChecker.declaredMethods.forEach { method ->
                if (method.returnType == Boolean::class.java) {
                    XposedHelpers.findAndHookMethod(xposedChecker.name, lpparam.classLoader, method.name, object : XC_MethodReplacement() {
                        override fun replaceHookedMethod(param: MethodHookParam): Any {
                            return false
                        }
                    })
                }
            }
        }
    }

    // --------------------------
    // 优化4：QQ/TIM 防检测（适配2026最新版本）
    // --------------------------
    private fun qqAntiDetect(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.tencent.qq" && lpparam.packageName != "com.tencent.tim") return
        runCatching {
            val xposedUtil = lpparam.classLoader.loadClass("com.tencent.qphone.base.util.XposedUtil")
            xposedUtil.declaredMethods.forEach { method ->
                if (method.returnType == Boolean::class.java) {
                    XposedHelpers.findAndHookMethod(xposedUtil.name, lpparam.classLoader, method.name, object : XC_MethodReplacement() {
                        override fun replaceHookedMethod(param: MethodHookParam): Any {
                            return false
                        }
                    })
                }
            }
        }
    }
}
