package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.ScheduleRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto){
        Schedule schedule = new Schedule(
                null,
                dto.getDescription(),
                dto.getUsername(),
                dto.getPassword(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getDescription(),savedSchedule.getUsername(), savedSchedule.getCreatedAt(), savedSchedule.getUpdatedAt());
    }
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll(){
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponseDto(schedule.getId(), schedule.getDescription(), schedule.getUsername(), schedule.getCreatedAt(),schedule.getUpdatedAt()))
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당하는 일정이 없습니다.")
        );
        return new ScheduleResponseDto(schedule.getId(), schedule.getDescription(), schedule.getUsername(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto){
        Schedule schedule = new Schedule(
                id,
                dto.getDescription(),
                dto.getUsername(),
                dto.getPassword(),
                null,
                LocalDateTime.now()
        );
        Schedule updatedSchedule = scheduleRepository.update(schedule);
        return new ScheduleResponseDto(updatedSchedule.getId(), updatedSchedule.getDescription(), updatedSchedule.getUsername(), updatedSchedule.getCreatedAt(),updatedSchedule.getUpdatedAt());
    }
    @Transactional
    public void deleteSchedule(Long id, String password){
        scheduleRepository.deleteById(id, password);
    }
}
