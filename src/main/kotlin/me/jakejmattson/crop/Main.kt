package me.jakejmattson.crop

import io.javalin.Javalin
import io.javalin.http.HttpStatus
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
                    val output = ImageCropper(input).crop()

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