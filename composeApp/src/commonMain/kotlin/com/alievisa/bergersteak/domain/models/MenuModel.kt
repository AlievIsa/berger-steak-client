package com.alievisa.bergersteak.domain.models

data class MenuModel(
    val categories: List<CategoryModel>,
)

fun MenuModel.toDishesList() = this.categories.map { it.dishes }.flatten()

val menuMock = MenuModel(
    listOf(
        CategoryModel(
            id = 0,
            name = "Бургеры",
            dishes = listOf(
                DishModel(
                    id = 0,
                    name = "Бёргер с яйцом халяль",
                    price = 389,
                    description = "Бёргер с яйцом халяль – завтрак для тех, кто ценит вкусную и сытную пищу. Сочная булка с кунжутом, аппетитный салат айсберг, ароматный сыр, спелые помидоры, хрустящие маринованные огурцы, вкусная яичница – всё это делает бёргер с яйцом халяль по-настоящему особенным. Насладитесь богатым вкусом и приятным ароматом этого блюда, не выходя из дома!",
                    calories = 850,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 1,
                    name = "Бергер бургер",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 850,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 2,
                    name = "Бергер бургер",
                    price = 1090,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 3,
                    name = "Бергер бургер",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 4,
                    name = "Бергер бургер",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
            )
        ),
        CategoryModel(
            id = 1,
            name = "Роллы",
            dishes = listOf(
                DishModel(
                    id = 0,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 1,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 2,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 3,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 4,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 5,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
            )
        ),
        CategoryModel(
            id = 2,
            name = "Снэки",
            dishes = listOf(
                DishModel(
                    id = 0,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 1,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 2,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 3,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 4,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 5,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                )
            )
        ),
        CategoryModel(
            id = 3,
            name = "Десерты",
            dishes = listOf(
                DishModel(
                    id = 0,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 1,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 2,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
            )
        ),
        CategoryModel(
            id = 4,
            name = "Хот-доги",
            dishes = listOf(
                DishModel(
                    id = 0,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 1,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
            )
        ),
        CategoryModel(
            id = 5,
            name = "Напитки",
            dishes = listOf(
                DishModel(
                    id = 0,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 1,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 2,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
            )
        ),
        CategoryModel(
            id = 6,
            name = "Супы",
            dishes = listOf(
                DishModel(
                    id = 0,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 1,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 2,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
            )
        ),
        CategoryModel(
            id = 7,
            name = "Кофе",
            dishes = listOf(
                DishModel(
                    id = 0,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 1,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
                DishModel(
                    id = 2,
                    name = "Бергер ролл",
                    price = 390,
                    description = "Сочный мощный",
                    calories = 450,
                    weight = 250,
                    image = "https://res.cloudinary.com/dzkwtm6yw/image/upload/v1746390352/image_1_njpbou.png"
                ),
            )
        ),
    )
)

val dishesListMock = menuMock.categories.map { it.dishes }.flatten()

fun List<DishModel>.findDishById(id: Int) = this.firstOrNull { it.id == id }