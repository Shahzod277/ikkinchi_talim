package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiplomaAdminResponse {

private String universityName;
private List<CountApp> diploma;
}
