package main

import sdl.SDL_CreateWindow
import sdl.SDL_DestroyWindow
import sdl.SDL_Event
import sdl.SDL_GetWindowSurface
import sdl.SDL_Init
import sdl.SDL_INIT_VIDEO
import sdl.SDL_KEYDOWN
import sdl.SDL_KEYUP
import sdl.SDL_PollEvent
import sdl.SDL_UpdateWindowSurface
import sdl.SDL_WINDOWPOS_CENTERED
import sdl.SDL_Quit

import sdl.SDLK_ESCAPE
import sdl.SDLK_LEFT
import sdl.SDLK_RIGHT

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
  arena.defer { SDL_DestroyWindow(win) }
  val surface = SDL_GetWindowSurface(win)
  val game = Game(surface!!)
  var run = true
  val event = arena.alloc<SDL_Event>()
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
    game.update(mode)
    SDL_UpdateWindowSurface(win)
  }
}
