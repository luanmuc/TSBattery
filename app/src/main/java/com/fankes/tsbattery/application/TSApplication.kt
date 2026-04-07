package com.fankes.tsbattery.application

import com.fankes.tsbattery.hook.HookManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class TSApplication : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        // 防检测核心逻辑（原生实现，无依赖）
        runCatching {
            when (lpparam.packageName) {
                "com.tencent.mm" -> {
                    val weChatChecker = lpparam.classLoader.loadClass("com.tencent.mm.util.XposedChecker")
                    weChatChecker.declaredMethods.forEach { method ->
                        if (method.returnType == Boolean::class.java) {
                            method.isAccessible = true
                            method.invoke(null, false)
                        }
                    }
                }
                "com.tencent.qq", "com.tencent.tim" -> {
                    val qqChecker = lpparam.classLoader.loadClass("com.tencent.qphone.base.util.XposedUtil")
                    qqChecker.declaredMethods.forEach { method ->
                        if (method.returnType == Boolean::class.java) {
                            method.isAccessible = true
                            method.invoke(null, false)
                        }
                    }
                }
            }
        }

        // 保留原项目的Hook逻辑
        HookManager.init(lpparam)
    }
}
