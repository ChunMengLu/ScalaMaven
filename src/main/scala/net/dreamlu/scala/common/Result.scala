package net.dreamlu.scala.common

import scala.beans.BeanProperty

class Result[T] extends Serializable {
  @BeanProperty
  var code: Int = _
  @BeanProperty
  var data: T = _
  @BeanProperty
  var msg: String = _
}