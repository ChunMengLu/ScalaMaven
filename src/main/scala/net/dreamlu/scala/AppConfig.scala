package net.dreamlu.scala

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.RouterFunctions.resources
import org.springframework.web.reactive.function.server.ServerResponse

import net.dreamlu.scala.handler.UserHandler
import reactor.ipc.netty.http.server.HttpServer

/**
 * 应用配置
 */
@SpringBootApplication
class AppConfig (
    private val userHandler: UserHandler,
    private val environment: Environment) {
  private val logger = LoggerFactory.getLogger(classOf[AppConfig])
  
  @Bean
  def routerFunction(): RouterFunction[ServerResponse] = {
    logger.info("===========init route===========")
    resources("/static/**", new ClassPathResource("/static/"))
      .andRoute(GET("/api/user"), userHandler.handleGetUsers)
      .andRoute(GET("/api/user/{id}"), userHandler.handleGetUserById)
      .andRoute(POST("/api/user"), userHandler.handlePostUser)
  }
  
  @Bean
  def httpServer(routerFunction: RouterFunction[ServerResponse]): HttpServer = {
    logger.info("===========init httpServer===========")
    var httpHandler = RouterFunctions.toHttpHandler(routerFunction)
    var adapter = new ReactorHttpHandlerAdapter(httpHandler)
    var server = HttpServer.create(environment.getProperty("server.port").toInt)
    server.newHandler(adapter);
    server
  }
}