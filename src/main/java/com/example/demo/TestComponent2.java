package com.example.demo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestComponent2 {
  private final AppleRepository apples;
  private final PieRepository pies;
  private final TestComponent testComponent;

  @PostConstruct
  void init() {
    Pie pieA = pies.save(new Pie().setTaste("good"));
    Pie pieB = pies.save(new Pie().setTaste("better"));
    Pie pieC = pies.save(new Pie().setTaste("best"));
    Pie pieD = pies.save(new Pie().setTaste("bestest"));

    apples.saveAll(List.of(
        new Apple().setBakedPie(pieA).setBakedOn(LocalDate.of(2000, 1, 1)),
        new Apple().setBakedPie(pieA).setBakedOn(LocalDate.of(2010, 1, 1)),
        new Apple().setBakedPie(pieA).setBakedOn(LocalDate.of(2020, 1, 1)),
        new Apple().setBakedPie(pieA).setBakedOn(LocalDate.of(2030, 1, 1)),
        new Apple().setBakedPie(pieA).setBakedOn(LocalDate.of(2040, 1, 1)),
        new Apple().setBakedPie(pieB).setBakedOn(LocalDate.of(2000, 1, 1)),
        new Apple().setBakedPie(pieB).setBakedOn(LocalDate.of(2010, 1, 1)),
        new Apple().setBakedPie(pieB).setBakedOn(LocalDate.of(2020, 1, 1)),
        new Apple().setBakedPie(pieB).setBakedOn(LocalDate.of(2030, 1, 1)),
        new Apple().setBakedPie(pieB).setBakedOn(LocalDate.of(2040, 1, 1)),
        new Apple().setBakedPie(pieC).setBakedOn(LocalDate.of(2000, 1, 1)),
        new Apple().setBakedPie(pieC).setBakedOn(LocalDate.of(2010, 1, 1)),
        new Apple().setBakedPie(pieC).setBakedOn(LocalDate.of(2020, 1, 1)),
        new Apple().setBakedPie(pieD).setBakedOn(LocalDate.of(2030, 1, 1)),
        new Apple().setBakedPie(pieD).setBakedOn(LocalDate.of(2040, 1, 1))
    ));

    System.out.println("with joins");
    System.out.println("select, filter, union");
    apples.selectFilterUnionWithJoin(List.of(pieB.getId(), pieC.getId()), LocalDate.now())
        .forEach(a -> {
          System.out.println(a.getId() + " -- " + a.getPieId() + " -- " + a.getDir());
        });
    System.out.println("select, union, filter, will throw NPE");
    apples.selectUnionFilterWithJoin(List.of(pieB.getId(), pieC.getId()), LocalDate.now())
        .forEach(a -> {
          System.out.println(a.getId() + " -- " + a.getPieId() + " -- " + a.getDir());
        });
  }
}
