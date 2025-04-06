package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestH411Dto;

public interface H411Service {
    String saveExtendedFormData(Long messageId, RequestH411Dto h411Dto);
}
