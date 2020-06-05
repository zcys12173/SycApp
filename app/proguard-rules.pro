# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# 代码混淆压缩比，在0~7之间，默认为5，一般不需要更改
-optimizationpasses 5
# 混淆时不适用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclasses
# 不做预校验，preverify是proguard的4个步骤之一，android不需要做预校验，去除这一步可以加快混淆速度
-dontpreverify
# 有了verbose这句话，混淆后就会生成映射文件
-verbose
# 指定混淆时采用的算法，后面的参数是一个过滤器
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 保护代码中的Annotation不被混淆
-keepattributes *Annotation*
#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature
#忽略警告
-ignorewarnings
-keepattributes EnclosingMethod
-keepattributes InnerClasses

-dontwarn org.codehaus.**
-dontwarn java.nio.**
-dontwarn java.lang.invoke.**

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
    *;
}
-keep class * implements java.io.Serializable{
    *;
}
-keep public class * extends android.view.View {
public <init>(android.content.Context);
public <init>(android.content.Context, android.util.AttributeSet);
public <init>(android.content.Context, android.util.AttributeSet, int);
public void set*(...);
}

-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep public class **.R$*{
   public static final int *;
}
