package cinema

import kotlin.Exception

val grid = mutableListOf<MutableList<String>>()
var income = 0
var purchasedSeats = 0
var booked: Boolean? = null

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    createGrid(seats)

    while (true) {
        println("\n1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")

        when (readln().toInt()) {
            1 -> drawTheatre(rows, seats)
            2 -> do {
                try {
                    pickSeat(rows, seats)
                } catch (e: Exception) {
                    println(e.message)
                } finally {
                    continue
                }
            } while (booked == false)
            3 -> stats(rows, seats)
            0 -> return
        }
    }
}

fun createGrid(seats: Int) {

    for (i in 0..seats) {
        grid.add(mutableListOf("S"))
    }

    for (i in 0..seats) {
        repeat(seats - 1) {
            grid[i].add("S")
        }
    }
}

fun drawTheatre(rows: Int, seats: Int) {
    println("\nCinema:")

    // top row numbers
    for (i in 0..seats) {
        if (i == 0) print(" ")
        else print(i)
        print(" ")
    }
    print("\n")

    // print each row number + seats
    for (i in 1..rows) {
        print("$i ")
        println(grid[i].joinToString(" "))
    }
}

fun pickSeat(rows: Int, seats: Int) {
    booked = false

    println("\nEnter a row number:")
    val chosenRow = readln().toInt()
    println("Enter a seat number in that row:")
    val chosenSeat = readln().toInt()

    if (chosenRow > rows || chosenSeat > seats) throw Exception("\nWrong input!")
    if (grid[chosenRow][chosenSeat - 1] == "B") throw Exception("\nThat ticket has already been purchased!")

    grid[chosenRow][chosenSeat - 1] = "B"
    booked = true

    var price = 0
    when {
        rows * seats <= 60 -> price = 10
        rows * seats > 60 -> {
            if (chosenRow <= (rows / 2)) price = 10
            if (chosenRow >= (rows - (rows / 2))) price = 8
        }
    }

    income += price
    purchasedSeats++
    println("\nTicket price: $$price")
}

fun stats(rows: Int, seats: Int) {
    val percent = "%.2f".format((purchasedSeats / (rows.toDouble() * seats.toDouble())) * 100)

    var totalIncome = 0
    when {
        rows * seats <= 60 -> totalIncome = (rows * seats) * 10
        rows * seats > 60 -> totalIncome = (((rows / 2) * seats) * 10) + (((rows - (rows / 2)) * seats) * 8)
    }

    println("\nNumber of purchased tickets: $purchasedSeats")
    println("Percentage: $percent%")
    println("Current income: $$income")
    println("Total income: $$totalIncome")
}
