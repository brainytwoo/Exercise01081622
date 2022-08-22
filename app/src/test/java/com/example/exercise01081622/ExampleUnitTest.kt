package com.example.exercise01081622

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val jsonItems = """[
            {"id": 276, "listId": 1, "name": "Item 276"},
            {"id": 684, "listId": 1, "name": "Item 684"},
            {"id": 755, "listId": 2, "name": "Item 755"},
            {"id": 203, "listId": 2, "name": ""},
            {"id": 736, "listId": 3, "name": null},
            {"id": 926, "listId": 4, "name": null},
        ]"""

    private val parsedItems = arrayOf(
        Item(276, 1, "Item 276"),
        Item(684, 1, "Item 684"),
        Item(755, 2, "Item 755"),
        Item(203, 2, ""),
        Item(736, 3, null),
        Item(926, 4, null)
    )

    private val winnowedItems = arrayOf(
        Item(276, 1, "Item 276"),
        Item(684, 1, "Item 684"),
        Item(755, 2, "Item 755")
    )

    @Test
    fun parse_isCorrect() {
        val actualResult = parseItems(jsonItems)
        assertArrayEquals(parsedItems, actualResult)
    }

    @Test
    fun winnowItems_isCorrect() {
        val actualResult = winnowItems(parsedItems)
        assertArrayEquals(winnowedItems, actualResult)
    }
}