package main

import sdl.SDL_FillRect
import sdl.SDL_Surface

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public class Brick(parent: CPointer<SDL_Surface>, x: Int, y: Int) : IDrawable(40, 10, x, y) {
  val color = Color(parent.pointed.format!!).RED
  val parent = parent
  override fun draw() {
    SDL_FillRect(parent, rect.ptr, color)
  }
}
