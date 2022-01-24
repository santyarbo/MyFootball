//
// Created by Santi  Rodriguez Lorenzo on 2/15/21.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_es_santyarbo_myfootball_data_server_ApiKeyRetriever_getFooApiKey(JNIEnv* env, jobject /* this */) {
    std::string apiKey =  "8695ef4873mshd7b05b62256bb67p142921jsnad04cab8aba8";
    return env->NewStringUTF(apiKey.c_str());
}