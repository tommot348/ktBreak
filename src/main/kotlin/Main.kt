package main

import sdl.SDL_CreateWindow
import sdl.SDL_DestroyWindow
import sdl.SDL_Event
import sdl.SDL_GetWindowSurface
import sdl.SDL_Init
import sdl.SDL_INIT_VIDEO
import sdl.SDL_KEYUP
import sdl.SDL_PollEvent
import sdl.SDL_UpdateWindowSurface
import sdl.SDL_WINDOWPOS_CENTERED
import sdl.SDL_Quit

import kotlinx.cinterop.Arena
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

fun main(args: Array<String>) {
  val arena = Arena()
  SDL_Init(SDL_INIT_VIDEO)
  arena.defer { SDL_Quit() }
  val win = SDL_CreateWindow("test",
    SDL_WINDOWPOS_CENTERED.toInt(),
    SDL_WINDOWPOS_CENTERED.toInt(),
    800,
    600,
    0)
  val surface = SDL_GetWindowSurface(win)
  val game = Game(surface!!)
  arena.defer { SDL_DestroyWindow(win) }
  var run = true
  val event = arena.alloc<SDL_Event>()
  while (run) {
    SDL_PollEvent(event.ptr)
    when (event.type) {
      SDL_KEYUP -> run = false
    }
    game.draw()
    SDL_UpdateWindowSurface(win)
  }
}
