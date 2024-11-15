package org.example.domain.block.dto;

public class BlockReqDTO {

    public record Block(
            Long from,
            Long to
    ) {}
}