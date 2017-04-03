package monocle

import monocle.macros.{GenLens, MakeClassy, Lenses}
import shapeless._


// ====== derived codes

//object Foo {
//
//  def fooStr[A: HasFoo]: Lens[A, String] =
//    implicitly[HasFoo[A]].lens.composeLens(GenLens[Foo](_.str))
//
//  trait HasFoo[A] {
//    def lens: Lens[A, Foo]
//  }
//
//  object HasFoo {
//
//    implicit val fooHasFoo: HasFoo[Foo] = new HasFoo[Foo] {
//      override val lens: Lens[Foo, Foo] = Lens.id
//    }
//
//    object typeClass extends ProductTypeClass[HasFoo] {
//
//      // これを導出したい
//      implicit val fooBarHasFoo = new HasFoo[FooBar] {
//        override def lens = Lens[FooBar, Foo](_.foo)(foo => foobar => foobar.copy(foo = foo))
//      }
//
//      override def emptyProduct = new HasFoo[HNil] {
//        override def lens: Lens[HNil, Foo] = ???
//      }
//
//      override def product[H, T <: HList](ch: HasFoo[H], ct: HasFoo[T]) = new HasFoo[H :: T] {
//        override val lens: Lens[H :: T, Foo] = Lens(
//
//        ) (
//
//        )
//      }
//
//      override def project[F, G](instance: => HasFoo[G], to: (F) => G, from: (G) => F) = new HasFoo[F] {
//        override val lens: Lens[F, Foo] = Lens(
//          f => instance.lens.get(to(f))
//        ) (
//          foo => f => from(instance.lens.set(foo)(to(f)))
//        )
//      }
//    }
//  }
//}


object Usage {
  import monocle.state.all._

  @MakeClassy
  case class Foo(str: String)

  case class Bar(num: Int)

  case class FooBar(foo: Foo, bar: Bar)

  val foo = Foo("foo str")
  val bar = Bar(100)
  val foobar = FooBar(foo, bar)

  def test1[A: Foo.HasFoo] =  for {
    l <- Foo.str.assigno("foobar str")
  } yield l

  assert(Foo.str.get(foo) == "foo str")
  assert(test1.exec(foo) == foo.copy(str = "foobar st"))

  object hoge {
      implicit val foobarHasFoo = new Foo.HasFoo[FooBar] {
        override val lens = GenLens[FooBar](_.foo)
      }

      assert(Foo.str.get(foobar) == "foo str")
      assert(test1.exec(foobar) == foobar.copy(foo = foobar.foo.copy(str = "foobar str")))
  }
}

@MakeClassy
//@Lenses
case class Nyan(name: String, age: Int)
