package y2020


object Day21 : Day {
    override fun day() = 21

    data class Food(val ingredients: List<String>, val allergens: List<String>)

    private fun getInput() = getInputAsList().filter { it.isNotBlank() }.map { parseFood(it) }

    private fun parseFood(line: String): Food {
        val parts = line.split(" (contains ")
        val ingredients= parts[0].split(" ")
        val allergens= parts[1].takeWhile { it != ')' }.split(", ")
        return Food(ingredients, allergens)
    }

    private fun assignAllergens(foods: List<Food>): Map<String, String> {
        val allergensResult = mutableMapOf<String, String>()

        // fill in possibilities
        val possibilities = foods.fold(mutableMapOf<String, MutableList<MutableSet<String>>>()) { acc, food ->
            food.allergens.forEach { allergen ->
                acc.putIfAbsent(allergen, mutableListOf())
                acc.computeIfPresent(allergen) { _, old -> old.add(food.ingredients.toMutableSet()); old }
            }
            acc
        }
//        foods.forEach { food ->
//            food.allergens.forEach { allergen ->
//                possibilities.compute(allergen) { key, old ->
//                    if (old != null) {
//                        old.add(food.ingredients.toMutableSet())
//                        old
//                    } else {
//                        mutableListOf(food.ingredients.toMutableSet())
//                    }
//                }
//            }
//        }
        val totalAllergens = possibilities.keys.size

        while (allergensResult.keys.size < totalAllergens) {
            // find all intersections available
            val intersections = possibilities.mapValues { (allergen, possibleValues) ->
                val remainingPossibilities = possibleValues.reduce { acc, it -> acc.intersect(it).toMutableSet()}
                remainingPossibilities
            }

            intersections.forEach { (allergen, intersection) ->
                // only one possible outcome
                if (intersection.size == 1) {
                    val ingridient = intersection.first()
                    allergensResult[allergen] = ingridient
                    // remove allergen from search map
                    possibilities.remove(allergen)
                    // remove ingridient from all possible outcomes
                    possibilities.forEach {(_, ingridientList) ->
                        ingridientList.forEach { set -> set.remove(ingridient) }
                    }
                }
            }
        }
        return allergensResult
    }



    fun part1(): Int {
        val input = getInput()

        val allergensResult = assignAllergens(input)

        val allWithAllergen = allergensResult.values.toSet()
        val cantContainAllergens = input.flatMap { it.ingredients }.distinct().subtract(allWithAllergen)
        val appearencesCount = input.map { it.ingredients }.flatMap { ingridients -> ingridients.map { it in cantContainAllergens } }.count { it }
        return appearencesCount
    }

    fun part2(): String {
        val input = getInput()

        val allergensResult = assignAllergens(input)
        val sorted = allergensResult.toList().sortedBy { it.first }
        return sorted.map { it.second }.joinToString(",")
    }
}

fun main() {
    println(Day21.part1())
    println(Day21.part2())
}