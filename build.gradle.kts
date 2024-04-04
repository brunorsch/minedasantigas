val serverDir = "${System.getProperty("user.home")}\\Desktop\\MineDasAntigas"

plugins {
    id("java")
}

group = "brunorsch.minedasantigas"
version = "1.4.0" // Locations

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files("$serverDir\\craftbukkit.jar"))
    compileOnly("org.projectlombok:lombok:1.18.32")

    annotationProcessor("org.projectlombok:lombok:1.18.32")
}

sourceSets {
    main {
        java {
            srcDir("src/java")
        }
        resources {
            srcDir("src/resources")
        }
    }
}

tasks.processResources {
    val props = mapOf(
        "version" to version
    )

    inputs.properties(props)

    filteringCharset = "UTF-8"

    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.withType<Jar> {
    archiveFileName.set("MineDasAntigas.jar")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}