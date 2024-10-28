file://<WORKSPACE>/src/main/scala/c/x/wzs/restful/User.scala
### java.lang.ArrayIndexOutOfBoundsException: Index 224 out of bounds for length 219

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<WORKSPACE>/src/main/resources [exists ], <WORKSPACE>/.bloop/root/bloop-bsp-clients-classes/classes-Metals-Z8RQDpYES0a5VDCuQUhiuQ== [exists ], <HOME>/Library/Caches/bloop/semanticdb/com.sourcegraph.semanticdb-javac.0.10.0/semanticdb-javac-0.10.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.3.3/scala3-library_3-3.3.3.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/typesafe/play/twirl-api_3/1.6.2/twirl-api_3-1.6.2.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio_3/2.1.6/zio_3-2.1.6.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-json_3/0.6.2/zio-json_3-0.6.2.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-http_3/3.0.0-RC6/zio-http_3-3.0.0-RC6.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/getquill/quill-zio_3/4.8.0/quill-zio_3-4.8.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/getquill/quill-jdbc-zio_3/4.8.0/quill-jdbc-zio_3-4.8.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-config_3/4.0.0-RC16/zio-config_3-4.0.0-RC16.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-config-typesafe_3/4.0.0-RC16/zio-config-typesafe_3-4.0.0-RC16.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-config-magnolia_3/4.0.0-RC16/zio-config-magnolia_3-4.0.0-RC16.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-logging_3/2.1.15/zio-logging_3-2.1.15.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-logging-slf4j_3/2.1.15/zio-logging-slf4j_3-2.1.15.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-macros_3/2.1.6/zio-macros_3-2.1.6.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.12/scala-library-2.13.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-xml_3/2.2.0/scala-xml_3-2.2.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-internal-macros_3/2.1.6/zio-internal-macros_3-2.1.6.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-stacktracer_3/2.1.6/zio-stacktracer_3-2.1.6.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/izumi-reflect_3/2.3.8/izumi-reflect_3-2.3.8.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-streams_3/2.1.6/zio-streams_3-2.1.6.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-collection-compat_3/2.11.0/scala-collection-compat_3-2.11.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/softwaremill/magnolia1_3/magnolia_3/1.3.0/magnolia_3-1.3.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-schema_3/1.1.0/zio-schema_3-1.1.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-schema-json_3/1.1.0/zio-schema-json_3-1.1.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-schema-protobuf_3/1.1.0/zio-schema-protobuf_3-1.1.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-codec-http/4.1.101.Final/netty-codec-http-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-handler-proxy/4.1.101.Final/netty-handler-proxy-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport-native-epoll/4.1.101.Final/netty-transport-native-epoll-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport-native-epoll/4.1.101.Final/netty-transport-native-epoll-4.1.101.Final-linux-x86_64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport-native-epoll/4.1.101.Final/netty-transport-native-epoll-4.1.101.Final-linux-aarch_64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport-native-kqueue/4.1.101.Final/netty-transport-native-kqueue-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport-native-kqueue/4.1.101.Final/netty-transport-native-kqueue-4.1.101.Final-osx-x86_64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport-native-kqueue/4.1.101.Final/netty-transport-native-kqueue-4.1.101.Final-osx-aarch_64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/incubator/netty-incubator-transport-native-io_uring/0.0.24.Final/netty-incubator-transport-native-io_uring-0.0.24.Final-linux-x86_64.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/getquill/quill-sql_3/4.8.0/quill-sql_3-4.8.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-java8-compat_3/1.0.2/scala-java8-compat_3-1.0.2.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/getquill/quill-jdbc_3/4.8.0/quill-jdbc_3-4.8.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/zaxxer/HikariCP/5.0.1/HikariCP-5.0.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/typesafe/config/1.4.2/config-1.4.2.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-config-derivation_3/4.0.0-RC16/zio-config-derivation_3-4.0.0-RC16.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-parser_3/0.1.9/zio-parser_3-0.1.9.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-managed_3/2.1.6/zio-managed_3-2.1.6.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/izumi-reflect-thirdparty-boopickle-shaded_3/2.3.8/izumi-reflect-thirdparty-boopickle-shaded_3-2.3.8.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-schema-macros_3/1.1.0/zio-schema-macros_3-1.1.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-prelude_3/1.0.0-RC21/zio-prelude_3-1.0.0-RC21.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-constraintless_3/0.3.2/zio-constraintless_3-0.3.2.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-schema-derivation_3/1.1.0/zio-schema-derivation_3-1.1.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-common/4.1.101.Final/netty-common-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-buffer/4.1.101.Final/netty-buffer-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport/4.1.101.Final/netty-transport-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-codec/4.1.101.Final/netty-codec-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-handler/4.1.101.Final/netty-handler-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-codec-socks/4.1.101.Final/netty-codec-socks-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport-native-unix-common/4.1.101.Final/netty-transport-native-unix-common-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport-classes-epoll/4.1.101.Final/netty-transport-classes-epoll-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-transport-classes-kqueue/4.1.101.Final/netty-transport-classes-kqueue-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/incubator/netty-incubator-transport-classes-io_uring/0.0.24.Final/netty-incubator-transport-classes-io_uring-0.0.24.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/suzaku/boopickle_3/1.4.0/boopickle_3-1.4.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/pprint_3/0.8.1/pprint_3-0.8.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/getquill/quill-engine_3/4.8.0/quill-engine_3-4.8.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/getquill/quill-util_3/4.8.0/quill-util_3-4.8.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/typesafe/scala-logging/scala-logging_3/3.9.5/scala-logging_3-3.9.5.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.21.9/protobuf-java-3.21.9.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/dev/zio/zio-prelude-macros_3/1.0.0-RC21/zio-prelude-macros_3-1.0.0-RC21.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/netty/netty-resolver/4.1.101.Final/netty-resolver-4.1.101.Final.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/fansi_3/0.4.0/fansi_3-0.4.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/lihaoyi/sourcecode_3/0.3.0/sourcecode_3-0.3.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/github/takayahilton/sql-formatter_2.13/1.2.1/sql-formatter_2.13-1.2.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/github/ben-manes/caffeine/caffeine/3.1.8/caffeine-3.1.8.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalameta/scalafmt-core_2.13/3.7.14/scalafmt-core_2.13-3.7.14.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/modules/scala-parallel-collections_3/1.0.4/scala-parallel-collections_3-1.0.4.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/checkerframework/checker-qual/3.37.0/checker-qual-3.37.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.21.1/error_prone_annotations-2.21.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalameta/scalafmt-sysops_2.13/3.7.14/scalafmt-sysops_2.13-3.7.14.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalameta/scalafmt-config_2.13/3.7.14/scalafmt-config_2.13-3.7.14.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalameta/scalameta_2.13/4.8.10/scalameta_2.13-4.8.10.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.13.12/scala-reflect-2.13.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/geirsson/metaconfig-core_2.13/0.11.1/metaconfig-core_2.13-0.11.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/geirsson/metaconfig-typesafe-config_2.13/0.11.1/metaconfig-typesafe-config_2.13-0.11.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalameta/parsers_2.13/4.8.10/parsers_2.13-4.8.10.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scalap/2.13.12/scalap-2.13.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/com/geirsson/metaconfig-pprint_2.13/0.11.1/metaconfig-pprint_2.13-0.11.1.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/typelevel/paiges-core_2.13/0.4.2/paiges-core_2.13-0.4.2.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalameta/trees_2.13/4.8.10/trees_2.13-4.8.10.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-compiler/2.13.12/scala-compiler-2.13.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scalameta/common_2.13/4.8.10/common_2.13-4.8.10.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/io/github/java-diff-utils/java-diff-utils/4.12/java-diff-utils-4.12.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/jline/jline/3.22.0/jline-3.22.0.jar [exists ], <HOME>/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/net/java/dev/jna/jna/5.13.0/jna-5.13.0.jar [exists ]
Options:
-Ymacro-annotations -Xsemanticdb -sourceroot <WORKSPACE>


