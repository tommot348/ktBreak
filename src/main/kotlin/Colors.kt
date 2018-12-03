package main
import sdl.SDL_MapRGB
import sdl.SDL_PixelFormat

import kotlinx.cinterop.CPointer

class Color(val fmt: CPointer<SDL_PixelFormat>) {
  val BLUE = SDL_MapRGB(fmt, 0, 0, 255)
  val GREEN = SDL_MapRGB(fmt, 0, 255, 0)
  val RED = SDL_MapRGB(fmt, 255, 0, 0)
  val WHITE = SDL_MapRGB(fmt, 255, 255, 255)
}
