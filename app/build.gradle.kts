plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.opscpoe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.opscpoe"
        minSdk = 32
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.google.android.material:material:1.11.0")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("com.applandeo:material-calendar-view:1.9.2")
    implementation("com.jakewharton:butterknife:10.2.3")
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("com.github.bumptech.glide:glide:5.0.0-rc01")
    implementation("com.github.bumptech.glide:okhttp3-integration:5.0.0-rc01")
    implementation("androidx.activity:activity:1.9.0")
    implementation("com.applandeo:material-calendar-view:1.9.2")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation ("io.github.inflationx:calligraphy3:3.1.1")
    implementation ("io.github.inflationx:viewpump:2.1.1")

    //ButterKnife
    implementation ("com.jakewharton:butterknife:10.2.3")
    annotationProcessor ("com.jakewharton:butterknife-compiler:10.2.3")

    //Room Database
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")


    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    implementation("com.github.bumptech.glide:okhttp3-integration:4.16.0") {
        exclude(group = "glide-parent")
    }
    //noinspection GradleDependency
    implementation ("com.applandeo:material-calendar-view:1.9.2")

    implementation ("com.jakewharton.timber:timber:5.0.1")





}