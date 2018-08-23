package com.epam.repository.implementation;

import com.epam.entity.Attachment;
import com.epam.exception.FileLoadingException;
import com.epam.repository.AttachmentRepository;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AttachmentRepositoryImpl implements AttachmentRepository {

    private static final Logger LOGGER = Logger.getLogger(AttachmentRepositoryImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Attachment addAttachment(Attachment attachment) {
        Session session = sessionFactory.getCurrentSession();
        session.save(attachment);
        return attachment;
    }

    @Override
    public Optional<Attachment> getAttachmentById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Attachment attachment = session.get(Attachment.class, id);
        return Optional.ofNullable(attachment);
    }

    @Override
    public List<Attachment> getAttachmentsByTicketId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Attachment> query = session
            .createQuery("from Attachment where ticket.id = :id", Attachment.class);
        query.setParameter("id", id);
        return query.list();
    }

    @Override
    public byte[] getFileAsResource(Attachment attachment) {
        Session session = sessionFactory.getCurrentSession();
        attachment = session.get(Attachment.class, attachment.getId());
        Blob blob = attachment.getBlob();
        byte[] bytes;
        try {
            bytes = blob.getBytes(0, (int) blob.length());
        } catch (SQLException e) {
            throw new FileLoadingException(e.getMessage());
        }
        return bytes;
    }

    @Override
    public Attachment removeAttachment(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Attachment attachment = session.get(Attachment.class, id);
        session.delete(attachment);
        return attachment;
    }

    @Override
    public Attachment updateAttachment(Attachment attachment) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(attachment);
        return attachment;
    }
}
