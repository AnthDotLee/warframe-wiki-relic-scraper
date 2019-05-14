data class Relic(
val era: String,
val name: String,
val commonRewards: MutableList<String>,
val uncommonRewards: MutableList<String>,
val rareReward: String,
val isVaulted: Boolean = false
)