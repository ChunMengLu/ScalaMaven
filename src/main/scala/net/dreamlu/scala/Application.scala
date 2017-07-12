package net.dreamlu.scala

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * 项目配置
 */
@SpringBootApplication
class AppConfig

/**
 * 项目入口
 */
object Application extends App {
  SpringApplication.run(classOf[AppConfig])
}