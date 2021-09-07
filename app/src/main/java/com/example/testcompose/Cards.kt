package com.example.testcompose

data class ColumnData(
    val name: String,
    val cards: List<CardData>
)

data class CardData(
    val tags: List<String>,
    val topic: String,
    val avatars: List<Int>
)

object SampleData {
    val cards = listOf(
        CardData(
            listOf("Разработка"),
            "#79 Запуск ОБС",
            listOf()
        ),
        CardData(
            listOf("Чтение", "Обдумывание"),
            "#80 Прочитать документацию",
            listOf(R.drawable.avatar1, R.drawable.avatar1)
        ),
        CardData(
            listOf("Чилл", "Отдых"),
            "#20 Заняться прокрастинацией",
            listOf(R.drawable.avatar1, R.drawable.avatar1)
        ),
        CardData(
            listOf("Разработка"),
            "Сделать парсер",
            listOf(R.drawable.avatar1)
        ),
        CardData(
            listOf("Разработка"),
            "#79 Запуск ОБС",
            listOf(R.drawable.avatar1)
        ),
        CardData(
            listOf("Чтение", "Обдумывание"),
            "#80 Прочитать документацию",
            listOf(R.drawable.avatar1, R.drawable.avatar1)
        )
    )

    val columns = listOf(
        ColumnData(
            "NEW",
            cards
        ),
        ColumnData(
            "READY",
            cards
        ),
        ColumnData(
            "PROG",
            cards
        ),
        ColumnData(
            "TEST",
            cards
        ),
        ColumnData(
            "DONE",
            cards
        )
    )

    val swimlane = listOf(
        "Table 1",
        "Table 2",
        "Table 3"
    )
}
