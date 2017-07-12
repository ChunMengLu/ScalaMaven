package net.dreamlu.scala.common

object Results {
  val SUCCESS: Int = 0
  val FAILURE: Int = 1
  
  def success[T](data: T) : Result[T] = {
    var result = new Result[T]()
    result.setCode(SUCCESS)
    result.setData(data)
    result.setMsg("成功")
    result
  }
  
  def success[T]() : Result[T] = {
    var result = new Result[T]()
    result.setCode(SUCCESS)
    result.setMsg("失败")
    return result
  }
  
  def failure[T](msg: String): Result[T] = {
    var result = new Result[T]()
    result.setCode(FAILURE)
    result.setMsg(msg)
    result
  }
}