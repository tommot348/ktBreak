package main

import sdl.SDL_FillRect
import sdl.SDL_Surface

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public class Paddle(parent: CPointer<SDL_Surface>, x: Int, y: Int) : IDrawable(100, 10, x, y) {
  val color = Color(parent.pointed.format!!).BLUE
  val parent = parent
  override fun draw() {
    SDL_FillRect(parent, rect.ptr, color)
  }
  fun moveX(delta: Int) {
    rect.x += delta
  }
}
