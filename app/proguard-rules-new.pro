-dontwarn **CompatHoneycomb
-dontwarn **CompatHoneycombMR2
-dontwarn **CompatCreatorHoneycombMR2

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public BaseActivity(android.content.Context, android.util.AttributeSet);
}

-keep class fr.isen.cerrone.androiderestaurant.** { *; }
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose