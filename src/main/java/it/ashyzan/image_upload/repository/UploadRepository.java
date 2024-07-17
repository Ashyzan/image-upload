package it.ashyzan.image_upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ashyzan.image_upload.model.Images;

public interface UploadRepository extends JpaRepository<Images, Integer> {

}
