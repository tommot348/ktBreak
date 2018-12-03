package main

import kotlinx.cinterop.CPointer

import sdl.SDL_Surface

class Game(surface: CPointer<SDL_Surface>) {
  val bricks = mutableListOf<Brick>()
  val paddle = Paddle(surface, 350, 500)
  val ball = Ball(surface, 355, 495)
  init {
    (0..5).forEach { y ->
      (0..15).forEach { x ->
        bricks.add(Brick(surface, (x * 45) + 15, (y * 15) + 15))
      }
    }
  }
  fun draw() {
    bricks.forEach { it.draw() }
    paddle.draw()
    ball.draw()
  }
}
