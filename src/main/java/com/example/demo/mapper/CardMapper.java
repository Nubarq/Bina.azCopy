package com.example.demo.mapper;

import com.example.demo.dto.card.CardRequest;
import com.example.demo.dto.card.CardResponse;
import com.example.demo.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {
    Card mapCardCreateRequestDTOToCardEntity(CardRequest cardCreateRequestDTO);
    Card mapCardUpdateRequestDTOToCardEntity(CardRequest cardUpdateRequestDTO);
    CardResponse mapCardEntityToCardResponseDTO(Card card);
}