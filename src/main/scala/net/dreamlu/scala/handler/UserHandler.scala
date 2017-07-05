package net.dreamlu.scala.handler

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

import net.dreamlu.scala.model.User
import net.dreamlu.scala.repository.UserRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.Validator
import org.springframework.web.bind.WebDataBinder
import org.springframework.validation.BindException
import org.springframework.validation.ObjectError
import org.springframework.validation.FieldError

@Service
class UserHandler(private val userRepository: UserRepository) {
  @Autowired var validator:Validator = _
  
  def handleGetUsers(request: ServerRequest): Mono[ServerResponse] = {
    ServerResponse.ok().body(Flux.fromIterable(userRepository.findAll()), classOf[User]).log()
  }
  
  def handleGetUserById(request: ServerRequest): Mono[ServerResponse] = {
    val id = request.pathVariable("id").toLong
    Mono.justOrEmpty(userRepository.findById(id).orElse(null))
      .flatMap(user => ServerResponse.ok().body(Mono.just(user), classOf[User]))
      .switchIfEmpty(ServerResponse.notFound().build()).log()
  }
  
  def handlePostUser(request: ServerRequest): Mono[ServerResponse] = {
    val entity = request.bodyToMono(classOf[User]).block()
    val bindingResult = new WebDataBinder(entity).getBindingResult
    validator.validate(entity, bindingResult)
    if (bindingResult.hasFieldErrors()) {
//      throw new BindException(bindingResult)
       ServerResponse.badRequest().body(Mono.just(bindingResult.getFieldError), classOf[FieldError]).log()
    } else {
       ServerResponse.ok().body(Mono.just(userRepository.save(entity)), classOf[User]).log()
    }
  }
}