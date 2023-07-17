package uz.raqamli_markaz.ikkinchi_talim.model.response;

import java.time.LocalDate;

public interface AppResponseProjection {
Integer getId();
String getFullName();
String getPhoneNumber();
String getSpeciality();
String getUniversity();
LocalDate getCreateDate();
}
