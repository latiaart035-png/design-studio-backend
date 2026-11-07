package com.designstudio.ps.service.client;

import com.designstudio.ps.dto.BriefRequest;
import com.designstudio.ps.dto.RecResult;

public interface AiServiceClient {
    RecResult requestScoring(BriefRequest request);
}