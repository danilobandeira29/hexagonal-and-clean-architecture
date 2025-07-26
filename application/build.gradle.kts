plugins {
    `java-conventions`
    `java-library`
}

group = "br.com.danilobandeira29.application"

dependencies {
    implementation(project(":domain"))
}
