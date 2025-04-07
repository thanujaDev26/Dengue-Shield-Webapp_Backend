package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestH411Dto;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.model.entity.H411;
import com.dengue_webapp.dengue_webapp.model.entity.InfectiousCaseReport;
import com.dengue_webapp.dengue_webapp.model.entity.Message;
import com.dengue_webapp.dengue_webapp.model.enums.MessageStatus;
import com.dengue_webapp.dengue_webapp.repository.H411Repo;
import com.dengue_webapp.dengue_webapp.repository.InfectiousCaseRepo;
import com.dengue_webapp.dengue_webapp.repository.MessageRepo;
import com.dengue_webapp.dengue_webapp.service.H411Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class H411ServiceImpl implements H411Service {

    @Autowired
    private H411Repo h411Repo;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private InfectiousCaseRepo infectiousCaseRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public String saveExtendedFormData(Long messageId, RequestH411Dto h411Dto) {
        System.out.println("hello in service");

        // Retrieve the old message by its ID
        Optional<Message> oldMsg = messageRepo.findById(messageId);
        if (oldMsg.isEmpty()) {
            throw new NoDataFoundException("Message couldn't find");
        }
        System.out.println(oldMsg);

        // Map the DTO to the H411 entity
        H411 h411 = modelMapper.map(h411Dto, H411.class);

        // Save the H411 entity (this is important to create the record in the communicable_disease_report table)
        h411Repo.save(h411);
        System.out.println(h411);

        // Now, you can set the H411 to the message after the H411 entity is saved
        oldMsg.get().setH411(h411);
        oldMsg.get().setStatus(MessageStatus.COMPLETED);
        // Save the updated message with the h411_id set
        messageRepo.save(oldMsg.get());
        System.out.println(oldMsg.get());
        InfectiousCaseReport report = new InfectiousCaseReport();
        report.setMessage(oldMsg.get());
        infectiousCaseRepo.save(report);
        return "h411 successfully saved";
    }

}
