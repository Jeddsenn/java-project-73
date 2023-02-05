package hexlet.code.dto.response;


import java.time.Instant;

public record LabelRes(

        Long id,
        String name,

        Instant createdAt) {
}

