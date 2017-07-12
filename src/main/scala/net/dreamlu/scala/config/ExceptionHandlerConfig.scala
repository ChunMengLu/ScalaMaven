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
 * 此处有问题
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

  @Bean
  @Order
  def responseStatusExceptionHandler(): WebExceptionHandler = {
    new WebFluxExceptionHandler()
  }
}

class WebFluxExceptionHandler extends WebExceptionHandler {
  private val logger = LoggerFactory.getLogger(classOf[WebFluxExceptionHandler])

  override def handle(exchange: ServerWebExchange, ex: Throwable): Mono[Void] = {
    if (ex.getMessage() != null) {
      logger.error(ex.getMessage());
    }
    if (ex.isInstanceOf[ResponseStatusException]) {
      val statusCode = ex.asInstanceOf[ResponseStatusException].getStatus
      exchange.getResponse().setStatusCode(statusCode);
    }
    if (ex.isInstanceOf[BindException]) {
      val fieldError = ex.asInstanceOf[BindException].getFieldError()
      val errorMsg = fieldError.getDefaultMessage()
      var result = Results.failure(errorMsg)
      val body = Mono.just(result)
      //      exchange.getResponse.writeWith(body)
      //      exchange.getResponse.writeAndFlushWith(body)
      //      Mono.empty()
      Mono.error(ex)
    } else {
      Mono.error(ex)
    }
  }
}