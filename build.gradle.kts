plugins {
    java
    application
}

group = "com.ll"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.ll.Main")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// 컴파일 옵션 추가
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(14)
}


// 한글 인코딩 설정
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

// run task에 입력 인코딩 추가
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    systemProperty("file.encoding", "UTF-8")
    systemProperty("console.encoding", "UTF-8")
}
