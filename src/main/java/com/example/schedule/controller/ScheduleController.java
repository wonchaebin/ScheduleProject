package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/schedule")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto dto){
        return ResponseEntity.ok(scheduleService.saveSchedule(dto));
    }
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>>findAllSchedules(){
        return ResponseEntity.ok(scheduleService.findAll());
    }
    @GetMapping("/{id}")
    public  ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id){
        return ResponseEntity.ok(scheduleService.findById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id, @RequestParam String password){
        scheduleService.deleteSchedule(id, password);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
