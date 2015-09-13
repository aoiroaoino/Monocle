package monocle.syntax

import monocle._

object compose extends ComposeSyntax

trait ComposeSyntax {
  implicit def toTraversalOps[S, T, A, B](traversal: PTraversal[S, T, A, B]): TraversalComposeOps[S, T, A, B] =
    new TraversalComposeOps(traversal)

  implicit def toOptionalOps[S, T, A, B](optional: POptional[S, T, A, B]): OptionalComposeOps[S, T, A, B] =
    new OptionalComposeOps(optional)

  implicit def toPrismCompose[S, T, A, B](prism: PPrism[S, T, A, B]): PrismComposeOps[S, T, A, B] =
    new PrismComposeOps(prism)

  implicit def toLensCompose[S, T, A, B](lens: PLens[S, T, A, B]): LensComposeOps[S, T, A, B] =
    new LensComposeOps(lens)

  implicit def toIsoCompose[S, T, A, B](iso: PIso[S, T, A, B]): IsoComposeOps[S, T, A, B] =
    new IsoComposeOps(iso)
}

final case class TraversalComposeOps[S, T, A, B](traversal: PTraversal[S, T, A, B]) {

  def compose[C](other: Fold[A, C]): Fold[S, C] =
    traversal composeFold other

  def compose[C](other: Getter[A, C]): Fold[S, C] =
    traversal composeGetter other

  def compose[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] =
    traversal composeSetter other

  def compose[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] =
    traversal composeTraversal other

  def compose[C, D](other: POptional[A, B, C, D]): PTraversal[S, T, C, D] =
    traversal composeOptional other

  def compose[C, D](other: PPrism[A, B, C, D]): PTraversal[S, T, C, D] =
    traversal composePrism other

  def compose[C, D](other: PLens[A, B, C, D]): PTraversal[S, T, C, D] =
    traversal composeLens other

  def compose[C, D](other: PIso[A, B, C, D]): PTraversal[S, T, C, D] =
    traversal composeIso other

  // aliases
  def ^[C](other: Fold[A, C]): Fold[S, C] = compose(other)
  def ^[C](other: Getter[A, C]): Fold[S, C] = compose(other)
  def ^[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] = compose(other)
  def ^[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] = compose(other)
  def ^[C, D](other: POptional[A, B, C, D]): PTraversal[S, T, C, D] = compose(other)
  def ^[C, D](other: PPrism[A, B, C, D]): PTraversal[S, T, C, D] = compose(other)
  def ^[C, D](other: PLens[A, B, C, D]): PTraversal[S, T, C, D] = compose(other)
  def ^[C, D](other: PIso[A, B, C, D]): PTraversal[S, T, C, D] = compose(other)
}

final case class OptionalComposeOps[S, T, A, B](optional: POptional[S, T, A, B]) {

  def compose[C](other: Fold[A, C]): Fold[S, C] =
    optional composeFold other

  def compose[C](other: Getter[A, C]): Fold[S, C] =
    optional composeGetter other

  def compose[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] =
    optional composeSetter other

  def compose[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] =
    optional composeTraversal other

  def compose[C, D](other: POptional[A, B, C, D]): POptional[S, T, C, D] =
    optional composeOptional other

  def compose[C, D](other: PPrism[A, B, C, D]): POptional[S, T, C, D] =
    optional composePrism other

  def compose[C, D](other: PLens[A, B, C, D]): POptional[S, T, C, D] =
    optional composeLens other

  def compose[C, D](other: PIso[A, B, C, D]): POptional[S, T, C, D] =
    optional composeIso other

  // aliases
  def ^[C](other: Fold[A, C]): Fold[S, C] = compose(other)
  def ^[C](other: Getter[A, C]): Fold[S, C] = compose(other)
  def ^[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] = compose(other)
  def ^[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] = compose(other)
  def ^[C, D](other: POptional[A, B, C, D]): POptional[S, T, C, D] = compose(other)
  def ^[C, D](other: PPrism[A, B, C, D]): POptional[S, T, C, D] = compose(other)
  def ^[C, D](other: PLens[A, B, C, D]): POptional[S, T, C, D] = compose(other)
  def ^[C, D](other: PIso[A, B, C, D]): POptional[S, T, C, D] = compose(other)
}


