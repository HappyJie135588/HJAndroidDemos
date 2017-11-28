# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#保留类名和里面的成员函数名
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers

-keepclassmembernames
#保留成员函数名
-keepclassmembers public class *extends android.view.View{
    void set*(***);
    *** get*();
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

-keepclassmembers class **.R$*{
    public static <fields>;
}
#保留类名
-keep class * implements android.os.Parcelable{
    public static final android.os.Parcelable$Creator *;
}