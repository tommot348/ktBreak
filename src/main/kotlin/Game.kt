package main

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed

import sdl.SDL_FillRect
import sdl.SDL_Surface

class Game(surface: CPointer<SDL_Surface>) {
  val bricks = mutableListOf<Brick>()
  val paddle = Paddle(surface, 350, 500)
  val ball = Ball(surface, 355, 495)
  val surface = surface
  val backgroundColor = Color(surface.pointed.format!!).WHITE
  init {
    (0..5).forEach { y ->
      (0..15).forEach { x ->
        bricks.add(Brick(surface, (x * 45) + 15, (y * 15) + 15))
      }
    }
  }
  fun draw() {
    SDL_FillRect(surface, null, backgroundColor)
    bricks.forEach { it.draw() }
    paddle.draw()
    ball.draw()
  }
  fun update(mode: Mode) {
    when (mode) {
      Mode.Left -> paddle.moveX(-1)
      Mode.Right -> paddle.moveX(1)
      else -> null
    }
  }
}
