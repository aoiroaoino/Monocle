package monocle.internal

import monocle._

trait GetterMagnet[S, A] {
  type ReturnType
  def andThen(source: Getter[S, A]): ReturnType
}

object GetterMagnet {

  implicit def fold[S, A, B](other: Fold[A, B]) = new GetterMagnet[S, A] {
    type ReturnType = Fold[S, B]
    def andThen(source: Getter[S, A]): ReturnType =
      source.asFold compose other
  }
  implicit def getter[S, A, B](other: Getter[A, B]) = new GetterMagnet[S, A] {
    type ReturnType = Getter[S, B]
    def andThen(source: Getter[S, A]): ReturnType =
      new Getter[S, B] {
        def get(s: S): B =
          other.get(source.get(s))
      }
  }
  implicit def traversal[S, A, B, C, D](other: PTraversal[A, B, C, D]) = new GetterMagnet[S, A] {
    type ReturnType = Fold[S, C]
    def andThen(source: Getter[S, A]): ReturnType =
      source.asFold compose other
  }
  implicit def optional[S, A, B, C, D](other: POptional[A, B, C, D]) = new GetterMagnet[S, A] {
    type ReturnType = Fold[S, C]
    def andThen(source: Getter[S, A]): ReturnType =
      source.asFold compose other
  }
  implicit def prism[S, A, B, C, D](other: PPrism[A, B, C, D]) = new GetterMagnet[S, A] {
    type ReturnType = Fold[S, C]
    def andThen(source: Getter[S, A]): ReturnType =
      source.asFold compose other
  }
  implicit def lens[S, A, B, C, D](other: PLens[A, B, C, D]) = new GetterMagnet[S, A] {
    type ReturnType = Getter[S, C]
    def andThen(source: Getter[S, A]): ReturnType =
      source compose other.asGetter
  }
  implicit def iso[S, A, B, C, D](other: PIso[A, B, C, D]) = new GetterMagnet[S, A] {
    type ReturnType = Getter[S, C]
    def andThen(source: Getter[S, A]): ReturnType =
      source compose other.asGetter
  }
}
