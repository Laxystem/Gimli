<!-- This file is auto-generated. Edit module.md instead. -->
# [Project: Gimli](../README.md) Utilities

Contains general utilities, and adds KotlinX Serialization support to more classes.

## Artifacts

<details><summary>Gradle Kotlin</summary>

```kotlin
repositories {
    maven(url = "https://codeberg.org/api/packages/Moria/maven/")
}

dependencies {
    implementation("quest.laxla.gimli:util:0.0.1-alpha.3")
}
```

</details>
<details><summary>Gradle Groovy</summary>

```groovy
repositories {
    maven url: "https://codeberg.org/api/packages/Moria/maven/"
}

dependencies {
    implementation "quest.laxla.gimli:util:0.0.1-alpha.3"
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
    	<artifactId>util</artifactId>
    	<version>0.0.1-alpha.3</version>
    </dependency>
</dependencies>
```

</details>

## Subprojects
* [KotlinX Coroutines (`coroutines`)](coroutines/README.md)
* [JetBrains Exposed (`exposed`)](exposed/README.md)
* [URI (`uri`)](uri/README.md)
