package me.jakejmattson.crop

import io.javalin.Javalin
import io.javalin.http.HttpStatus
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.io.encoding.Base64

fun main(vararg args: String) {
    val port = args
        .firstOrNull()
        ?.toIntOrNull()
        ?: 6583

    Javalin.create()
        .post("/") { context ->
            val type = context.contentType()!!
            println("Received $type from ${context.ip()}")

            when {
                type.startsWith("image") -> {
                    val input = ImageIO.read(context.bodyInputStream())
                    val output = cropImage(input)

                    val bytes = ByteArrayOutputStream().apply {
                        ImageIO.write(output, "png", this)
                    }.toByteArray()

                    context.result(Base64.encode(bytes))
                }

                else -> {
                    context.status(HttpStatus.BAD_REQUEST)
                }
            }
        }
        .start(port)
}

private fun cropImage(image: BufferedImage): BufferedImage {
    val height = image.height
    val width = image.width
    val centerX = width / 2
    val centerY = height / 2

    val top = image.seekTop(centerX, centerY)
    val left = image.seekLeft(centerX, centerY)
    val bottom = image.seekBottom(centerX, centerY, height)
    val right = image.seekRight(centerX, centerY, width)

    return image.getSubimage(left, top, right - left, bottom - top)
}

