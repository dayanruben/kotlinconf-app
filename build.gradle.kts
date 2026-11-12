import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsPlugin
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsPlugin

plugins {
    alias(libs.plugins.aboutLibraries) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinPowerAssert) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.jib) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.metro) apply false
}

// Pin Node.js to the last 22.x LTS. Node 24/25 prebuilt linux-x64 binaries
// require GLIBC newer than what some CI images provide (see GLIBC_2.27 errors).
val pinnedNodeVersion = "22.22.0"
allprojects {
    plugins.withType<NodeJsPlugin> {
        extensions.configure<NodeJsEnvSpec> { version.set(pinnedNodeVersion) }
    }
    plugins.withType<WasmNodeJsPlugin> {
        extensions.configure<WasmNodeJsEnvSpec> { version.set(pinnedNodeVersion) }
    }
}

plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("ws", "8.21.0")
        resolution("diff", "8.0.3")
        resolution("body-parser", "1.20.9")
        resolution("serialize-javascript", "7.1.4")
        resolution("fast-uri", "3.1.9")
        resolution("follow-redirects", "1.16.3")
        resolution("glob", "14.0.6")
        resolution("minimatch", "11.0.8")
    }
}
plugins.withType<org.jetbrains.kotlin.gradle.targets.wasm.yarn.WasmYarnPlugin> {
    the<org.jetbrains.kotlin.gradle.targets.wasm.yarn.WasmYarnRootExtension>().apply {
        resolution("ws", "8.21.0")
    }
}

apply(from = "gradle/releases.gradle.kts")
