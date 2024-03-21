val serverDir = "${System.getProperty("user.home")}\\Desktop\\minedasantigas"

plugins {
    id("java")
}

group = "brunorsch.minedasantigas"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files("$serverDir\\craftbukkit.jar"))
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