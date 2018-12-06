package main

import sdl.SDL_FillRect
import sdl.SDL_Surface

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public class Ball(parent: CPointer<SDL_Surface>, x: Int, y: Int) : IDrawable(5, 5, x, y) {
  val color = Color(parent.pointed.format!!).GREEN
  val parent = parent
  var deltaX = 1
  var deltaY = 1
  var _moving = false
  public val moving: Boolean
    get() = _moving
  fun changeDirX() {
    deltaX *= -1
  }
  fun changeDirY() {
    deltaY *= -1
  }
  override fun draw() {
    SDL_FillRect(parent, rect.ptr, color)
  }
  fun getRectPtr() = rect.ptr
  fun getVector() = listOf<Int>(deltaX, deltaY)
  fun move(width: Int, height: Int) {
    rect.x += deltaX
    rect.y += deltaY
    if (rect.x >= width - 5) {
      rect.x = width - 5
      deltaX *= -1
    }
    if (rect.x <= 0) {
      rect.x = 0
      deltaX *= -1
    }
    if (rect.y >= height - 5) {
      rect.y = height - 5
      deltaY *= -1
    }
    if (rect.y <= 0) {
      rect.y = 0
      deltaY *= -1
    }
  }
  fun start() {
    _moving = true
  }
}
