package main

import sdl.SDL_FillRect
import sdl.SDL_Surface

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public class Paddle(val parent: CPointer<SDL_Surface>, x: Int, y: Int) : IDrawable(100, 10, x, y) {
  val color = Color(parent.pointed.format!!).BLUE
  override fun draw() {
    SDL_FillRect(parent, rect.ptr, color)
  }
  fun moveX(delta: Int, width: Int) {
    rect.x += delta
    if (rect.x + 100 >= width) {
      rect.x = width - 100
    }
    if (rect.x <= 0) {
      rect.x = 0
    }
  }
}
