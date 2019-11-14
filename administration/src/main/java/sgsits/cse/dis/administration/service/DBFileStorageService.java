package sgsits.cse.dis.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import sgsits.cse.dis.administration.exception.FileStorageException;
import sgsits.cse.dis.administration.exception.MyFileNotFoundException;
import sgsits.cse.dis.administration.model.DocumentsFile;
import sgsits.cse.dis.administration.repo.DocumentsFileRepository;

import java.io.IOException;

@Service
public class DBFileStorageService {

    @Autowired
    private DocumentsFileRepository dbFileRepository;

    public DocumentsFile storeFile(MultipartFile file, long parent) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DocumentsFile dbFile = new DocumentsFile(fileName, file.getContentType(), file.getBytes(), parent);

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DocumentsFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
