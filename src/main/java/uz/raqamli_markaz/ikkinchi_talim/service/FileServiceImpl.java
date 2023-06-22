package uz.raqamli_markaz.ikkinchi_talim.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.storage.location}")
    private Path location;

    @Override
    public void init() {
        try {
            Files.createFile(location);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String upload(MultipartFile file, String key) {
        if (!file.isEmpty()) {
            String random = RandomStringUtils.random(5, true, true);
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            int name = ThreadLocalRandom.current().nextInt(99999999, 1000000000);
            String s = FilenameUtils.getExtension(fileName);
            String fullFileName = key + "-" + random + name + "." + s;
            String currentUrl = getCurrentUrl(fullFileName);
            try {
                Files.copy(file.getInputStream(), this.location.resolve(Objects.requireNonNull(fullFileName)), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
            return currentUrl;
        }
        return null;
    }

    public String uploadCert(MultipartFile file, String key) {
        if (!file.isEmpty()) {
            String random = RandomStringUtils.random(5, true, true);
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String orginalName = fileName.replace("`", "");
            String fullFileName = key + "-" + orginalName;
            try {
                Files.copy(file.getInputStream(), this.location.resolve(Objects.requireNonNull(fullFileName)),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
            return fullFileName;
        }
        return null;
    }

    public String uploadProto(MultipartFile file, String key) {
        if (!file.isEmpty()) {
//            String random = RandomStringUtils.random(5, true, true);
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String orginalName = fileName.replace("`", "");
            Integer name = ThreadLocalRandom.current().nextInt(99999999, 1000000000);
            String fullFileName = key + "-" + name;
            try {
                Files.copy(file.getInputStream(), this.location.resolve(Objects.requireNonNull(fullFileName)),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
            return fullFileName;
        }
        return null;
    }

    @Override
    public Resource download(String filename) {
        try {
            Path path = location.resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(String filename) {
        try {
            File file = new File(location + "/" + filename);
            file.delete();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleted: " + e.getMessage());
        }
    }

    @Override
    public void deleteFiles(List<String> filenames) {
        try {
            filenames.forEach(s -> {
                File file = new File(location + "/" + s);
                file.delete();
            });
        } catch (RuntimeException e) {
            throw new RuntimeException("Error deleted: " + e.getMessage());
        }
    }

    private String getCurrentUrl(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/public/download/")
                .path(fileName).toUriString();
    }
}
