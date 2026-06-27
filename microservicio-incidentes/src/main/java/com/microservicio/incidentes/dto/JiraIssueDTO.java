// com.microservicio.incidentes.dto.JiraIssueDTO.java
package com.microservicio.incidentes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JiraIssueDTO {

    @Data
    public static class IssueResponse {
        private String id;

        @JsonProperty("issueKey")
        private String issueKey;

        private String self;

        @JsonProperty("issueId")
        private String issueId;

        @JsonProperty("key")
        private String key;

        // Constructor por defecto
        public IssueResponse() {
        }

        // Constructor con parámetros
        public IssueResponse(String id, String issueKey, String self, String issueId, String key) {
            this.id = id;
            this.issueKey = issueKey;
            this.self = self;
            this.issueId = issueId;
            this.key = key;
        }

        // Método helper para obtener el key
        public String getKey() {
            return key != null ? key : issueKey;
        }
    }
}