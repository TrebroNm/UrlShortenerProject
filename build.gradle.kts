import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property

plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("nu.studer.jooq") version "8.2.1"
    id("org.flywaydb.flyway") version "9.21.1"
}

group = "com.demo"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.flywaydb:flyway-core:9.21.1")
    implementation("org.jooq:jooq")
    implementation("org.postgresql:postgresql:42.2.27")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    jooqGenerator("org.postgresql:postgresql:42.5.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
    testImplementation("org.mockito:mockito-inline:3.4.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.withType<Test> {
    useJUnitPlatform()
}

afterEvaluate {
    tasks.named("generateJooq") {
        dependsOn("flywayMigrate")
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/urlshortener"
    user = "urlshortener"
    password = "heslo"
    locations = arrayOf("filesystem:${projectDir}/src/main/resources/db/migration")
    cleanDisabled = false
}

jooq {
    version.set("3.18.5")  // default (can be omitted)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)  // default (can be omitted)

    configurations {
        create("main") {  // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(true)  // default (can be omitted)

            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/urlshortener"
                    user = "urlshortener"
                    password = "heslo"
                    properties.add(Property().apply {
                        key = "ssl"
                        value = "false"
                    })
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "com.demo.urlshortener"
                        directory = "build/generated-src/jooq/main"  // default (can be omitted)
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}