final case class PrismComposeOps[S, T, A, B](prism: PPrism[S, T, A, B]) {

  def compose[C](other: Fold[A, C]): Fold[S, C] =
    prism composeFold other

  def compose[C](other: Getter[A, C]): Fold[S, C] =
    prism composeGetter other

  def compose[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] =
    prism composeSetter other

  def compose[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] =
    prism composeTraversal other

  def compose[C, D](other: POptional[A, B, C, D]): POptional[S, T, C, D] =
    prism composeOptional other

  def compose[C, D](other: PPrism[A, B, C, D]): PPrism[S, T, C, D] =
    prism composePrism other

  def compose[C, D](other: PLens[A, B, C, D]): POptional[S, T, C, D] =
    prism composeLens other

  def compose[C, D](other: PIso[A, B, C, D]): PPrism[S, T, C, D] =
    prism composeIso other

  // aliases
  def ^[C](other: Fold[A, C]): Fold[S, C] = compose(other)
  def ^[C](other: Getter[A, C]): Fold[S, C] = compose(other)
  def ^[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] = compose(other)
  def ^[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] = compose(other)
  def ^[C, D](other: POptional[A, B, C, D]): POptional[S, T, C, D] = compose(other)
  def ^[C, D](other: PPrism[A, B, C, D]): PPrism[S, T, C, D] = compose(other)
  def ^[C, D](other: PLens[A, B, C, D]): POptional[S, T, C, D] = compose(other)
  def ^[C, D](other: PIso[A, B, C, D]): PPrism[S, T, C, D] = compose(other)
}

final case class LensComposeOps[S, T, A, B](lens: PLens[S, T, A, B]) {

  def compose[C](other: Fold[A, C]): Fold[S, C] =
    lens composeFold other

  def compose[C](other: Getter[A, C]): Getter[S, C] =
    lens composeGetter other

  def compose[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] =
    lens composeSetter other

  def compose[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] =
    lens composeTraversal other

  def compose[C, D](other: POptional[A, B, C, D]): POptional[S, T, C, D] =
    lens composeOptional other

  def compose[C, D](other: PPrism[A, B, C, D]): POptional[S, T, C, D] =
    lens composePrism other

  def compose[C, D](other: PLens[A, B, C, D]): PLens[S, T, C, D] =
    lens composeLens other

  def compose[C, D](other: PIso[A, B, C, D]): PLens[S, T, C, D] =
    lens composeIso other

  // aliases
  def ^[C](other: Fold[A, C]): Fold[S, C] = compose(other)
  def ^[C](other: Getter[A, C]): Getter[S, C] = compose(other)
  def ^[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] = compose(other)
  def ^[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] = compose(other)
  def ^[C, D](other: POptional[A, B, C, D]): POptional[S, T, C, D] = compose(other)
  def ^[C, D](other: PPrism[A, B, C, D]): POptional[S, T, C, D] = compose(other)
  def ^[C, D](other: PLens[A, B, C, D]): PLens[S, T, C, D] = compose(other)
  def ^[C, D](other: PIso[A, B, C, D]): PLens[S, T, C, D] = compose(other)
}

final case class IsoComposeOps[S, T, A, B](iso:PIso[S, T, A, B]) {

  def compose[C](other: Fold[A, C]): Fold[S, C] =
    iso composeFold other

  def compose[C](other: Getter[A, C]): Getter[S, C] =
    iso composeGetter other

  def compose[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] =
    iso composeSetter other

  def compose[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] =
    iso composeTraversal other

  def compose[C, D](other: POptional[A, B, C, D]): POptional[S, T, C, D] =
    iso composeOptional other

  def compose[C, D](other: PPrism[A, B, C, D]): PPrism[S, T, C, D] =
    iso composePrism other

  def compose[C, D](other: PLens[A, B, C, D]): PLens[S, T, C, D] =
    iso composeLens other

  def compose[C, D](other: PIso[A, B, C, D]): PIso[S, T, C, D] =
    iso composeIso other

  // aliases
  def ^[C](other: Fold[A, C]): Fold[S, C] = compose(other)
  def ^[C](other: Getter[A, C]): Getter[S, C] = compose(other)
  def ^[C, D](other: PSetter[A, B, C, D]): PSetter[S, T, C, D] = compose(other)
  def ^[C, D](other: PTraversal[A, B, C, D]): PTraversal[S, T, C, D] = compose(other)
  def ^[C, D](other: POptional[A, B, C, D]): POptional[S, T, C, D] = compose(other)
  def ^[C, D](other: PPrism[A, B, C, D]): PPrism[S, T, C, D] = compose(other)
  def ^[C, D](other: PLens[A, B, C, D]): PLens[S, T, C, D] = compose(other)
  def ^[C, D](other: PIso[A, B, C, D]): PIso[S, T, C, D] = compose(other)
}
