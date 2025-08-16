package me.jakejmattson.crop

import java.awt.image.BufferedImage

private fun Int.isBlack() = this == -16777216

// TODO Scan for solid black lines instead of fragile 3 in a row

fun BufferedImage.seekTop(centerX: Int, centerY: Int): Int {
    var count = 0

    for (i in centerY downTo 0) {
        if (getRGB(centerX, i).isBlack()) {
            count++
            if (count == 3) {
                return i + 2
            }
        } else {
            count = 0
        }
    }

    return 0
}

fun BufferedImage.seekLeft(centerX: Int, centerY: Int): Int {
    var count = 0

    for (i in centerX downTo 0) {
        if (getRGB(i, centerY).isBlack()) {
            count++
            if (count == 3) {
                return i + 2
            }
        } else {
            count = 0
        }
    }

    return 0
}

fun BufferedImage.seekBottom(centerX: Int, centerY: Int, height: Int): Int {
    var count = 0

    for (i in centerY until height) {
        if (getRGB(centerX, i).isBlack()) {
            count++
            if (count == 3) {
                return i - 2
            }
        } else {
            count = 0
        }
    }

    return height
}

fun BufferedImage.seekRight(centerX: Int, centerY: Int, width: Int): Int {
    var count = 0

    for (i in centerX until width) {
        if (getRGB(i, centerY).isBlack()) {
            count++
            if (count == 3) {
                return i - 2
            }
        } else {
            count = 0
        }
    }

    return width
}