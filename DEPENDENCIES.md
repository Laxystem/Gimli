# Project: Gimli's Dependencies

Dependencies are listed the following way:

* **Dependency Name** (`identifier`; LICENSE), usage

Dependency versions can be found in [gradle.properties](gradle.properties),
listed under their `identifier`.

This list isn't complete.
If you spot any mistakes or dependencies we haven't mentioned, feel free to PR.
Note this list contains behaviour that is *planned*, but not yet implemented.
Note we try including note-worthy dependencies only, that is, software either used directly by Gimli,
or essential to the functionality of a dependency.

For a list of protocols implemented by Project: Gimli, see [FEDERATION.md](FEDERATION.md).

## Alphabetically Sorted List of Dependencies

* **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform)** (Apache 2.0),
  a Kotlin UI framework developed by Google for Android, and ported to Kotlin/Multiplatform by JetBrains.
* **[Exposed](https://github.com/JetBrains/Exposed)** (`exposed`; Apache 2.0),
  SQL for Kotlin/JVM.
* **[Extism](https://extism.org) Java [SDK](https://github.com/extism/java-sdk)** (BSD 3-Clause),
  a source-visible WebAssembly host. Temporarily used by Gimli's official clients to interact with OpenMLS,
  and will be replaced by AndroidX's Messaging Layer Security implementation.
* **[IntelliJ Markdown](https://github.com/JetBrains/Markdown/)** (Apache 2.0),
  a Kotlin/Multiplatform Markdown-to-HTML compiler, supporting various flavours.
* **[Jakarta](https://jakarta.ee)** (`jakarta`; EPL 2.0),
  formerly known as JavaX and Java Enterprise, an extension to Java's standard library.
* **Java Virtual Machine** (`jdk`; GPL 2.0 with Classpath Exception),
  a virtual machine running Gimli's servers and clients, with an exception of the web app.
  Any distribution of the JVM works, but Gimli mostly uses [Eclipse Temurin](https://adoptium.net/temurin)
  and [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime).
* **[Kotlin](https://kotlinlang.org)** (`kotlin`; Apache 2.0),
  the multiplatform language Gimli is written in.
* **[Kotlin Logging](https://github.com/oshai/kotlin-logging)** (`klogging`; Apache 2.0),
  a multiplatform logging library.
* **[KotlinX Datetime](https://GitHub.com/Kotlin/KotlinX-DateTime)** (`datetime`; Apache 2.0),
  date and time utilities.
* **[KotlinX Immutable Collections](https://GitHub.com/Kotlin/KotlinX.Collections.Immutable)** (`collections`; Apache
  2.0),
  safe immutable collections.
* **[KotlinX Coroutines](https://GitHub.com/Kotlin/KotlinX.Coroutines)** (`coroutines`; Apache 2.0),
  structured concurrency.
* **[KotlinX Serialization](https://GitHub.com/Kotlin/KotlinX.Serialization)** (`serialization`; Apache 2.0),
  a Kotlin/Multiplatform compile-time serialization library.
* **[Ktor](https://ktor.io)** (`ktor`; Apache 2.0),
  a Kotlin/Multiplatform server- and client-sided networking library.
* **[Logback](https://logback.qos.ch)** (`logback`; EPL 1.0, LGPL 2.1),
  an implementation of the SLF4J API.
* **[Multiplatform Markdown Renderer](https://github.com/mikepenz/multiplatform-markdown-renderer)** (Apache 2.0),
  a Compose Multiplatform Markdown renderer.
* **[OpenMLS](https://openmls.tech)** (MIT),
  an implementation of the Messaging Layer Security protocol written in Rust.
* **[PGJDBC-NG](https://impossibl.github.io/pgjdbc-ng/)** (`postgres`; Custom),
  Java Database Connectivity API driver for PostgreSQL.
  Will be replaced with the official PostgreSQL driver.
* **[PostgreSQL](https://postgresql.org)** (PostgreSQL License),
  an open source SQL dialect.
* **[SLF4J](https://slf4j.org)** (MIT),
  a logging API for the JVM, unifying the various logging frameworks it has. Used by Kotlin Logging on the JVM.
* **[Titanium](https://github.com/filip26/titanium-json-ld)** (`titanium`; Apache 2.0),
  an implementation of JsonLD written in Java.
* **[WebAssembly](https://webassembly.org)** (Apache 2.0),
  a modular virtual machine runnable both standalone and within the browser.
  Gimli's web client compiles to Wasm, and official clients uses Wasm libraries via Extism.