package monocle.internal

import monocle._

import scalaz.Monoid

trait FoldMagnet[S, A] {
  type ReturnType
  def andThen(source: Fold[S, A]): ReturnType
}

object FoldMagnet {

  implicit def fold[S, A, B](other: Fold[A, B]) = new FoldMagnet[S, A] {
    type ReturnType = Fold[S, B]
    def andThen(source: Fold[S, A]): ReturnType =
      new Fold[S, B] {
        def foldMap[M: Monoid](f: B => M)(s: S): M =
          source.foldMap(other.foldMap(f)(_))(s)
      }
  }
  implicit def getter[S, A, C](other: Getter[A, C]) = new FoldMagnet[S, A] {
    type ReturnType = Fold[S, C]
    def andThen(source: Fold[S, A]): ReturnType =
      source compose other.asFold
  }
  implicit def traversal[S, A, B, C, D](other: PTraversal[A, B, C, D]) = new FoldMagnet[S, A] {
    type ReturnType = Fold[S, C]
    def andThen(source: Fold[S, A]): ReturnType =
      source compose other.asFold
  }
  implicit def optional[S, A, B, C, D](other: POptional[A, B, C, D]) = new FoldMagnet[S, A] {
    type ReturnType = Fold[S, C]
    def andThen(source: Fold[S, A]): ReturnType =
      source compose other.asFold
  }
  implicit def prism[S, A, B, C, D](other: PPrism[A, B, C, D]) = new FoldMagnet[S, A] {
    type ReturnType = Fold[S, C]
    def andThen(source: Fold[S, A]): ReturnType =
      source compose other.asFold
  }
  implicit def lens[S, A, B, C, D](other: PLens[A, B, C, D]) = new FoldMagnet[S, A] {
    type ReturnType = Fold[S, C]
    def andThen(source: Fold[S, A]): ReturnType =
      source compose other.asFold
  }
  implicit def iso[S, A, B, C, D](other: PIso[A, B, C, D]) = new FoldMagnet[S, A] {
    type ReturnType = Fold[S, C]
    def andThen(source: Fold[S, A]): ReturnType =
      source compose other.asFold
  }
}