action parameters:
uri: file://<WORKSPACE>/src/main/scala/c/x/wzs/restful/User.scala
text:
```scala
package c.x.wzs.restful

import java.util.UUID
import zio.json.*
import zio.schema._
import zio.schema.DeriveSchema._

case class User (name: String, age: Int)

object User:
  given Schema[User] = DeriveSchema.gen[User]
```



#### Error stacktrace:

```
scala.meta.internal.mtags.MtagsEnrichments$.adjust(MtagsEnrichments.scala:137)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.collect(PcSemanticTokensProvider.scala:79)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.collect(PcSemanticTokensProvider.scala:62)
	scala.meta.internal.pc.PcCollector.scala$meta$internal$pc$PcCollector$$_$collect$1(PcCollector.scala:104)
	scala.meta.internal.pc.PcCollector.collectNamesWithParent$1(PcCollector.scala:147)
	scala.meta.internal.pc.PcCollector.$anonfun$9(PcCollector.scala:291)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:310)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:312)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	scala.meta.internal.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:312)
	scala.meta.internal.pc.PcCollector$DeepFolderWithParent.apply(PcCollector.scala:318)
	scala.meta.internal.pc.PcCollector.traverseSought(PcCollector.scala:292)
	scala.meta.internal.pc.PcCollector.traverseSought$(PcCollector.scala:25)
	scala.meta.internal.pc.SimpleCollector.traverseSought(PcCollector.scala:370)
	scala.meta.internal.pc.PcCollector.resultAllOccurences(PcCollector.scala:38)
	scala.meta.internal.pc.PcCollector.resultAllOccurences$(PcCollector.scala:25)
	scala.meta.internal.pc.SimpleCollector.resultAllOccurences(PcCollector.scala:370)
	scala.meta.internal.pc.SimpleCollector.result(PcCollector.scala:375)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:90)
	scala.meta.internal.pc.ScalaPresentationCompiler.semanticTokens$$anonfun$1(ScalaPresentationCompiler.scala:117)
```
#### Short summary: 

java.lang.ArrayIndexOutOfBoundsException: Index 224 out of bounds for length 219