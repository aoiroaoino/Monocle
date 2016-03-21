import sbt._
import org.scalajs.sbtplugin.cross._

object MonocleCrossType extends CrossType {

  override def projectDir(crossBase: File, projectType: String) =
    crossBase / projectType

  def shared(projectBase: File, conf: String) =
    projectBase.getParentFile / "src" / conf / "scala"

  override def sharedSrcDir(projectBase: File, conf: String) =
    Some(shared(projectBase, conf))
}
