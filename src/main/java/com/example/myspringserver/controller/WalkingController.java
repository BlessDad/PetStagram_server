package com.example.myspringserver.controller;

import com.example.myspringserver.dto.WalkingDto;
import com.example.myspringserver.entity.User;
import com.example.myspringserver.entity.Walking;
import com.example.myspringserver.repository.UserRepository;
import com.example.myspringserver.repository.WalkingRepository;
import com.example.myspringserver.service.WalkingService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/walking")
public class WalkingController {
    @Autowired
    private WalkingRepository walkingRepository;
    @Autowired
    private WalkingService walkingService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/insert/{user_id}")
    public ResponseEntity<String> insertWalking(@PathVariable Integer user_id, @RequestBody WalkingDto walkingDto) {
        try{
            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없음"));
            walkingDto.setUser_id(user.getUser_id());
            Walking walking = walkingService.convertToEntity(walkingDto);
            walkingRepository.save(walking);
            return ResponseEntity.status(HttpStatus.CREATED).body("산책 기록 성공");
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원을 찾을 수 없음");
        }
    }

    //사진 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }
        try {
            String uploadDir = "/home/ubuntu/walkingImages";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // 업로드 디렉토리가 없는 경우 생성
            }
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            File dest = new File(filePath);
            file.transferTo(dest);
            String fileUrl = "/uploads/" + fileName;
            return ResponseEntity.status(HttpStatus.CREATED).body(fileUrl);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    @PutMapping("/updateWalking/{id}")
    public ResponseEntity<String> updateWalking(@PathVariable Integer id, @RequestBody Walking updatedWalking){
        Walking existingWalking = walkingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("산책 정보를 찾을 수 없음"));

        //existingWalking.setWalking_id(updatedWalking.getWalking_id());
        existingWalking.setWalking_start(updatedWalking.getWalking_start());
        existingWalking.setWalking_end(updatedWalking.getWalking_end());
        existingWalking.setWalking_distance(updatedWalking.getWalking_distance());
        existingWalking.setWalking_calorie(updatedWalking.getWalking_calorie());
        existingWalking.setWalking_speed(updatedWalking.getWalking_speed());

        walkingRepository.save(existingWalking);

        return ResponseEntity.ok("산책정보 수정 성공");
    }

    //User의 모든 산책 정보 가져오기
    //포함하여 실행하면 빌드 실패해서 일단 주석 처리
//    @GetMapping("/getWalking/{user_id}")
//    public ResponseEntity<List<WalkingDto>> getWalkings(@PathVariable Integer user_id) {
//        List<Walking> walkings = walkingRepository.findByUserId(user_id);
//        List<WalkingDto> walkingDtos = walkings.stream().map(walkingService::convertToDto).collect(Collectors.toList());
//
//        return new ResponseEntity<>(walkingDtos, HttpStatus.OK);
//    }

    @GetMapping("/getWalking")
    public ResponseEntity<List<WalkingDto>> getWalkings() {
        List<Walking> walkings = walkingRepository.findAll();
        List<WalkingDto> walkingDtos = walkings.stream().map(walkingService::convertToDto).collect(Collectors.toList());

        return new ResponseEntity<>(walkingDtos, HttpStatus.OK);
    }

    @GetMapping("/getWalking/between")
    public ResponseEntity<List<WalkingDto>> getWalkingBetween(@RequestParam("startDateTime") LocalDateTime startDateTime,
                                           @RequestParam("endDateTime") LocalDateTime endDateTime) {
        List<Walking> walkings = walkingRepository.findByWalking_startBetween(startDateTime, endDateTime);
        List<WalkingDto> walkingDtos = walkings.stream().map(walkingService::convertToDto).collect(Collectors.toList());

        return new ResponseEntity<>(walkingDtos, HttpStatus.OK);
    }




    @DeleteMapping("/deleteWalking/{id}")
    public ResponseEntity<?> deleteWalking (@PathVariable Integer id) {
        try {
            walkingRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
