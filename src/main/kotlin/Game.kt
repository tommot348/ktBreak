package main

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed

import sdl.SDL_HasIntersection
import sdl.SDL_FillRect
import sdl.SDL_Surface

typealias Point = List<Int>
typealias Edge = List<Point>

fun Edge.contains(p: Point) {
  return false
}

fun Edge.intersect(other: Edge): Point? {
  val e1p1 = this[0]
  val e1p2 = this[1]
  val e2p1 = other[0]
  val e2p2 = other[1]
  val x1 = e1p1[0]
  val y1 = e1p1[1]
  val x2 = e1p2[0]
  val y2 = e1p2[1]
  val x3 = e2p1[0]
  val y3 = e2p1[1]
  val x4 = e2p2[0]
  val y4 = e2p2[1]
  val divisor = ((y4 - y3) * (x2 - x1) - (y2 - y1) * (x4 - x3))
  if (divisor == 0) {
    return null
  }
  val xDivident = ((x4 - x3) * ((x2 * y1) - (x1 * y2)) - ((x2 - x1) * ((x4 * y3) - (x3 * y4))))
  val xinters: Float = (xDivident.toFloat() / divisor)
  val yDivident = ((y1 - y2) * ((x4 * y3) - (x3 * y4)) - (y3 - y4) * ((x2 * y1) - (x1 * y2)))
  val yinters: Float = (yDivident.toFloat() / divisor)
  val inters = listOf<Int>(xinters.toInt(), yinters.toInt()) as Point
  // ist punkt auf beiden strecken?
  if (this.contains(inters) and other.contains(inters)) {
    return inters
  } else {
    return null
  }
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
    val hit = bricks.map {
      val edges = it.getEdges()
      val ballvec = ball.getVector()
      val interstop = edges[0].intersect(ballvec)
      val intersright = edges[1].intersect(ballvec)
      val intersbottom = edges[2].intersect(ballvec)
      val intersleft = edges[3].intersect(ballvec)
      when (listOf<Point>(interstop, intersright, intersbottom, intersleft)) {

      }
    }
    if (hit.size > 0) {

    }
  }
  fun startBall() {
    ball.start()
  }
}
