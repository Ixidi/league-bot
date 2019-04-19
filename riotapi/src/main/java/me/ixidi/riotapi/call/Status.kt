package me.ixidi.riotapi.call

enum class Status(val code: Int) {

    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    DATA_NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    UNSUPPORTED_MEDIA_TYPE(415),
    RATE_LIMIT_EXCEEDED(429),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504),
    UNSUPPORTED(-1);

    companion object {

        fun match(code: Int): Status {
            return values().firstOrNull { it.code == code } ?: UNSUPPORTED
        }

    }
}