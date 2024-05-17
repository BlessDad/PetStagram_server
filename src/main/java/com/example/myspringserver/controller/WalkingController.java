package com.example.myspringserver.controller;

import com.example.myspringserver.dto.WalkingDto;
import com.example.myspringserver.entity.Walking;
import com.example.myspringserver.repository.WalkingRepository;
import com.example.myspringserver.service.WalkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/insert")
    public ResponseEntity<String> insertWalking(@RequestBody WalkingDto walkingDto) {
        walkingRepository.save(walkingService.convertToEntity(walkingDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("산책 기록 성공");
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

    @GetMapping("/getWalking")
    public ResponseEntity<List<WalkingDto>> getWalkings() {
        List<Walking> walkings = walkingRepository.findAll();
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
