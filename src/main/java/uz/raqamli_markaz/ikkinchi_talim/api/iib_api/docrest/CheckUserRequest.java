package uz.raqamli_markaz.ikkinchi_talim.api.iib_api.docrest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckUserRequest {
    private String pinfl;
    private String serialAndNumber;
    private String birthDate;
}
