package monocle.internal

import monocle._

trait IsoMagnet[S, T, A, B] {
  type ReturnType
  def andThen(source: PIso[S, T, A, B]): ReturnType
}

object IsoMagnet {

  implicit def fold[S, T, A, B, C](other: Fold[A, C]) = new IsoMagnet[S, T, A, B] {
    type ReturnType = Fold[S, C]
    def andThen(source: PIso[S, T, A, B]): ReturnType =
      source.asFold compose other
  }
  implicit def getter[S, T, A, B, C](other: Getter[A, C]) = new IsoMagnet[S, T, A, B] {
    type ReturnType = Getter[S, C]
    def andThen(source: PIso[S, T, A, B]): ReturnType =
      source.asGetter compose other
  }
  implicit def setter[S, T, A, B, C, D](other: PSetter[A, B, C, D]) = new IsoMagnet[S, T, A, B] {
    type ReturnType = PSetter[S, T, C, D]
    def andThen(source: PIso[S, T, A, B]): ReturnType =
      source.asSetter compose other
  }
  implicit def traversal[S, T, A, B, C, D](other: PTraversal[A, B, C, D]) = new IsoMagnet[S, T, A, B] {
    type ReturnType = PTraversal[S, T, C, D]
    def andThen(source: PIso[S, T, A, B]): ReturnType =
      source.asTraversal compose other
  }
  implicit def optional[S, T, A, B, C, D](other: POptional[A, B, C, D]) = new IsoMagnet[S, T, A, B] {
    type ReturnType = POptional[S, T, C, D]
    def andThen(source: PIso[S, T, A, B]): ReturnType =
      source.asOptional compose other
  }
  implicit def prism[S, T, A, B, C, D](other: PPrism[A, B, C, D]) = new IsoMagnet[S, T, A, B] {
    type ReturnType = PPrism[S, T, C, D]
    def andThen(source: PIso[S, T, A, B]): ReturnType =
      source.asPrism compose other
  }
  implicit def lens[S, T, A, B, C, D](other: PLens[A, B, C, D]) = new IsoMagnet[S, T, A, B] {
    type ReturnType = PLens[S, T, C, D]
    def andThen(source: PIso[S, T, A, B]): ReturnType =
      source.asLens compose other
  }
  implicit def iso[S, T, A, B, C, D](other: PIso[A, B, C, D]) = new IsoMagnet[S, T, A, B] {
    type ReturnType = PIso[S, T, C, D]
    def andThen(source: PIso[S, T, A, B]): ReturnType =
      new PIso[S, T, C, D]{ composeSelf =>
        def get(s: S): C =
          other.get(source.get(s))

        def reverseGet(d: D): T =
          source.reverseGet(other.reverseGet(d))

        def reverse: PIso[D, C, T, S] =
          new PIso[D, C, T, S] {
            def get(d: D): T =
              source.reverseGet(other.reverseGet(d))

            def reverseGet(s: S): C =
              other.get(source.get(s))

            def reverse: PIso[S, T, C, D] =
              composeSelf
          }
      }
  }
}
