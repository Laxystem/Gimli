<!-- This file is auto-generated. Edit module.md instead. -->
# [Project: Gimli](../README.md) Networking

Provides [Ktor](https://ktor.io) support.

## Artifacts

<details><summary>Gradle Kotlin</summary>

```kotlin
repositories {
    maven(url = "https://codeberg.org/api/packages/Moria/maven/")
}

dependencies {
    implementation("quest.laxla.gimli:networking:0.0.1-alpha.3")
}
```

</details>
<details><summary>Gradle Groovy</summary>

```groovy
repositories {
    maven url: "https://codeberg.org/api/packages/Moria/maven/"
}

dependencies {
    implementation "quest.laxla.gimli:networking:0.0.1-alpha.3"
}
```

</details>
<details><summary>Maven</summary>

```xml
<repositories>
	<repository>
		<id>moria</id>
		<url>https://codeberg.org/api/packages/Moria/maven/</url>
	</repository>
</repositories>

<distributionManagement>
	<repository>
		<id>moria</id>
		<url>https://codeberg.org/api/packages/Moria/maven/</url>
	</repository>

	<snapshotRepository>
		<id>moria</id>
		<url>https://codeberg.org/api/packages/Moria/maven/</url>
	</snapshotRepository>
</distributionManagement>

<dependencies>
    <dependency>
    	<groupId>quest.laxla.gimli</groupId>
    	<artifactId>networking</artifactId>
    	<version>0.0.1-alpha.3</version>
    </dependency>
</dependencies>
```

</details>

## Subprojects
* [Client (`client`)](client/README.md) – client-side networking support.
* [Routing (`routing`)](routing/README.md) – provides support for Ktor's [Routing](https://ktor.io/docs/routing-in-ktor.html) and [Resources](https://ktor.io/docs/type-safe-routing.html) plugins.
* [Server (`server`)](server/README.md) – server-side networking support.
* [Routing (`routing`)](routing/README.md) – provides support for Ktor's [Routing](https://ktor.io/docs/routing-in-ktor.html) and [Resources](https://ktor.io/docs/type-safe-routing.html) plugins.
