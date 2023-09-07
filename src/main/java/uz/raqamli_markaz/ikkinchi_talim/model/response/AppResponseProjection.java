package uz.raqamli_markaz.ikkinchi_talim.model.response;

import java.time.LocalDate;

public interface AppResponseProjection {
Integer getId();
String getFullName();
String getPhoneNumber();
String getPinfl();
String getGivenDate();
String getPassportSerial();
String getPassportNumber();
String getSpeciality();
String getEduForm();
String getLang();
String getUniversity();
LocalDate getCreateDate();
}
