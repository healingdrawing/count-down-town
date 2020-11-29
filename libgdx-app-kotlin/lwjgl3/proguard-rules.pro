# proguard github example stuff for application
# https://github.com/Guardsquare/proguard/blob/master/examples/gradle/applications.gradle
-keepattributes '*Annotation*'
#-libraryjars '/usr/lib/jvm/jdk1.8.0_181/jre/lib/rt.jar'

-keepclasseswithmembers public class * { public static void main(java.lang.String[]); }

-keepclasseswithmembernames class * { native <methods>; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements java.io.Serializable {
         static final long serialVersionUID;
         static final java.io.ObjectStreamField[] serialPersistentFields;
         private void writeObject(java.io.ObjectOutputStream);
         private void readObject(java.io.ObjectInputStream);
         java.lang.Object writeReplace();
         java.lang.Object readResolve();
     }

 # mr.anderson stuff

-keep public class io.guthub.healingdrawing.countdowntown.lwjgl3.Lwjgl3Launcher {
    public static void main(java.lang.String[]);
}

#-keep public class !io.github.healingdrawing.countdowntown** { *; }
-keep public class !io.guthub.healingdrawing.countdowntown**,!cfg**,!saveload**,!timer**,!tune** { *; }

-forceprocessing
#-classobfuscationdictionary 'obfuscationClassNames.txt'
-ignorewarnings
-overloadaggressively
-mergeinterfacesaggressively
-repackageclasses ''
-allowaccessmodification

# FIELD ISSUE NPE
-optimizations !field/propagation/value

# LAMBDA FIX
-keepclassmembernames class * {
    private static synthetic *** lambda$*(...);
}
-dontoptimize
###### PROGUARD ANNOTATIONS END #####
-optimizationpasses 5