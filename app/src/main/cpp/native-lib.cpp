#include <jni.h>
#include <string>
#include<math.h>

extern "C" JNIEXPORT jlong JNICALL
Java_id_ac_ui_cs_mobileprogramming_michaelwiryadinatahalim_chatapp_ui_chat_ChatFragment_fibonacci(
        JNIEnv* env,
        jobject thiz,
        jlong input) {
    double phi = (1 + sqrt(5)) / 2;
    return round(pow(phi, input) / sqrt(5));
}
