scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
    "org.mockito" % "mockito-core" % "1.10.8",
    "com.novocode" % "junit-interface" % "0.11" % Test,
    "org.backuity" %% "matchete" % "1.11" % Test,
    "com.chuusai" %% "shapeless" % "2.0.0",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value)