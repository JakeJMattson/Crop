package me.jakejmattson.crop

import java.awt.image.BufferedImage

class ImageCropper(
    private val image: BufferedImage
) {
    private val height = image.height
    private val width = image.width
    private val centerX = width / 2
    private val centerY = height / 2

    fun crop(): BufferedImage {
        val top = image.seekTop()
        val bottom = image.seekBottom()

        val left = image.seekLeft(top, bottom)
        val right = image.seekRight(top, bottom)

        return image.getSubimage(left, top, right - left, bottom - top)
    }

    fun BufferedImage.seekTop(): Int {
        for (i in centerY downTo 0)
            if (getRGB(centerX, i).isBlack())
                if (confirmHorizontalBorder(i))
                    return i

        return 0
    }

    fun BufferedImage.seekLeft(top: Int, bottom: Int): Int {
        for (i in centerX downTo 0)
            if (getRGB(i, centerY).isBlack())
                if (confirmVerticalBorder(i, top, bottom))
                    return i

        return 0
    }

    fun BufferedImage.seekBottom(): Int {
        for (i in centerY until height)
            if (getRGB(centerX, i).isBlack())
                if (confirmHorizontalBorder(i))
                    return i

        return height
    }

    fun BufferedImage.seekRight(top: Int, bottom: Int): Int {
        for (i in centerX until width)
            if (getRGB(i, centerY).isBlack())
                if (confirmVerticalBorder(i, top, bottom))
                    return i

        return width
    }

    private fun BufferedImage.confirmHorizontalBorder(targetY: Int): Boolean {
        if (targetY == 0 || targetY == height)
            return true

        for (i in 0 until width)
            if (!getRGB(i, targetY).isBlack())
                return false

        return true
    }

    private fun BufferedImage.confirmVerticalBorder(targetX: Int, startY: Int, endY: Int): Boolean {
        if (targetX == 0 || targetX == width)
            return true

        for (i in startY until endY)
            if (!getRGB(targetX, i).isBlack())
                return false

        return true
    }

    private fun Int.isBlack() = this == -16777216
}