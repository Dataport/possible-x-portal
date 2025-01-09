import cz.habarta.typescript.generator.EnumMapping
import cz.habarta.typescript.generator.JsonLibrary
import cz.habarta.typescript.generator.TypeScriptFileType
import cz.habarta.typescript.generator.TypeScriptOutputKind

plugins {
  java
  alias(libs.plugins.springBoot)
  alias(libs.plugins.springDependencyManagement)
  alias(libs.plugins.typescriptGenerator)
}

group = "eu.possiblex"
version = "0.0.1"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.springBootStarterActuator)
  implementation(libs.springBootStarterWeb)
  implementation(libs.springBootStarterWebflux)
  implementation(libs.springBootStarterDataJpa)
  implementation(libs.springBootStarterSecurity)
  implementation(libs.hibernateValidator)
  implementation(libs.openApi)
  implementation(libs.titaniumJsonLd)
  implementation(libs.jakartaJson)
  implementation(libs.postgresql)
  compileOnly(libs.lombok)
  annotationProcessor(libs.lombokMapStructBinding)
  implementation(libs.mapStruct)
  annotationProcessor(libs.mapStructProcessor)
  developmentOnly(libs.springBootDevtools)
  runtimeOnly(libs.therApi)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.therApiScribe)
  testImplementation(libs.springBootStarterTest)
  testImplementation(libs.reactorTest)
  testImplementation(libs.h2)
  testImplementation(libs.springSecurityTest)
  testRuntimeOnly(libs.jUnit)
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}


tasks.bootJar {
  mainClass.set("eu.possiblex.portal.PortalApplication")
  archiveBaseName.set("backend")
}

tasks.getByName<Jar>("jar") {
  enabled = false
}

tasks.register<Copy>("copyWebApp") {
  outputs.upToDateWhen { false }
  description = "Copies the GUI into the resources of the Spring project."
  group = "Application"
  from("$rootDir/frontend/build/resources")
  into(layout.buildDirectory.dir("resources/main/static/."))
}

tasks.named("compileJava") {
  if (!project.hasProperty("backendOnly")) { // skip frontend if user wants only backend
    println("Hint: You can skip frontend build with '-PbackendOnly'")
    dependsOn(":frontend:npmTestConditional")
  }
  else {
    println("skipping frontend")
  }
}

tasks.named("processResources") {
  if (!project.hasProperty("backendOnly")) { // skip frontend if user wants only backend
    dependsOn("copyWebApp")
  }
  else {
    println("skipping frontend")
  }
}

tasks {
  generateTypeScript {
    jsonLibrary = JsonLibrary.jackson2
    outputKind = TypeScriptOutputKind.module
    outputFileType = TypeScriptFileType.implementationFile
    scanSpringApplication = true
    generateSpringApplicationClient = true
    addTypeNamePrefix = "I"
    classPatterns = listOf(
      "eu.possiblex.portal.application.entity.**",
      "eu.possiblex.portal.application.boundary.**"
    )
    outputFile = "../frontend/src/app/services/mgmt/api/backend.ts"
    noFileComment = true
    mapEnum = EnumMapping.asEnum
    importDeclarations = listOf(
      "import { HttpHeaders } from \"@angular/common/http\""
    )
  }
}