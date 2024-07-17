package it.ashyzan.image_upload.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.ashyzan.image_upload.model.Images;
import it.ashyzan.image_upload.repository.UploadRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class ImageUploadController {

	@Autowired
	private UploadRepository uploadRepo;

	@GetMapping("/")
	public String index() {

		return "index";

	}

	@PostMapping("/imageUpload")
	public String imageUpload(@RequestParam MultipartFile img, HttpSession session) {

		// System.out.println(img.getOriginalFilename());

		Images im = new Images();
		im.setImageName(img.getOriginalFilename());

		// System.out.println(img.getOriginalFilename());

		Images uploadImg = uploadRepo.save(im);

		if (uploadImg != null) {

			try {

				// cambiare destinazione
				File saveFile = new ClassPathResource("static/uploadsimg").getFile();

				// System.out.println(saveFile);

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img.getOriginalFilename());

				// System.out.println(path);

				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				// inserire istruzione salvare file url a db
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		session.setAttribute("msg", "Image Upload succesfully");

		return "redirect:/";

	}

}
