<!-- This file is auto-generated. Edit module.md instead. -->
# [Project: Gimli Networking](../README.md) Routing

<p style="text-align: center"><i>This module's documentation is empty.</i></p>

## Artifacts

<details><summary>Gradle Kotlin</summary>

```kotlin
repositories {
    maven(url = "https://codeberg.org/api/packages/Moria/maven/")
}

dependencies {
    implementation("quest.laxla.gimli:routing:0.0.1-alpha.3")
}
```

</details>
<details><summary>Gradle Groovy</summary>

```groovy
repositories {
    maven url: "https://codeberg.org/api/packages/Moria/maven/"
}

dependencies {
    implementation "quest.laxla.gimli:routing:0.0.1-alpha.3"
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
    	<artifactId>routing</artifactId>
    	<version>0.0.1-alpha.3</version>
    </dependency>
</dependencies>
```

</details>

## Subprojects
* [Client (`client`)](client/README.md) – client-side routing support.
* [Server (`server`)](server/README.md) – server-side routing support.
