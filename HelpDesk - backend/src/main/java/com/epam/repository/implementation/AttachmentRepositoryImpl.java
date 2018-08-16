package com.epam.repository.implementation;

import com.epam.entity.Attachment;
import com.epam.repository.AttachmentRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
}
