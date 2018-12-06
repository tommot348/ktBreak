package main

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed

import sdl.SDL_HasIntersection
import sdl.SDL_FillRect
import sdl.SDL_Surface

typealias Point = List<Int>
typealias Edge = List<Point>

fun Edge.getIntersection(other: Edge): Point {
}

class Game(val surface: CPointer<SDL_Surface>, val width: Int, val height: Int) {
  val bricks = mutableListOf<Brick>()
  val paddle = Paddle(surface, 350, 500)
  val ball = Ball(surface, 355, 495)
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
    val ballPtr = ball.getRectPtr()
    when (mode) {
      Mode.Left -> paddle.moveX(-1, width)
      Mode.Right -> paddle.moveX(1, width)
      else -> null
    }
    if (ball.moving) {
      ball.move(width, height)
    }
    val hit = bricks.filter {
      val edges = it.getEdges()
      
    }
    if (hit.size > 0) {

    }
  }
  fun startBall() {
    ball.start()
  }
}
