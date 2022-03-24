package me.rav.libs.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BlogProperties::class)
class KotlinSpringDemoApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringDemoApplication>(*args)
}