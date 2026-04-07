#!/system/bin/sh
am force-stop com.tencent.mm
am force-stop com.tencent.mobileqq
am force-stop com.tencent.tim

rm -rf /data/data/com.tencent.mm/cache/*
rm -rf /data/data/com.tencent.mm/code_cache/*
rm -rf /data/data/com.tencent.mobileqq/cache/*
rm -rf /data/data/com.tencent.mobileqq/code_cache/*
rm -rf /data/data/com.tencent.tim/cache/*
rm -rf /data/data/com.tencent.tim/code_cache/*

rm -rf /data/local/tmp/tsbattery*
rm -rf /data/local/tmp/yukihook*

sync
echo "✅ 清理完成，无残留防检测"
