// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies.dependency

import sbtpolicies.library.LibraryCollection

private[dependency] object Dependencies {

  private lazy val scalaLibraries = LibraryCollection { builder =>
    import builder._ // scalastyle:ignore import.grouping underscore.import

    // Macros and compiler supports

    add("org.scalamacros", "paradise", "2.1.1")
    add("scalameta-paradise" -> lib("org.scalameta", "paradise", "3.0.0-M10"))

    add("org.spire-math", "kind-projector", "0.9.4")

    // Testing

    add("org.scalatest", "scalatest", "3.0.4")

    // Logging

    using("org.slf4j", "1.7.25") { (org, rev) =>
      add(org, "slf4j-simple", rev)
    }

    // Configuration

    add("com.github.pureconfig", "pureconfig", "0.8.0")

    // Frameworks

    using("org.typelevel") { org =>
      add(org, "cats-effect", "0.4")
    }

    using("org.scalaz", "7.2.16") { (org, rev) =>
      add(org, "scalaz-concurrent", rev)
    }

    using("io.frees", "0.3.1") { (org, rev) =>
      add("frees" -> lib(org, "freestyle", rev))
    }

    // Persistence

    add("org.flywaydb", "flyway-core", "4.2.0")
    add("postgresql", "postgresql", "9.1-901-1.jdbc4")

    using("org.tpolecat", "0.5.0-M8") { (org, rev) =>
      add(org, "doobie-core", rev)
      add(org, "doobie-postgres", rev)
    }

    // Serialization

    using("io.circe", "0.8.0") { (org, rev) =>
      add(org, "circe-generic", rev)
      add(org, "circe-generic-extras", rev)
      add(org, "circe-parser", rev)
    }

    // API

    using("org.sangria-graphql") { org =>
      add(org, "sangria", "1.3.0")
      add(org, "sangria-circe", "1.1.0")
    }

    // HTTP

    add("com.typesafe.akka", "akka-http", "10.0.10")
    add("de.heikoseeberger", "akka-http-circe", "1.18.1")

    using("org.http4s", "0.18.0-M4") { (org, rev) =>
      add(org, "http4s-blaze-client", rev)
      add(org, "http4s-circe", rev)
    }

    // UI

    using("com.github.japgolly.scalajs-react", "1.1.0") { (org, rev) =>
      add("scalajs-react-core" -> lib(org, "core", rev))
      add("scalajs-react-extra" -> lib(org, "extra", rev))
    }

    // Utilities

    add("joda-time", "joda-time", "2.9.9")
  }

  private lazy val npmLibraries = LibraryCollection { builder =>
    import builder._ // scalastyle:ignore import.grouping underscore.import

    // Dependencies

    add("antd", "2.13.3")
    add("less", "2.7.2")
    add("react-apollo", "1.4.16")
    add("tachyons", "4.8.1")
    add("moment", "2.19.1")

    using("15.6.1") { rev =>
      add("react", rev)
      add("react-dom", rev)
    }

    // Dev dependencies

    add("css-loader", "0.28.7")
    add("extract-text-webpack-plugin", "3.0.0")
    add("less-loader", "4.0.5")
    add("style-loader", "0.18.2")
    add("webpack", "3.6.0")
    add("webpack-dev-server", "2.9.1")
  }

  lazy val libraries: LibraryCollection = scalaLibraries ++ npmLibraries
}
