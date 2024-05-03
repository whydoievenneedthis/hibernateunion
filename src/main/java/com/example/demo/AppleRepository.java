package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppleRepository extends JpaRepository<Apple,Integer> {
  @Query(
      """
          SELECT new com.example.demo.CurrentApple(id, pieId, dir)
          FROM (
              SELECT
                id id, pieId pieId, bakedOn bakedOn,
                MAX(bakedOn) OVER (PARTITION BY pieId) mbo,
                -1 dir
              FROM Apple c
              WHERE
                   pieId IN :list
               AND bakedOn <= :now
          )
          WHERE bakedOn = mbo

          UNION ALL

          SELECT new com.example.demo.CurrentApple(id, pieId, dir)
          FROM (
              SELECT
                id id, pieId pieId, bakedOn bakedOn,
                MIN(bakedOn) OVER (PARTITION BY pieId) mbo,
                1 dir
              FROM Apple c
              WHERE
                   pieId IN :list
               AND bakedOn > :now
          )
          WHERE bakedOn = mbo
         """)
  List<CurrentApple> selectFilterUnionWithoutJoin(List<Integer> list, LocalDate now);

  @Query(
      """
          SELECT new com.example.demo.CurrentApple(id, pieId, dir)
          FROM (
            (
              SELECT
                id id, pieId pieId, bakedOn bakedOn,
                MAX(bakedOn) OVER (PARTITION BY pieId) mbo,
                -1 dir
              FROM Apple c
              WHERE
                   pieId IN :list
               AND bakedOn <= :now

            ) UNION ALL (

              SELECT
                id id, pieId pieId, bakedOn bakedOn,
                MIN(bakedOn) OVER (PARTITION BY pieId) mbo,
                1 dir
              FROM Apple c
              WHERE
                   pieId IN :list
               AND bakedOn > :now
            )
          )
          WHERE bakedOn = mbo
          ORDER BY dir
         """)
  List<CurrentApple> selectUnionFilterWithoutJoin(List<Integer> list, LocalDate now);








  @Query(
      """
          SELECT new com.example.demo.CurrentApple(id, bakedPie.id, dir)
          FROM (
              SELECT
                id id, bakedPie bakedPie, bakedOn bakedOn,
                MAX(bakedOn) OVER (PARTITION BY bakedPie.id) mbo,
                -1 dir
              FROM Apple c
              WHERE
                   bakedPie.id IN :list
               AND bakedOn <= :now
          )
          WHERE bakedOn = mbo

          UNION ALL

          SELECT new com.example.demo.CurrentApple(id, bakedPie.id, dir)
          FROM (
              SELECT
                id id, bakedPie bakedPie, bakedOn bakedOn,
                MIN(bakedOn) OVER (PARTITION BY bakedPie.id) mbo,
                1 dir
              FROM Apple c
              WHERE
                   bakedPie.id IN :list
               AND bakedOn > :now
          )
          WHERE bakedOn = mbo
         """)
  List<CurrentApple> selectFilterUnionWithJoin(List<Integer> list, LocalDate now);

  @Query(
      """
          SELECT new com.example.demo.CurrentApple(id, bakedPie.id, dir)
          FROM (
            (
              SELECT
                id id, bakedPie bakedPie, bakedOn bakedOn,
                MAX(bakedOn) OVER (PARTITION BY bakedPie.id) mbo,
                -1 dir
              FROM Apple c
              WHERE
                   bakedPie.id IN :list
               AND bakedOn <= :now

            ) UNION ALL (

              SELECT
                id id, bakedPie bakedPie, bakedOn bakedOn,
                MIN(bakedOn) OVER (PARTITION BY bakedPie.id) mbo,
                1 dir
              FROM Apple c
              WHERE
                   bakedPie.id IN :list
               AND bakedOn > :now
            )
          )
          WHERE bakedOn = mbo
          ORDER BY dir
         """)
  List<CurrentApple> selectUnionFilterWithJoin(List<Integer> list, LocalDate now);
}
