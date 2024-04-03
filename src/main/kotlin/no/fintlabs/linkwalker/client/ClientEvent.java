package no.fintlabs.linkwalker.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClientEvent {

    private Client client;
    private String orgId;
    private Operation operation;
    private String errorMessage;


    public boolean hasError() {
        return StringUtils.hasText(errorMessage);
    }

    @JsonIgnore
    public String getOrganisationObjectName() {
        return orgId.replaceAll("\\.", "_");
    }

    public enum Operation {
        CREATE,
        READ,
        UPDATE,
        DELETE
    }

}
