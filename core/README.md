<!-- This file is auto-generated. Edit module.md instead. -->
# [Project: Gimli](../README.md) Core

The basic public API of Project: Gimli.

Defines a concurrent extensions framework, supporting:

* Dynamic loading/unloading
* Dependency relations
* Event propagation
* Stateless extensions

## Artifacts

<details><summary>Gradle Kotlin</summary>

```kotlin
repositories {
    maven(url = "https://codeberg.org/api/packages/Moria/maven/")
}

dependencies {
    implementation("quest.laxla.gimli:core:0.0.1-alpha.3")
}
```

</details>
<details><summary>Gradle Groovy</summary>

```groovy
repositories {
    maven url: "https://codeberg.org/api/packages/Moria/maven/"
}

dependencies {
    implementation "quest.laxla.gimli:core:0.0.1-alpha.3"
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
    	<artifactId>core</artifactId>
    	<version>0.0.1-alpha.3</version>
    </dependency>
</dependencies>
```

</details>

## Subprojects
* [Implementation (`impl`)](impl/README.md) – the default implementation of the extensions API.
