/* =========================================================================================
 * Copyright © 2013-2014 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * =========================================================================================
 */

import sbt._
import sbt.Keys._

object Publish {

  lazy val settings = Seq(
    pomExtra := kamonPomExtra,
    publishTo := kamonRepo,
    organization := "io.kamon",
    pomIncludeRepository := { x => false },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
  )

  def kamonRepo = Some("Vungle 3rd party repository" at "https://nexus.vungle.com/mnt2/nexus/content/repositories/thirdparty/")

  def kamonPomExtra = {
    <url>http://kamon.io</url>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        </license>
      </licenses>
      <scm>
        <url>git://github.com/kamon-io/Kamon.git</url>
        <connection>scm:git:git@github.com:kamon-io/Kamon.git</connection>
      </scm>
      <developers>
        <developer><id>ivantopo</id><name>Ivan Topolnjak</name><url>https://twitter.com/ivantopo</url></developer>
        <developer><id>dpsoft</id><name>Diego Parra</name><url>https://twitter.com/diegolparra</url></developer>
      </developers>
  }
}
