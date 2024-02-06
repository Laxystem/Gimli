package quest.laxla.gimli

public enum class Specification(
    public val context: String,
    public val baseUrl: String = "$context#"
) {
    ActivityPub(context = "https://www.w3.org/ns/activitystreams"),
    ForgeFed(context = "https://forgefed.org/ns"),
    GimliSpecification(context = "https://gimli.laxla.quest/namespace.jsonld"), // TODO: actually upload something there lmao
    FediverseEnhancementProposal(
        context = "https://codeberg.org/fediverse/fep/raw/branch/main/fep/2e40/namespace.json",
        baseUrl = "https://w3id.org/fep#"
    );

    @MustBeDocumented
    @Repeatable
    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
    public annotation class Compliance(
        val specification: Specification,
        val name: String,
        val level: Level = Level.Complete
    ) {
        public enum class Level {
            Complete, Partial, Planned
        }
    }
}
