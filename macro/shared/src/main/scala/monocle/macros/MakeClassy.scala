package monocle.macros

import scala.reflect.macros.blackbox
import scala.annotation.StaticAnnotation

class MakeClassy(prefix: String = "") extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro MakeClassyImpl.makeClassyAnnotationMacro
}

@macrocompat.bundle
private[macros] class MakeClassyImpl(val c: blackbox.Context) {

  def makeClassyAnnotationMacro(annottees: c.Expr[Any]*): c.Expr[Any] = annotationMacro(annottees)

  def annotationMacro(annottees: Seq[c.Expr[Any]]): c.Expr[Any] = {
    import c.universe._

    val prefix = "" // todo

    def typeClassTypeName(tpname: TypeName) = TypeName("Has" + tpname.toTermName.toString)
    def typeClassTermName(tpname: TypeName) = TermName("Has" + tpname.toTermName.toString)

    def typeClassDef(tpname: TypeName) = {
      val name = typeClassTypeName(tpname)
      val valName = TermName("__" + name)
      q"""
        trait $name[A] { def lens: Lens[A, $tpname] }
        object ${typeClassTermName(tpname)} {
          implicit def $valName: $name[$tpname] = new $name[$tpname] {
            override val lens: Lens[$tpname, $tpname] = Lens.id
          }
        }
      """
    }

    def lensDefs(tpname: TypeName, tparams: List[TypeDef], params: List[ValDef]): List[Tree] = {
      if (tparams.isEmpty) {
        val name = typeClassTypeName(tpname)
        params.map { param =>
          q"""
            def ${param.name}[A](implicit x: $name[A]): Lens[A, ${param.tpt}] =
              x.lens.composeLens(
                monocle.macros.internal.Macro.mkLens[$tpname, $tpname, ${param.tpt}, ${param.tpt}](${param.name.toString}))
          """
        }
      } else {
        c.abort(c.enclosingPosition, "Sorry")
      }
    }

    val result = annottees.map(_.tree) match {
      case (classDef @ q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$stats }")
        :: Nil if mods.hasFlag(Flag.CASE) =>
        val name = tpname.toTermName
        q"""
         $classDef
         object $name {
           ..${lensDefs(tpname, tparams, paramss.head)}
           ..${typeClassDef(tpname)}
         }
        """
      case (classDef @ q"$mods class $tpname[..$tparams] $ctorMods(...$paramss) extends { ..$earlydefns } with ..$parents { $self => ..$stats }")
        :: q"object $objName extends { ..$objEarlyDefs } with ..$objParents { $objSelf => ..$objDefs }"
        :: Nil if mods.hasFlag(Flag.CASE) =>
        q"""
         $classDef
         object $objName extends { ..$objEarlyDefs} with ..$objParents { $objSelf =>
           ..${lensDefs(tpname, tparams, paramss.head)}
           ..${typeClassDef(tpname)}
           ..$objDefs
         }
        """
      case _ => c.abort(c.enclosingPosition, "Invalid annotation target: must be a case class")
    }

    c.Expr[Any](result)
  }
}
