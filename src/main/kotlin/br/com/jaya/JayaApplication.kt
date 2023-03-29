package br.com.jaya

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class JayaApplication

fun main(args: Array<String>) {
    runApplication<JayaApplication>(*args)
}
