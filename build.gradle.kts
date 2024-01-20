import org.cadixdev.gradle.licenser.LicenseExtension

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("org.cadixdev.licenser") version "0.6.1"
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle(project.properties["paper_build"].toString()) // gradle.properties
    compileOnly(fileTree("libs/compile"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.named<JavaCompile>("compileJava") {
    options.encoding = "UTF-8"
}

tasks.named<Copy>("processResources") {
    filteringCharset = "UTF-8"
    filesMatching("coprolite.plugin.json") {
        expand("version" to version)
    }
}

tasks.named("assemble").configure {
    dependsOn("reobfJar")
}

tasks.test {
    useJUnitPlatform()
}

configure<LicenseExtension> {
    header(rootProject.file("HEADER"))
    include("**/*.java")
}
