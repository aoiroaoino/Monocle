package monocle.internal

import monocle._

import scalaz.Applicative

trait TraversalMagnet[S, T, A, B] {
  type ReturnType
  def andThen(source: PTraversal[S, T, A, B]): ReturnType
}

object TraversalMagnet {

  implicit def fold[S, T, A, B, C](other: Fold[A, C]) = new TraversalMagnet[S, T, A, B] {
    type ReturnType = Fold[S, C]
    def andThen(source: PTraversal[S, T, A, B]): ReturnType =
      source.asFold compose other
  }
  implicit def getter[S, T, A, B, C](other: Getter[A, C]) = new TraversalMagnet[S, T, A, B] {
    type ReturnType = Fold[S, C]
    def andThen(source: PTraversal[S, T, A, B]): ReturnType =
      source.asFold compose other
  }
  implicit def setter[S, T, A, B, C, D](other: PSetter[A, B, C, D]) = new TraversalMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: PTraversal[S, T, A, B]): ReturnType =
      source.asSetter compose other
  }
  implicit def traversal[S, T, A, B, C, D](other: PTraversal[A, B, C, D]) = new TraversalMagnet[S, T, A, B] {
    type ReturnType = PTraversal[S, T, C, D]
    def andThen(source: PTraversal[S, T, A, B]): ReturnType =
      new PTraversal[S, T, C, D] {
        def modifyF[F[_]: Applicative](f: C => F[D])(s: S): F[T] =
          source.modifyF(other.modifyF(f)(_))(s)
      }
  }
  implicit def optional[S, T, A, B, C, D](other: POptional[A, B, C, D]) = new TraversalMagnet[S, T, A, B] {
    type ReturnType = PTraversal[S, T, C, D]
    def andThen(source: PTraversal[S, T, A, B]): ReturnType =
      source compose other.asTraversal
  }
  implicit def prism[S, T, A, B, C, D](other: PPrism[A, B, C, D]) = new TraversalMagnet[S, T, A, B] {
    type ReturnType = PTraversal[S, T, C, D]
    def andThen(source: PTraversal[S, T, A, B]): ReturnType =
      source compose other.asTraversal
  }
  implicit def lens[S, T, A, B, C, D](other: PLens[A, B, C, D]) = new TraversalMagnet[S, T, A, B] {
    type ReturnType = PTraversal[S, T, C, D]
    def andThen(source: PTraversal[S, T, A, B]): ReturnType =
      source compose other.asTraversal
  }
  implicit def iso[S, T, A, B, C, D](other: PIso[A, B, C, D]) = new TraversalMagnet[S, T, A, B] {
    type ReturnType = PTraversal[S, T, C, D]
    def andThen(source: PTraversal[S, T, A, B]): ReturnType =
      source compose other.asTraversal
  }
}
