package monocle.internal

import monocle._

import scalaz.{Applicative, \/}

trait OptionalMagnet[S, T, A, B] {
  type ReturnType
  def andThen(source: POptional[S, T, A, B]): ReturnType
}

object OptionalMagnet {

  implicit def fold[S, T, A, B, C](other: Fold[A, C]) = new OptionalMagnet[S, T, A, B] {
    type ReturnType = Fold[S, C]
    def andThen(source: POptional[S, T, A, B]): ReturnType =
      source.asFold compose other
  }
  implicit def getter[S, T, A, B, C](other: Getter[A, C]) = new OptionalMagnet[S, T, A, B] {
    type ReturnType = Fold[S, C]
    def andThen(source: POptional[S, T, A, B]): ReturnType =
      source.asFold compose other
  }
  implicit def setter[S, T, A, B, C, D](other: PSetter[A, B, C, D]) = new OptionalMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: POptional[S, T, A, B]): ReturnType =
      source.asSetter compose other
  }
  implicit def traversal[S, T, A, B, C, D](other: PTraversal[A, B, C, D]) = new OptionalMagnet[S, T, A, B] {
    type ReturnType = PTraversal[S, T, C, D]
    def andThen(source: POptional[S, T, A, B]): ReturnType =
      source.asTraversal compose other
  }
  implicit def optional[S, T, A, B, C, D](other: POptional[A, B, C, D]) = new OptionalMagnet[S, T, A, B] {
    type ReturnType = POptional[S, T, C, D]
    def andThen(source: POptional[S, T, A, B]): ReturnType =
      new POptional[S, T, C, D]{
        def getOrModify(s: S): T \/ C =
          source.getOrModify(s).flatMap(a => other.getOrModify(a).bimap(source.set(_)(s), identity))

        def set(d: D): S => T =
          source.modify(other.set(d))

        def getOption(s: S): Option[C] =
          source.getOption(s) flatMap other.getOption

        def modifyF[F[_]: Applicative](f: C => F[D])(s: S): F[T] =
          source.modifyF(other.modifyF(f))(s)

        def modify(f: C => D): S => T =
          source.modify(other.modify(f))
      }
  }
  implicit def prism[S, T, A, B, C, D](other: PPrism[A, B, C, D]) = new OptionalMagnet[S, T, A, B] {
    type ReturnType = POptional[S, T, C, D]
    def andThen(source: POptional[S, T, A, B]): ReturnType =
      source compose other.asOptional
  }
  implicit def lens[S, T, A, B, C, D](other: PLens[A, B, C, D]) = new OptionalMagnet[S, T, A, B] {
    type ReturnType = POptional[S, T, C, D]
    def andThen(source: POptional[S, T, A, B]): ReturnType =
      source compose other.asOptional
  }
  implicit def iso[S, T, A, B, C, D](other: PIso[A, B, C, D]) = new OptionalMagnet[S, T, A, B] {
    type ReturnType = POptional[S, T, C, D]
    def andThen(source: POptional[S, T, A, B]): ReturnType =
      source compose other.asOptional
  }
}
