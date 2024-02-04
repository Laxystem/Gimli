package quest.laxla.gimli.util

public actual fun fetchEnvironmentVariables(
    defaults: Map<String, String>
): ImmutableMap<String, String> = emptyPersistentMap()