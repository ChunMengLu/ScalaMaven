package net.dreamlu.scala.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler

import net.dreamlu.scala.common.Result
import net.dreamlu.scala.common.Results
import reactor.core.publisher.Mono
import org.springframework.http.HttpStatus

/**
 * 此处有问题ExceptionHandler是为webmvc设计的
 * 
 * flux 采用
 *  ErrorWebFluxAutoConfiguration
 *  DefaultErrorWebExceptionHandler
 */
@ControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ResponseBody
class ExceptionHandlerConfig {

  @ExceptionHandler(Array(classOf[MethodArgumentNotValidException]))
  def processValidationError(ex: MethodArgumentNotValidException): Result[String] = {
    val result = ex.getBindingResult()
    val fieldError = result.getFieldError()
    val errorMsg = fieldError.getDefaultMessage()
    Results.failure(errorMsg)
  }

  @ExceptionHandler(Array(classOf[BindException]))
  def processException(ex: BindException): Result[String] = {
    val fieldError = ex.getFieldError()
    val errorMsg = fieldError.getDefaultMessage()
    Results.failure(errorMsg)
  }

}