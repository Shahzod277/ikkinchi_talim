package uz.raqamli_markaz.ikkinchi_talim.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    public void init();
    public String upload(MultipartFile file, String key);
    public Resource download(String filename);
    public void delete(String filename);
    public void deleteFiles(List<String> filenames);
}
