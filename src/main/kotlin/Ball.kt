package main

import sdl.SDL_FillRect
import sdl.SDL_Surface

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public class Ball(parent: CPointer<SDL_Surface>, x: Int, y: Int) : IDrawable(5, 5, x, y) {
  val color = Color(parent.pointed.format!!).GREEN
  val parent = parent
  override fun draw() {
    SDL_FillRect(parent, rect.ptr, color)
  }
  fun move(deltaX: Int, deltaY: Int) {
    rect.x += deltaX
    rect.y += deltaY
  }
}
