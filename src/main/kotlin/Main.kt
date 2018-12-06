package main

import sdl.SDL_AddTimer
import sdl.SDL_CreateWindow
import sdl.SDL_DestroyWindow
import sdl.SDL_Event
import sdl.SDL_GetWindowSurface
import sdl.SDL_Init
import sdl.SDL_INIT_VIDEO
import sdl.SDL_KEYDOWN
import sdl.SDL_KEYUP
import sdl.SDL_PollEvent
// import sdl.SDL_PushEvent
import sdl.SDL_RemoveTimer
import sdl.SDL_UpdateWindowSurface
import sdl.SDL_WINDOWPOS_CENTERED
import sdl.SDL_Quit

import sdl.SDLK_ESCAPE
import sdl.SDLK_LEFT
import sdl.SDLK_RIGHT
import sdl.SDLK_UP

import kotlinx.cinterop.Arena
import kotlinx.cinterop.alloc
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ptr
import kotlinx.cinterop.staticCFunction

var update = true
fun cb(interval: UInt, param: COpaquePointer?): UInt {
  update = true
  return interval
}
fun main(args: Array<String>) {
  val width = 800
  val height = 600
  val arena = Arena()
  SDL_Init(SDL_INIT_VIDEO)
  arena.defer { SDL_Quit() }
  val win = SDL_CreateWindow("test",
    SDL_WINDOWPOS_CENTERED.toInt(),
    SDL_WINDOWPOS_CENTERED.toInt(),
    width,
    height,
    0)
  arena.defer { SDL_DestroyWindow(win) }
  val surface = SDL_GetWindowSurface(win)
  val game = Game(surface!!, width, height)
  var run = true
  val event = arena.alloc<SDL_Event>()
  val timer = SDL_AddTimer(100 / 6, staticCFunction(::cb), null)
  arena.defer { SDL_RemoveTimer(timer) }
  var mode = Mode.Stop
  while (run) {
    SDL_PollEvent(event.ptr)
    when (event.type) {
      SDL_KEYDOWN -> when (event.key.keysym.sym) {
        SDLK_ESCAPE.toInt() -> run = false
        SDLK_LEFT.toInt() -> if (mode == Mode.Stop) {
          mode = Mode.Left
        }
        SDLK_RIGHT.toInt() -> if (mode == Mode.Stop) {
          mode = Mode.Right
        }
        SDLK_UP.toInt() -> game.startBall()
      }
      SDL_KEYUP -> when (event.key.keysym.sym) {
        SDLK_LEFT.toInt() -> if (mode == Mode.Left) {
          mode = Mode.Stop
        }
        SDLK_RIGHT.toInt() -> if (mode == Mode.Right) {
          mode = Mode.Stop
        }
      }
    }
    game.draw()
    if (update) {
      update = false
      game.update(mode)
    }
    SDL_UpdateWindowSurface(win)
  }
}
