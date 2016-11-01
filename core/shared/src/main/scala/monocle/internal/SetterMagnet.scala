package monocle.internal

import monocle._

trait SetterMagnet[S, T, A, B] {
  type ReturnType
  def andThen(source: PSetter[S, T, A, B]): ReturnType
}

object SetterMagnet {

  implicit def setter[S, T, A, B, C, D](other: PSetter[A, B, C, D]) = new SetterMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: PSetter[S, T, A, B]): ReturnType =
      new PSetter[S, T, C, D]{
        def modify(f: C => D): S => T =
          source.modify(other.modify(f))

        def set(d: D): S => T =
          source.modify(other.set(d))
      }
  }
  implicit def traversal[S, T, A, B, C, D](other: PTraversal[A, B, C, D]) = new SetterMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: PSetter[S, T, A, B]): ReturnType =
      source compose other.asSetter
  }
  implicit def optional[S, T, A, B, C, D](other: POptional[A, B, C, D]) = new SetterMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: PSetter[S, T, A, B]): ReturnType =
      source compose other.asSetter
  }
  implicit def prism[S, T, A, B, C, D](other: PPrism[A, B, C, D]) = new SetterMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: PSetter[S, T, A, B]): ReturnType =
      source compose other.asSetter
  }
  implicit def lens[S, T, A, B, C, D](other: PLens[A, B, C, D]) = new SetterMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: PSetter[S, T, A, B]): ReturnType =
      source compose other.asSetter
  }
  implicit def iso[S, T, A, B, C, D](other: PIso[A, B, C, D]) = new SetterMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: PSetter[S, T, A, B]): ReturnType =
      source compose other.asSetter
  }
}
