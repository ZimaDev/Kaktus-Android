-dontobfuscate

# RetroLambda
-dontwarn java.lang.invoke**

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.dubai.fa.model.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Retrofit 2.X
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# JSOUP
-keeppackagenames org.jsoup.nodes

# Junit
-dontwarn android.test.**

#Picasso
-dontwarn com.squareup.okhttp.**

-dontwarn **$$Lambda$*

#Improve Crashlytics reports
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

# StatefulLayout
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }