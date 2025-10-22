plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // El plugin de Compose no es necesario aquí si se usa el BOM
}

android {
    namespace = "com.gameverse"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.gameverse"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8 // Cambiado a 1.8, estándar para Compose
        targetCompatibility = JavaVersion.VERSION_1_8 // Cambiado a 1.8
    }
    kotlinOptions {
        jvmTarget = "1.8" // Cambiado a 1.8
    }
    buildFeatures {
        // REMOVED: viewBinding no es necesario
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // Versión compatible con el BOM más reciente
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // --- BOM (Bill of Materials) para gestionar versiones de Compose ---
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // --- Dependencias Core y de UI de Compose ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    // --- ViewModel y Navegación para Compose ---
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // --- Coroutines (sigue siendo necesario) ---
    implementation(libs.kotlinx.coroutines.android)

    // --- Coil para cargar imágenes en Compose ---
    implementation(libs.coil.compose)

    // --- Pruebas (Testing) ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // --- DEPENDENCIAS ANTIGUAS ELIMINADAS ---
    // implementation("androidx.lifecycle:lifecycle-livedata-ktx:...")
    // implementation("androidx.fragment:fragment-ktx:...")
    // implementation("androidx.navigation:navigation-fragment-ktx:...")
    // implementation("androidx.navigation:navigation-ui-ktx:...")
    // implementation("com.github.bumptech.glide:glide:...")
    // implementation("com.google.android.material:material:...")
}
