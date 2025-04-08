package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestInwardDocumentDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestNotebookDto;
import com.dengue_webapp.dengue_webapp.dto.request.RequestPHIDto;
import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;
import com.dengue_webapp.dengue_webapp.model.entity.InwardDocument;
import com.dengue_webapp.dengue_webapp.model.entity.Message;
import com.dengue_webapp.dengue_webapp.model.entity.NoteBook;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;

import java.util.List;
import java.util.Map;

public interface PHIService {

    public PHIOfficer createPHI(RequestPHIDto dto);
   public void  assignMOHOfficer(PHIOfficer newPhiOfficer, String branch, String district);
    public ResponsePHIDto getPHI(long id);

    public void deletePHI(long id);


    public List<ResponsePHIDto> getAllPHI(String searchText, int page, int size);

    PHIOfficer updatePhiOfficer(Long id, Map<String, Object> updates);

    InwardDocument saveInwardDocument(Long phiId,RequestInwardDocumentDto document);

    List<InwardDocument> getAllInwardDocument(Long phiId);

    InwardDocument updateInwardDocument(Long id, Map<String, Object> updates);

    InwardDocument deleteInwardDocument(Long id);

    List<Message> getAllPendingMessages(Long phiId);


 Message updateMessageStatus(Long id);

 List<Message> getAllSentMessages(Long phiId);

    Message getMessageById(Long messageId);

    NoteBook saveNotebook(Long phiId, RequestNotebookDto note);

    List<Message> getAllCompletedMessages(Long phiId);
}