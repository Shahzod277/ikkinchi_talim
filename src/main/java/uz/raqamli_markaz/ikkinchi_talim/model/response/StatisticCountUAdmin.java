package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticCountUAdmin {
    private Map<String, Integer> nationalDiploma = new HashMap<>();
    private Map<String, Integer> diplomaForeign = new HashMap<>();
    private Map<String, Integer> application = new HashMap<>();
    private String fullName;
    private String university;


}
