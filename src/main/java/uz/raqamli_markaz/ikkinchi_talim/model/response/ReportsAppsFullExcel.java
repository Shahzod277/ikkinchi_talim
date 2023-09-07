package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.ByteArrayInputStream;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportsAppsFullExcel {
    private ByteArrayInputStream byteArrayInputStream;
    private String  univerName;
}
