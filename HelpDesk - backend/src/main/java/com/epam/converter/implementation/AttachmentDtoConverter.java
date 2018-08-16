package com.epam.converter.implementation;

import com.epam.converter.DtoConverter;
import com.epam.dto.AttachmentDto;
import com.epam.entity.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentDtoConverter implements DtoConverter<Attachment, AttachmentDto> {

    @Autowired
    private TicketDtoConverter ticketDtoConverter;

    @Override
    public AttachmentDto fromEntityToDto(Attachment attachment) {
        if (attachment == null) {
            return null;
        }
        return new AttachmentDto.Builder()
            .setId(attachment.getId())
            .setBlob(attachment.getBlob())
            .setName(attachment.getName())
            .setTicket(ticketDtoConverter.fromEntityToDto(attachment.getTicket()))
            .build();
    }

    @Override
    public Attachment fromDtoToEntity(AttachmentDto attachmentDto) {
        if (attachmentDto == null) {
            return null;
        }
        return new Attachment.Builder()
            .setId(attachmentDto.getId())
            .setBlob(attachmentDto.getBlob())
            .setName(attachmentDto.getName())
            .setTicket(ticketDtoConverter.fromDtoToEntity(attachmentDto.getTicket()))
            .build();
    }
}
