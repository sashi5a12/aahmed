name :="spark-tutorials"

scalaVersion :="2.10.4"

version :="1.0"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.2.1",
  "org.apache.spark" % "spark-streaming_2.10" % "1.2.1",
  "org.apache.spark" % "spark-sql_2.10" % "1.2.1"
)