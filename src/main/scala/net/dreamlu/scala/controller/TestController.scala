package net.dreamlu.scala.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import net.dreamlu.scala.model.User
import net.dreamlu.scala.repository.UserRepository
import reactor.core.publisher.Flux

@RestController
class TestController(private val userRepository: UserRepository) {

  @GetMapping(Array("index.json"))
  def handleGetUsers: Object = {
    // 直接返回对象不使用Flux包装会自动使用Mono.justOrEmpty(body)包装
    // AbstractMessageWriterResultHandler.writeBody 90行
    // userRepository.findAll()
    Flux.fromIterable(userRepository.findAll).log()
  }

}