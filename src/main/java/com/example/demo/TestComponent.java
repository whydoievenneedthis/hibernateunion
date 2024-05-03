package com.example.demo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestComponent {
  private final AppleRepository repo;

  @PostConstruct
  void init() {
    repo.saveAll(List.of(
        new Apple().setPieId(1).setBakedOn(LocalDate.of(2000, 1, 1)),
        new Apple().setPieId(1).setBakedOn(LocalDate.of(2010, 1, 1)),
        new Apple().setPieId(1).setBakedOn(LocalDate.of(2020, 1, 1)),
        new Apple().setPieId(1).setBakedOn(LocalDate.of(2030, 1, 1)),
        new Apple().setPieId(1).setBakedOn(LocalDate.of(2040, 1, 1)),
        new Apple().setPieId(2).setBakedOn(LocalDate.of(2000, 1, 1)),
        new Apple().setPieId(2).setBakedOn(LocalDate.of(2010, 1, 1)),
        new Apple().setPieId(2).setBakedOn(LocalDate.of(2020, 1, 1)),
        new Apple().setPieId(2).setBakedOn(LocalDate.of(2030, 1, 1)),
        new Apple().setPieId(2).setBakedOn(LocalDate.of(2040, 1, 1)),
        new Apple().setPieId(3).setBakedOn(LocalDate.of(2000, 1, 1)),
        new Apple().setPieId(3).setBakedOn(LocalDate.of(2010, 1, 1)),
        new Apple().setPieId(3).setBakedOn(LocalDate.of(2020, 1, 1)),
        new Apple().setPieId(4).setBakedOn(LocalDate.of(2030, 1, 1)),
        new Apple().setPieId(4).setBakedOn(LocalDate.of(2040, 1, 1))
    ));

    System.out.println("without joins");
    System.out.println("select, filter, union");
    repo.selectFilterUnionWithoutJoin(List.of(2,3), LocalDate.now())
        .forEach(a -> {
          System.out.println(a.getId() + " -- " + a.getPieId() + " -- " + a.getDir());
        });
    System.out.println("select, union, filter");
    repo.selectUnionFilterWithoutJoin(List.of(2,3), LocalDate.now())
        .forEach(a -> {
          System.out.println(a.getId() + " -- " + a.getPieId() + " -- " + a.getDir());
        });
    System.out.println();
  }
}
