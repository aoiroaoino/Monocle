package monocle.internal

import monocle._

import scalaz.Functor

trait LensMagnet[S, T, A, B] {
  type ReturnType
  def andThen(source: PLens[S, T, A, B]): ReturnType
}

object LensMagnet {

  implicit def fold[S, T, A, B, C](other: Fold[A, C]) = new LensMagnet[S, T, A, B] {
    type ReturnType = Fold[S, C]
    def andThen(source: PLens[S, T, A, B]): ReturnType =
      source.asFold compose other
  }
  implicit def getter[S, T, A, B, C](other: Getter[A, C]) = new LensMagnet[S, T, A, B] {
    type ReturnType = Getter[S, C]
    def andThen(source: PLens[S, T, A, B]): ReturnType =
      source.asGetter compose other
  }
  implicit def setter[S, T, A, B, C, D](other: PSetter[A, B, C, D]) = new LensMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: PLens[S, T, A, B]): ReturnType =
      source.asSetter compose other
  }
  implicit def traversal[S, T, A, B, C, D](other: PTraversal[A, B, C, D]) = new LensMagnet[S, T, A, B] {
    type ReturnType = PTraversal[S, T, C, D]
    def andThen(source: PLens[S, T, A, B]): ReturnType =
      source.asTraversal compose other
  }
  implicit def optional[S, T, A, B, C, D](other: POptional[A, B, C, D]) = new LensMagnet[S, T, A, B] {
    type ReturnType = POptional[S, T, C, D]
    def andThen(source: PLens[S, T, A, B]): ReturnType =
      source.asOptional compose other
  }
  implicit def prism[S, T, A, B, C, D](other: PPrism[A, B, C, D]) = new LensMagnet[S, T, A, B] {
    type ReturnType = POptional[S, T, C, D]
    def andThen(source: PLens[S, T, A, B]): ReturnType =
      source.asOptional compose other.asOptional
  }
  implicit def lens[S, T, A, B, C, D](other: PLens[A, B, C, D]) = new LensMagnet[S, T, A, B] {
    type ReturnType = PLens[S, T, C, D]
    def andThen(source: PLens[S, T, A, B]): ReturnType =
      new PLens[S, T, C, D]{
        def get(s: S): C =
          other.get(source.get(s))

        def set(d: D): S => T =
          source.modify(other.set(d))

        def modifyF[F[_]: Functor](f: C => F[D])(s: S): F[T] =
          source.modifyF(other.modifyF(f))(s)

        def modify(f: C => D): S => T =
          source.modify(other.modify(f))
      }
  }
  implicit def iso[S, T, A, B, C, D](other: PIso[A, B, C, D]) = new LensMagnet[S, T, A, B] {
    type ReturnType = PLens[S, T, C, D]
    def andThen(source: PLens[S, T, A, B]): ReturnType = {
      source compose other.asLens
    }
  }
}
