SELECT id,
       row_number() over(ORDER BY x.id) page_number
FROM
  (SELECT id,
          CASE row_number() over(ORDER BY id) % 100
              WHEN 0 THEN 1
              ELSE 0
          END page_boundary
   FROM trips
   where id > :last_page PARAMS
   ORDER BY id LIMIT 1000) x
WHERE x.page_boundary = 1