package quest.laxla.gimli

interface Railway : Element.Federalized<Railway> {
    val guild: Guild
    val startFromTop: Boolean get() = false
}
