package main

import kotlinx.cinterop.alloc
import kotlinx.cinterop.ArenaBase

import sdl.SDL_Rect

public abstract class IDrawable(w: Int, h: Int, x: Int, y: Int) : ArenaBase() {
  val rect = alloc<SDL_Rect>()
  init {
    rect.w = w
    rect.h = h
    rect.x = x
    rect.y = y
  }
  public abstract fun draw()
}
