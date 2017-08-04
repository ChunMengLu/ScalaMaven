package net.dreamlu.scala.model

import java.time.LocalDateTime

import scala.beans.BeanProperty

import org.hibernate.validator.constraints.NotBlank
import org.springframework.format.annotation.DateTimeFormat

import com.fasterxml.jackson.annotation.JsonFormat

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Table(name = "users")
@Entity
class User extends Serializable {
  @Id
  @BeanProperty
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = _
  
  @NotBlank
  @BeanProperty
  var name: String = _
  
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @BeanProperty
  var birthday: LocalDateTime = _
  
  @NotNull
  @BeanProperty
  var telephone: String = _
  
  override def toString = s"[User]{id:$id, name:$name, birthday:$birthday, telephone:$telephone}"
}