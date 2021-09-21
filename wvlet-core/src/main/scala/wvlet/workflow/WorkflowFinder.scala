package wvlet.workflow

import java.lang.reflect.Modifier

import wvlet.log.io.Resource

/**
  */
object WorkflowFinder {

  def findWorkflowClasses(
      packageName: String,
      classLoader: ClassLoader = Thread.currentThread().getContextClassLoader
  ): Seq[Class[_]] = {

    def componentName(path: String): Option[String] = {
      val dot: Int = path.lastIndexOf(".")
      if (dot <= 0) {
        None
      } else {
        Some(path.substring(0, dot).replaceAll("/", "."))
      }
    }
    def findClass(name: String): Option[Class[_]] = {
      try Some(Class.forName(name, false, classLoader))
      catch {
        case e: ClassNotFoundException => None
      }
    }

    val classFiles = Resource.listResources(packageName, _.endsWith(".class"), classLoader)
    val b          = Seq.newBuilder[Class[_]]
    for (vf <- classFiles; cn <- componentName(vf.logicalPath)) {
      val className: String = packageName + "." + cn
      for (cl <- findClass(className)) {
        val w = cl.getAnnotation(classOf[workflow])
        if (w != null) {
          b += cl
        }
      }
    }
    b.result()
  }
}
