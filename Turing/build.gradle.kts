plugins {
  kotlin("jvm") version "1.9.22"
  application
}

group = "dev.bryant"
version = "1.0"


repositories {
  mavenCentral()
}

dependencies {
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
}

application {
  mainClass.set("MainKt")
}