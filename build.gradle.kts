// Top-level build file where you can add configuration options common to all sub-projects/modules.
// CORRECTO en el build.gradle.kts de la raíz
plugins {
    // Usa alias para ambos plugins para leer la versión desde libs.versions.toml
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false // <-- CÁMBIALO A ESTA LÍNEA
}