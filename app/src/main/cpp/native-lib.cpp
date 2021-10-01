//
// Created by fiqri on 2/27/2021.
//

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_basekotlinkoin_di_ApiModuleKt_getDevUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_basekotlinkoin_di_ApiModuleKt_getStagingUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_basekotlinkoin_di_ApiModuleKt_getReleaseUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_basekotlinkoin_di_ApiModuleKt_getDevelopmentWebUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}