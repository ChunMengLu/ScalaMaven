package net.dreamlu.scala.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import net.dreamlu.scala.model.User
import net.dreamlu.scala.repository.UserRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import net.dreamlu.scala.common.Results

@RestController
class TestController(private val userRepository: UserRepository) {

  @GetMapping(Array("test/users.json"))
  def handleGetUsers: Object = {
    // 直接返回对象不使用Flux包装会自动使用Mono.justOrEmpty(body)包装
    // AbstractMessageWriterResultHandler.writeBody 90行
    // userRepository.findAll()
    Results.success(userRepository.findAll);
  }

  @GetMapping(Array("test/user/{id}"))
  def handleGetUserById(@PathVariable id: Long): Object = {
    Results.success(userRepository.findById(id))
  }

  @PostMapping(Array("test/user"))
  def handlePostUser(@Valid user: User): Object = {
    Results.success(userRepository.save(user))
  }

}