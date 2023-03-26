package com.example.demo.controller;

import com.example.demo.dto.RelativeDto;
import com.example.demo.repository.RelativeRepository;
import com.example.demo.service.RelativeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/kindergarten/relative")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class RelativeController {
    private final RelativeRepository repository;
    private final RelativeService service;

    @GetMapping("/{childId}")
    public List<RelativeDto> getAll(Principal principal, @PathVariable long childId) {
        return repository.findRelatives(childId, principal.getName())
                .stream().map(RelativeDto::new).toList();
    }

    @PostMapping("/{childId}")
    public RelativeDto add(Principal principal, @PathVariable long childId, @RequestBody @Valid RelativeDto dto) {
        return new RelativeDto(service.add(principal.getName(), childId, dto.name(), dto.address(), dto.phone()));
    }

    @DeleteMapping("/{childId}/{relativeId}")
    public void delete(Principal principal, @PathVariable long childId, @PathVariable long relativeId) {
        service.delete(principal.getName(), childId, relativeId);
    }


    @PatchMapping("/{childId}")
    public void update(Principal principal, @PathVariable long childId, @RequestBody @Valid RelativeDto dto) {
        service.updateOrReplaceRelative(principal.getName(), childId, dto.id(), dto.name(), dto.address(), dto.phone());
    }
}
