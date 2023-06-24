val datadogPackageName = "com.datadoghq"
val datadogAgentName = "dd-java-agent"
val datadogAgentVersion = "1.16.2"
val ddJavaAgent = datadogPackageName % datadogAgentName % datadogAgentVersion

val jvmArgs = s"""
                 |addJava "-javaagent:$${app_home}/../lib/$datadogPackageName.$datadogAgentName-$datadogAgentVersion.jar"
                 |addJava "-XX:+FlightRecorder"
                 |addJava "-XX:FlightRecorderOptions=stackdepth=256"
                 |addJava "-Ddd.profiling.enabled=true"
                 |""".stripMargin

val kamonVersion = "2.6.1"
val kamonCore = "io.kamon" %% "kamon-core" % kamonVersion
val kamonAkka = "io.kamon" %% "kamon-akka" % kamonVersion
val kamonStatusPage = "io.kamon" %% "kamon-status-page" % kamonVersion
val kamonBundle = "io.kamon" %% "kamon-bundle" % kamonVersion
val kamonDataDog = "io.kamon" %% "kamon-datadog" % kamonVersion
val kamonPrometheus =  "io.kamon" %% "kamon-prometheus" % kamonVersion

val kamonDependencies = Seq(
  kamonBundle,
//  kamonCore,
//  kamonAkka,
//  kamonStatusPage,
  kamonDataDog,
  kamonPrometheus
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, JavaAgent)
  .settings(
    name := """play-scala-starter-example""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.10",
    bashScriptExtraDefines += jvmArgs,
    libraryDependencies ++= Seq(
      guice,
      "com.h2database" % "h2" % "1.4.200",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
//    javaAgents += "io.kamon" % "kanela-agent" % "1.0.17",
    libraryDependencies ++= kamonDependencies :+ ddJavaAgent,
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
