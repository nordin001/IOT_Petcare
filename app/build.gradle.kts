plugins {
    id("com.android.application")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.example.sprint_1_nuevo_15"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sprint_1_nuevo_15"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled =true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

implementation ("com.android.support:multidex:1.0.3")

    implementation("com.google.firebase:firebase-firestore:24.9.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.firebase:firebase-bom:32.4.0")
    implementation("com.firebaseui:firebase-ui-auth:7.1.1")
    //lo he añadido para la verificacion del email
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    //foto del usuario
    implementation("com.android.volley:volley:1.2.1")
    implementation ("com.google.firebase:firebase-auth:21.0.0") // Replace with the latest version
    //frestore
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    //recycler Firestore
    implementation("com.firebaseui:firebase-ui-firestore:8.0.2")
}