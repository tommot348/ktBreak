package main

import kotlin.math.sqrt

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed

import sdl.SDL_FillRect
import sdl.SDL_Surface

typealias Point = List<Int>
typealias Edge = List<Point>

fun Edge.containsPoint(p: Point): Boolean {
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
  val inters = listOf<Int>(xinters.toInt(), yinters.toInt())
  // ist punkt auf beiden strecken?
  if (this.containsPoint(inters) and other.containsPoint(inters)) {
    return inters
  } else {
    return null
  }
}

fun Point.distanceTo(other: Point): Int {
  val x = this[0] - other[0]
  val y = this[1] - other[1]
  return sqrt((x*x + y*y).toDouble()).toInt()
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
    when (mode) {
      Mode.Left -> paddle.moveX(-1, width)
      Mode.Right -> paddle.moveX(1, width)
      else -> paddle.moveX(0, width)
    }
    if (ball.moving) {
      ball.move(width, height)
    }
    val hit = bricks.mapIndexed { i, it ->
      val edges = it.getEdges()
      val ballvec = ball.getVector()
      val interstop = edges[0].intersect(ballvec)
      val intersright = edges[1].intersect(ballvec)
      val intersbottom = edges[2].intersect(ballvec)
      val intersleft = edges[3].intersect(ballvec)
      val dists = listOf<Point?>(interstop, intersright, intersbottom, intersleft).map {
        it?.distanceTo(ball.position)
      }
      if (dists.filterNotNull().size == 0) {
        null
      } else {
        val shortestIndex = dists.reduceIndexed { i, acc, x ->
          if (x != null) {
            if (acc == null) {
              i
            } else {
              if (x < dists[acc]!!) {
                i
              } else {
                acc
              }
            }
          } else {
            acc
          }
        }
        when (shortestIndex) {
          0 -> "top"
          1 -> "right"
          2 -> "bottom"
          3 -> "left"
          else -> ""
        }
      }
    }
    hit.forEachIndexed { i, it ->
      if (it != null) {
        bricks.removeAt(i)
        when (it) {
          "top" -> ball.changeDirY()
          "bottom" -> ball.changeDirY()
          "left" -> ball.changeDirY()
          "right" -> ball.changeDirY()
        }
      }
    }
  }
  fun startBall() {
    ball.start()
  }
}
