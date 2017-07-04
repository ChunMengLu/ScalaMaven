package net.dreamlu.scala.handler

import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher._
import org.springframework.web.reactive.function.server.ServerResponse
import net.dreamlu.scala.model.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import net.dreamlu.scala.repository.UserRepository

@Service
class UserHandler(private val userRepository: UserRepository) {
  
  def handleGetUsers(request: ServerRequest): Mono[ServerResponse] = {
    val userList = userRepository.findAll()
    ServerResponse.ok().body(Flux.fromIterable(userList), classOf[User])
  }
  
  def handleGetUserById(request: ServerRequest): Mono[ServerResponse] = {
    val user = userRepository.findById(request.pathVariable("id").toLong)
    Mono.justOrEmpty(user.orElse(null))
      .flatMap(user => ServerResponse.ok().body(Mono.just(user), classOf[User]))
      .switchIfEmpty(ServerResponse.notFound().build())
  }
  
  def handlePostUser(request: ServerRequest): Mono[ServerResponse] = {
    val entity = request.bodyToMono(classOf[User]).block()
    userRepository.save(entity)
    ServerResponse.ok().body(Mono.just(entity), classOf[User])
  }
}