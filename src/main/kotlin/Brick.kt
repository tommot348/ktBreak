package main

import sdl.SDL_FillRect
import sdl.SDL_Surface

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public class Brick(val parent: CPointer<SDL_Surface>, x: Int, y: Int) : IDrawable(40, 10, x, y) {
  val color = Color(parent.pointed.format!!).RED
  fun getRectPtr() = rect.ptr
  override fun draw() {
    SDL_FillRect(parent, rect.ptr, color)
  }
  fun getEdges(): List<Edge> {
    val a = listOf<Int>(rect.x, rect.y)
    val b = listOf<Int>(rect.x + 40, rect.y)
    val c = listOf<Int>(rect.x + 40, rect.y + 10)
    val d = listOf<Int>(rect.x, rect.y + 10)
    val top = listOf<Point>(a, b)
    val right = listOf<Point>(b, c)
    val bottom = listOf<Point>(c, d)
    val left = listOf<Point>(d, a)
    return listOf<Edge>(top, right, bottom, lefleft)
  }
}
